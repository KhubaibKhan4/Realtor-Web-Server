package com.realtor.plugins.dao.house

import com.realtor.plugins.data.table.HouseRow

interface HouseDao {
    suspend fun createHouse(
        title: String,
        imageUrl: String,
        address: String,
        type: String,
        size: String,
        rooms: String,
        categoryId: Int
    ): Boolean

    suspend fun getHousesByCategory(
        categoryId: Int,
        pageNumber: Int,
        pageSize: Int
    ): List<HouseRow>

    suspend fun getHouse(houseId: Long): HouseRow?

    suspend fun updateHouse(
        houseId: Long,
        title: String,
        imageUrl: String,
        address: String,
        type: String,
        size: String,
        rooms: String,
        categoryId: Int
    ): Boolean

    suspend fun deleteHouse(houseId: Long): Boolean
}
