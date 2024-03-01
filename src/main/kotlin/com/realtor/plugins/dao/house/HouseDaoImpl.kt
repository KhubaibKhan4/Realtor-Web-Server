package com.realtor.plugins.dao.house

import com.realtor.plugins.data.table.HouseRow
import com.realtor.plugins.data.table.HouseTable
import com.realtor.plugins.data.table.HouseTable.houseId
import com.realtor.plugins.repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.insert

class HouseDaoImpl: HouseDao{
    override suspend fun createHouse(
        title: String,
        imageUrl: String,
        address: String,
        type: String,
        size: String,
        rooms: String,
        categoryId: Int
    ): Boolean {
        return dbQuery {
            val insertStatement = HouseTable.insert {
                it[HouseTable.title] = title
                it[HouseTable.imageUrl] = imageUrl
                it[HouseTable.address] = address
                it[HouseTable.type] = type
                it[HouseTable.size] = size
                it[HouseTable.rooms] = rooms
                it[HouseTable.categoryId] = categoryId
            }
            insertStatement.resultedValues?.singleOrNull() != null
        }
    }

    override suspend fun getHousesByCategory(categoryId: Int, pageNumber: Int, pageSize: Int): List<HouseRow> {
        TODO("Not yet implemented")
    }

    override suspend fun getHouse(houseId: Long): HouseRow? {
        TODO("Not yet implemented")
    }

    override suspend fun updateHouse(
        houseId: Long,
        title: String,
        imageUrl: String,
        address: String,
        type: String,
        size: String,
        rooms: String,
        categoryId: Int
    ): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteHouse(houseId: Long): Boolean {
        TODO("Not yet implemented")
    }
}