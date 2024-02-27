package com.realtor.plugins.dao

import com.realtor.plugins.data.model.Contact

interface ContactDao {
    suspend fun insert(
        id: Int,
        name: String,
        email: String,
        message: String
    ): Contact?
}