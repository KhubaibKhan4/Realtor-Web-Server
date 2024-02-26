package com.realtor.plugins.dao

import com.realtor.plugins.data.model.Houses

interface HousesDao {
    suspend fun insert(
        title: String,
        price: String,
        type: String,
        size: String,
        rooms: Int
    ): Houses?

    suspend fun getHouses(): List<Houses>?
}