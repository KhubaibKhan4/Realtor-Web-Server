package com.realtor.plugins.dao

import com.realtor.plugins.data.model.Contact

interface ContactDao {
    suspend fun insert(
        name: String,
        email: String,
        message: String
    ): Contact?

    suspend fun getAllContacts(): List<Contact>?
    suspend fun getContactById(id: Int): Contact?
    suspend fun deleteContactById(id: Int): Int?
    suspend fun updateContactById(id: Int,name: String,email: String,message: String): Int?
}