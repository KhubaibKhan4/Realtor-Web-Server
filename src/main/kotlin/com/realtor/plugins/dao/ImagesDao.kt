package com.realtor.plugins.dao

import com.realtor.plugins.data.model.Images

interface ImagesDao {
    suspend fun insert(
        imageUrl: String,
        description: String
    ): Images?
    suspend fun getAllImages(): List<Images>?
    suspend fun getImagesById(id: Int): Images?
    suspend fun deleteImagesById(id: Int): Int?
    suspend fun updateImagesById(id: Int, imageUrl: String, description: String): Int?
}