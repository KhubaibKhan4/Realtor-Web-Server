package com.realtor.plugins.repository.house

import com.realtor.plugins.dao.house.HouseDao
import com.realtor.plugins.data.model.HouseResponse

class HouseRepositoryImpl(private val houseDao: HouseDao) : HouseRepository {
    override suspend fun createHouse(
        title: String,
        imageUrl: String,
        address: String,
        type: String,
        size: String,
        rooms: String,
        categoryId: Int
    ): HouseResponse {
        val houseId = houseDao.createHouse(
            title = title,
            imageUrl = imageUrl,
            address = address,
            type = type,
            size = size,
            rooms = rooms,
            categoryId = categoryId
        )

        return if (houseId != null) {
            HouseResponse(success = true, houseId = houseId.toString().toLong())
        } else {
            HouseResponse(success = false, message = "House could not be inserted in the database")
        }
    }

    override suspend fun getHouse(houseId: Long): HouseResponse? {
        val house = houseDao.getHouse(houseId)
        return if (house != null) {
            HouseResponse(success = true, houseId = house.houseId, message = "House found")
        } else {
            HouseResponse(success = false, message = "House not found")
        }
    }


    override suspend fun deleteHouse(houseId: Long): HouseResponse {
        val isDeleted = houseDao.deleteHouse(houseId)
        return if (isDeleted) {
            HouseResponse(success = true)
        } else {
            HouseResponse(success = false, message = "House could not be deleted from the database")
        }
    }
}

