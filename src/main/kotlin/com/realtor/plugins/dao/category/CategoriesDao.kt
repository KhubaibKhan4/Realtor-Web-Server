package com.realtor.plugins.dao.category

import com.realtor.plugins.data.model.category.Categories

interface CategoriesDao {
    suspend fun insert(
        name: String,
        priority: Int,
    ): Categories?

    suspend fun getAllCategories(): List<Categories>?
    suspend fun getCategoryById(id: Long): Categories?
    suspend fun getCategoryIdByName(name: String): Long?
    suspend fun deleteCategoryById(id: Long): Int?
    suspend fun updateCategory(id: Long, name: String, priority: String): Int
}