package com.realtor.plugins.repository.house

import com.realtor.plugins.data.model.HouseResponse

interface HouseRepository {
    suspend fun createHouse(
        title: String,
        imageUrl: String,
        address: String,
        type: String,
        size: String,
        rooms: String,
        categoryId: Int
    ): HouseResponse

    suspend fun getHouse(houseId: Long): HouseResponse?

    suspend fun deleteHouse(houseId: Long): HouseResponse
}