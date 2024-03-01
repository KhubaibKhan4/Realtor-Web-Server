package com.realtor.plugins.dao.house

import com.realtor.plugins.data.table.HouseRow
import com.realtor.plugins.data.table.HouseTable
import com.realtor.plugins.repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class HouseDaoImpl : HouseDao {
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
        return dbQuery {
            HouseTable
                .select { HouseTable.categoryId eq categoryId }
                .orderBy(column = HouseTable.createdAt, order = SortOrder.DESC)
                .limit(n = pageSize, offset = ((pageNumber - 1) * pageSize).toLong())
                .map { toHouseRow(it) }
        }
    }

    override suspend fun getHouse(houseId: Long): HouseRow? {
        return dbQuery {
            HouseTable
                .select { HouseTable.houseId eq houseId }
                .singleOrNull()
                ?.let { toHouseRow(it) }
        }
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
    private fun toHouseRow(row: ResultRow): HouseRow {
        return HouseRow(
            houseId = row[HouseTable.houseId],
            title = row[HouseTable.title],
            imageUrl = row[HouseTable.imageUrl],
            address = row[HouseTable.address],
            type = row[HouseTable.type],
            size = row[HouseTable.size],
            rooms = row[HouseTable.rooms],
            categoryId = row[HouseTable.categoryId],
            createdAt = row[HouseTable.createdAt].toString()
        )
    }
}