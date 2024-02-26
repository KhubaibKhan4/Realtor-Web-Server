package com.realtor.plugins.repository

import com.realtor.plugins.dao.HousesDao
import com.realtor.plugins.data.model.Houses
import com.realtor.plugins.data.table.HousesTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.InsertStatement

class HousesRepository : HousesDao {
    override suspend fun insert(title: String, price: String, type: String, size: String, rooms: Int): Houses? {
        var statement: InsertStatement<Number>? = null
        DatabaseFactory.dbQuery {
            statement = HousesTable.insert { house ->
                house[HousesTable.title] = title
                house[HousesTable.price] = price
                house[HousesTable.type] = type
                house[HousesTable.size] = size
                house[HousesTable.rooms]
            }
        }
        return rowToResult(statement?.resultedValues?.get(0)!!)
    }

    override suspend fun getHouses(): List<Houses>? {
        return DatabaseFactory.dbQuery {
            HousesTable.selectAll()
                .mapNotNull {
                    rowToResult(it)
                }
        }
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

    fun rowToResult(row: ResultRow): Houses? {
        if (row == null) {
            return null
        } else {
            return Houses(
                id = row[HousesTable.id],
                title = row[HousesTable.title],
                price = row[HousesTable.price],
                type = row[HousesTable.type],
                size = row[HousesTable.size],
                rooms = row[HousesTable.rooms]
            )
        }
    }
}