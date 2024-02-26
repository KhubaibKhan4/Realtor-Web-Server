package com.realtor.plugins.repository

import com.realtor.plugins.dao.HousesDao
import com.realtor.plugins.data.model.Houses

class HousesRepository: HousesDao {
    override suspend fun insert(title: String, price: String, type: String, size: String, rooms: Int): Houses? {
        TODO("Not yet implemented")
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