package com.realtor.plugins.repository

import com.realtor.plugins.dao.HousesDao
import com.realtor.plugins.data.model.Houses
import com.realtor.plugins.data.table.HousesTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
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
        return DatabaseFactory.dbQuery {
            HousesTable.select {
                HousesTable.id.eq(id)
            }.map {
                rowToResult(it)
            }.singleOrNull()
        }
    }

    override suspend fun deleteHouseById(id: Int): Int? {
        return DatabaseFactory.dbQuery {
            HousesTable.deleteWhere {
                HousesTable.id.eq(id)
            }
        }
    }

    override suspend fun updateHouseById(
        id: Int,
        title: String,
        price: String,
        type: String,
        size: String,
        rooms: Int
    ): Int? {
        return DatabaseFactory.dbQuery {
            HousesTable.update({ HousesTable.id.eq(id) }) { house ->
                house[HousesTable.id] = id
                house[HousesTable.title] = title
                house[HousesTable.price] = price
                house[HousesTable.type] = type
                house[HousesTable.size] = size
                house[HousesTable.rooms] = rooms
            }
        }
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