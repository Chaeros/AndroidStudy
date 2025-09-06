    package com.chaeros.datastore.data

    import android.util.Log
    import androidx.datastore.core.DataStore
    import androidx.datastore.preferences.core.Preferences
    import androidx.datastore.preferences.core.booleanPreferencesKey
    import androidx.datastore.preferences.core.edit
    import androidx.datastore.preferences.core.emptyPreferences
    import kotlinx.coroutines.flow.Flow
    import kotlinx.coroutines.flow.catch
    import kotlinx.coroutines.flow.map
    import java.io.IOException

    // DataStore : 안드로이드 Jetpack 라이브러리 중 하나로, 앱 내부에 데이터를 영구적으로 저장하는 용도
    // 앱 종료/재실행, 기기 재부팅, 앱 캐시 삭제에도 데이터가 남아있음
    // 단, 앱 데이터를 삭제하거나, 앱을 완전히 삭제 후 재설치하면 지워짐
    // DataStore는 Key-Value 기반 또는 Json 비슷한 직렬화 데이터를 남기는 메모장에 가깝고, Room은 SQL 기반 관계형 DB라는 차이점 있음
    // DataStore 중 Preferences는 Key-Value, Proto는 직렬화 데이터 형태로 데이터를 저장함
    class UserPreferencesRepository(
        private val dataStore: DataStore<Preferences>
    ) {
        // object : 싱글톤 객체 정의
        // companion object : 특정 클래스에 소속된 싱글톤 객체, 쉽게 클래스에서 사용하는 static 멤버
        // -> 클래스 인스턴스 없이도 접근 가능 ex) UserPreferencesRepository.IS_LINEAR_LAYOUT
        // 하지만, 아래의 companion object는 private로 접근 제한자가 지정되어 있어, 외부에서 접근 불가
        // -> private로 할거면 왜 companion object를 쓰냐? 클래스 인스턴스마다 새로 만들 필요가 없으니까, 그리고 외부에서 접근할 필요없으니 캡슐화시킴
        // -> 메모리 효율 상승 : 이러면 아래에 속한 키값들이 애플리케이션 실행 동안 한 번만 만들어도 되기때문에
        // booleanPreferencesKey() -> Preferences.Key<Boolean> 타입의 객체를 만드는 함수
        // is_linear_layout 이라는 문자열로 Key 이름으로 사용
        // IS_LINEAR_LAYOUT은 실제 코드에서 사용할 Key '객체' -> 여기에 true/false value 저장함
        // IS_LINEAR_LAYOUT 객체 내부 -> {"is_linear_layout" : true}
        // Key 객체를 사용하는 이유
        // 1) 어떤 타입을 사용할 것인지 명시 가능
        // 2) prefs["is_linear_layout"] = true 이건 오타가 있어도 컴파일러가 오류를 못 잡아 주는데,
        //    -> prefs[IS_LINEAR_LAYOUT] = true는 중간에 오타가 있으면 컴파일러가 오류를 잡아줄 수 있음
        // -> 즉 타입 안전성과 컴파일러 과정에서 오류를 잡아주어 코드 안전성 까지 챙길 수 있기 때문이다
        private companion object {
            val IS_LINEAR_LAYOUT = booleanPreferencesKey("is_linear_layout")
            const val TAG = "UserPreferencesRepository" // Log 찍을 때 쓸 예정인 문자열
        }

        // dataStore 내의 IS_LINEAR_LAYOUT Key 객체의 value를 isLinearLayout으로 변경하겠다는 메서드
        // edit() 메서드에 람다 전달하여 dataSTore 내 값 수정
        // dataStore 값 업데이트에 사용할 수 있는 MutablePreferences 인스턴스 전달
        // -> 이 람다 내부의 모든 업데이트가 단일 트랜잭션으로 실행됨
        // -> 즉, 업데이가 원자적으로 이루어지기 떄문에 일부 값은 업데이트 되고, 일부는 업데이트 안 되는 문제 발생x
        // MutalblePreferences 인스턴스를 전달하는 이유
        // -> Preferences 는 읽기 전용 맵같은 객체임, 즉 쓰기가 불가능
        // -> 따라서 쓰기 가능한 MutablePreferences 객체를 사용하는 것
        // 과정 : edit() 호출로 Preferences 복사본을 MutablePreferences 로 전달
        // -> 복사본에 값 수정 후 -> 람다 종류 후에 DataStore가 해당 복사본을 원본에 반영(저장소 커밋)
        // 위 과정을 거쳐 값 수정이 트랜잭션 단위로 발생함
        suspend fun saveLayoutPreference(isLinearLayout: Boolean) {
            dataStore.edit { preferences ->
                preferences[IS_LINEAR_LAYOUT] = isLinearLayout
            }
        }

        // DataStore 데이터 읽어오는 메서드
        // Flow를 통해 값을 받아오는 이유
        // -> 단순히 한 번 값 읽고 끝이 아니라, 값 변할 떄마다 구독자 측에게
        // 값이 바뀌었다는 사실과 새로운 값을 전달함. -> 단, 구독자가 이것을 받아서 재처리 하냐에 따라 UI 갱신 여부가 갈림
        //
        val isLinearLayout: Flow<Boolean> = dataStore.data
            .catch {
                if(it is IOException) { // 파일이 존재하지 않거나, 디스크가 꽉차거나, 마운트 해제될 경우 발생
                    Log.e(TAG, "Error reading preferences.", it)    // it는 발생한 예외 객체 자레를 가리킴
                    // emit() : 기본 빈 객체를 반환하도록 처리함, 앱이 예외 떄문에 종료되지 않고, 기본값을 사용해서 동작을 유지시키기 위함
                    // 기본 객체 반환되면, 아래 map 단계로 넘어간 이후 값이 없어 true를 반환시킨다.
                    // 따라서 map의 :? 처리는 필수적이다. 안하면 앱 종료될 위험이 있다.
                    emit(emptyPreferences())
                }
                else{
                    // IOException 외의 다른 예외가 발생할 경우 거친다.
                    // throw의 경우에는 emit()을 사요하지 않아, 이후 map을 거치지 않는다.
                    // 때문에 앱이 종료할 수도 있는데, 이러는 이유는 알 수 없는 이유로 정상동작을 하지않는데,
                    // 앱이 종료되지않고 지속적으로 수행하면, 심각한 문제가 발생할 수도 있기 때문이다.
                    // 따라서 앱이 종료되던 말던 크래시를 내서 개발자가 심각함 인지 후 원인을 찾도록 하기 위함이다.
                    // 이상한 로직이 계쏙 돌아가게 두는 것보다, 이게 낫다라고 판단하는 경우 쓴다.
                    throw it
                }
            }
            .map { preferences->
                // 없는 값을 반환시, 앱이 종료될 수도 있으므로 반드시 ?: 를 사용해 기본값을 넣어줘야한다.
                // 위에도 썻지만, catch문을 타서 문제가 발생한 경우에도, 앱이 종료되지 않도록하기 위해
                // emit을 통해 map 과정도 거치게 만들기 때문에 반드시 필요하다
                preferences[IS_LINEAR_LAYOUT] ?: true
            }
    }