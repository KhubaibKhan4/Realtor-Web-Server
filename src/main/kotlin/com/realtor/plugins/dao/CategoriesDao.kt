package com.realtor.plugins.dao

import com.realtor.plugins.data.Categories

interface CategoriesDao {
    suspend fun insert(
        name: String,
        priority: Int
    ): Categories?

    suspend fun getAllCategories(): List<Categories>?
    suspend fun getCategoryById(id: Int): Categories?
    suspend fun deleteCategoryById(id: Int): Int?
    suspend fun updateCategory(id: Int, name: String, priority: String): Int
}