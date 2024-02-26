package com.realtor.plugins.repository

import com.realtor.plugins.dao.HousesDao
import com.realtor.plugins.data.model.Houses
import com.realtor.plugins.data.table.HousesTable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.statements.InsertStatement

class HousesRepository : HousesDao {
    override suspend fun insert(title: String, price: String, type: String, size: String, rooms: Int): Houses? {
        val statement: InsertStatement<Number>? = null
        DatabaseFactory.dbQuery {
            statement = HousesTable.insert { house ->
                house[HousesTable.title] = title
                house[HousesTable.price] = price
                house[HousesTable.type] = type
                house[HousesTable.size] = size
                house[HousesTable.rooms]
            }
        }
    }

    override suspend fun getHouses(): List<Houses>? {
        TODO("Not yet implemented")
    }

    override suspend fun getHousesById(id: Int): Houses? {
        TODO("Not yet implemented")
    }

    override suspend fun deleteHouseById(id: Int): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun updateHouseById(title: String, price: String, type: String, size: String, rooms: Int): Int? {
        TODO("Not yet implemented")
    }
}