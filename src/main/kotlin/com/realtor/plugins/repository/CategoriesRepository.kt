package com.realtor.plugins.repository

import com.realtor.plugins.dao.CategoriesDao
import com.realtor.plugins.data.Categories

class CategoriesRepository : CategoriesDao {
    override suspend fun insert(name: String, priority: String): Categories? {
        TODO("Not yet implemented")
    }

    override suspend fun getAllCategories(): List<Categories>? {
        TODO("Not yet implemented")
    }

    override suspend fun getCategoryById(id: Int): Categories? {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCategoryById(id: Int): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun updateCategory(id: Int, name: String, priority: String): Int {
        TODO("Not yet implemented")
    }
}