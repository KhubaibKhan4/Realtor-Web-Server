package com.realtor.plugins.repository

import com.realtor.plugins.dao.ContactDao
import com.realtor.plugins.data.model.Contact

class ContactRepository: ContactDao {
    override suspend fun insert(id: Int, name: String, email: String, message: String): Contact? {
        TODO("Not yet implemented")
    }

    override suspend fun getAllContacts(): List<Contact>? {
        TODO("Not yet implemented")
    }

    override suspend fun getContactById(id: Int): Contact? {
        TODO("Not yet implemented")
    }

    override suspend fun deleteContactById(id: Int): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun updateContactById(id: Int, name: String, email: String, message: String): Int? {
        TODO("Not yet implemented")
    }
}