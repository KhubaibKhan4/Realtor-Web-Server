package com.realtor.plugins.dao

import com.realtor.plugins.data.model.Images

interface ImagesDao {
    suspend fun insert(
        imageUrl: String,
        description: String
    ): Images?
    suspend fun getAllImages(): List<Images>?
    suspend fun getImagesById(id: Long): Images?
    suspend fun deleteImagesById(id: Long): Int?
    suspend fun updateImagesById(id: Long, imageUrl: String, description: String): Int?
}