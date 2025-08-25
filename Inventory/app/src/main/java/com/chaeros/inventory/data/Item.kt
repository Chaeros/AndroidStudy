package com.chaeros.inventory.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// data class 로 선언 시, toString, copy, equals와 같은 유틸리티 사용 가능
// 단, abstract, open, sealed 키워드 사용 불가

// Entity : 해당 데이터 클래스를 Room 테이블로 생성
// tableName = "items" : 실제 SQLite 에 생성되는 테이블 이름을 items로
@Entity(tableName = "items")
data class Item(
    // 기본 키로 설정
    // autoGenerate=true 키워드를 통해 SQLite가 1,2,3 순서대로 증가하며 자동 값 배정
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,    // 만약 id가 자동증가값으로 넣어지지 않고, 미지정 된 경우 0으로 저장
    val name: String,
    val price: Double,
    val quantity: Int
)