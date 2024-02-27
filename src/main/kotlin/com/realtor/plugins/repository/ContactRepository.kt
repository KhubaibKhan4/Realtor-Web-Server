package com.realtor.plugins.repository

import com.realtor.plugins.dao.ContactDao
import com.realtor.plugins.data.model.Contact
import com.realtor.plugins.data.table.ContactTable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.statements.InsertStatement

class ContactRepository : ContactDao {
    override suspend fun insert(id: Int, name: String, email: String, message: String): Contact? {
        val insertStatement: InsertStatement<Number>? = null
        DatabaseFactory.dbQuery {
            insertStatement = ContactTable.insert { contact ->
                contact[ContactTable.id] = id
                contact[ContactTable.name] = name
                contact[ContactTable.email] = email
                contact[ContactTable.message] = message
            }
        }
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