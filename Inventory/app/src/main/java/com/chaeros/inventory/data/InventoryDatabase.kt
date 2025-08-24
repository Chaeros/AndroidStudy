package com.chaeros.inventory.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Item::class],   // Item을 entities 목록이 있는 유일한 클래스로 지정
    version = 1,                // version 입력, 스키마 변경마다 버전 높이기
    exportSchema = false        // 스키마 버전 백업 하지않기 위해 false로 설정
)
abstract class InventoryDatabase: RoomDatabase() {
    abstract fun itemDao(): ItemDao // DB가 DAO를 알 수 있도록 ItemDao 반환하는 추상 함수 선언
    
    // 클래스 내에서 싱글톤으로 가지는 객체 companion object
    companion object {
        // DB가 만들어지면 DB참조를 유지, 이를 통해 단일 인스턴스 유지로 DB 생성 비용 감소
        // @Volatile을 통해 기본 메모리에서 모든 읽기 쓰기 수행
        // 이를 통해 Instance 값을 최산상태로 유지하고 모든 싫행 스레드에 동일값 유지
        @Volatile
        private var Instance: InventoryDatabase? = null

        fun getDatabase(context: Context) : InventoryDatabase {
            // 여러 스레드에서 동시에 DB 인스턴스를 요청할 경우 경합 상태에 빠질 수 있으므로,
            // synchronized 블록을 통해 DB를 가져와 DB가 한 번만 초기화되도록 함
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, InventoryDatabase::class.java, "item_database")
                    // .fallbackToDestructiveMigration() // ← 사용하면 기존 스키마 삭제하고 새로 생성함, JPA에서 create 같은거
                    .build()
                    .also { Instance = it }
            }
        }
    }
}