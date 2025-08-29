package com.chaeros.inventory.item

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chaeros.inventory.data.InventoryDatabase
import com.chaeros.inventory.data.Item
import com.chaeros.inventory.data.ItemDao
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ItemDaoTest {
    private lateinit var itemDao: ItemDao
    private lateinit var inventoryDatabase: InventoryDatabase

    private var item1 = Item(1,"Apples",10.0,20)
    private var item2 = Item(2,"Bananas",15.0,97)

    private suspend fun addOneItemToDb() {
        itemDao.insert(item1)
    }

    private suspend fun addTwoItemsToDb() {
        itemDao.insert(item1)
        itemDao.insert(item2)
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsItemIntoDB() = runBlocking {
        addOneItemToDb()
        val allItems = itemDao.getAllItems().first()
        assertEquals(allItems[0], item1)    // DB 내 첫번째 item와 비교
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllItems_returnsAllItemsFromDB() = runBlocking {
        addTwoItemsToDb()
        val allItems = itemDao.getAllItems().first()
        assertEquals(allItems[0], item1)
        assertEquals(allItems[1], item2)
    }

    // 모든 테스트 전에 실행
    @Before
    fun createDB() {
        val context: android.content.Context = ApplicationProvider.getApplicationContext()
        // Memory 내에 DB를 사용하여 프로세스 종료시 함께 삭제
        inventoryDatabase = Room.inMemoryDatabaseBuilder(context, InventoryDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    // 모든 테스트 이후 실행
    @After
    @Throws(IOException::class)
    fun closeDb() {
        inventoryDatabase.close()
    }
}

