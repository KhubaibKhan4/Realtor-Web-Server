package com.realtor.plugins.dao.images

import com.realtor.plugins.data.model.HouseWithImages
import com.realtor.plugins.data.model.Images

interface ImagesDao {
    suspend fun insert(
        houseId: Long,
        imageUrl: String,
        description: String
    ): Images?
    suspend fun getAllImages(): List<Images>?
    suspend fun getImagesById(id: Long): Images?
    suspend fun getImagesListBYHouseId(id: Long): List<Images>
    suspend fun deleteImagesById(id: Long): Int?
    suspend fun updateImagesById(id: Long,houseId: Long, imageUrl: String, description: String): Int?
}