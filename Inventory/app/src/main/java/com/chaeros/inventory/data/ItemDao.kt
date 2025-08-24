package com.chaeros.inventory.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    // DB 작업에 오랜 시간이 걸릴 수 있으므로 suspend 키워드를 통해 별도 스레드에서 수행시킴
    // Room은 기본 스레드에서 DB 엑세스를 허용하지 않음
    // OnConflictStrategy.IGNORE : 충돌 방지 전략, 충돌 발생할 경우 이후 데이터 삽입 무시
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    @Update
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)

    // 별도 쿼리를 통해 SQL 수행 가능
    // 콜론 표기법 사용을 통해 함수의 인수 참조 가능
    // Flow를 반환 유형으로 사용할 경우
    // 1) DB 데이터 변경 시 알림 받아서 최신 데이터 상태 유지 가능
    // 2) Room 백그라운드 스레드에서 쿼리를 실행, 따라서 명시적으로 suspend 안 써도 됨
    @Query("SELECT * FROM items WHERE id = :id")
    fun getItem(id: Int): Flow<Item>

    @Query("SELECT * FROM items ORDER BY name ASC")
    fun getAllItems(): Flow<List<Item>>
}