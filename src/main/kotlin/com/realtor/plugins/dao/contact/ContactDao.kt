package com.realtor.plugins.dao.contact

import com.realtor.plugins.data.model.Contact

interface ContactDao {
    suspend fun insert(
        name: String,
        email: String,
        message: String
    ): Contact?

    suspend fun getAllContacts(): List<Contact>?
    suspend fun getContactById(id: Long): Contact?
    suspend fun deleteContactById(id: Long): Int?
    suspend fun updateContactById(id: Long,name: String,email: String,message: String): Int?
}