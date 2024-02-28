package com.realtor.plugins.repository

import com.realtor.plugins.dao.ImagesDao
import com.realtor.plugins.data.model.Images
import com.realtor.plugins.data.table.ImagesTable
import org.jetbrains.exposed.sql.ResultRow

class ImagesRepository : ImagesDao {
    override suspend fun insert(imageUrl: String, description: String): Images? {
        TODO("Not yet implemented")
    }

    override suspend fun getAllImages(): List<Images>? {
        TODO("Not yet implemented")
    }

    override suspend fun getImagesById(id: Int): Images? {
        TODO("Not yet implemented")
    }

    override suspend fun deleteImagesById(id: Int): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun updateImagesById(id: Int): Int? {
        TODO("Not yet implemented")
    }

    fun rowToResult(row: ResultRow): Images? {
        if (row == null) {
            return null
        } else {
            return Images(
                imageUrl = row[ImagesTable.imageUrl],
                description = row[ImagesTable.description]
            )
        }
    }
}