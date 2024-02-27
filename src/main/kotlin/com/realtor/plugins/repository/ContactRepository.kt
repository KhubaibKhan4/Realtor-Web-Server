package com.realtor.plugins.repository

import com.realtor.plugins.dao.ContactDao
import com.realtor.plugins.data.model.Contact
import com.realtor.plugins.data.table.ContactTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.InsertStatement

class ContactRepository : ContactDao {
    override suspend fun insert(id: Int, name: String, email: String, message: String): Contact? {
        var insertStatement: InsertStatement<Number>? = null
        DatabaseFactory.dbQuery {
            insertStatement = ContactTable.insert { contact ->
                contact[ContactTable.id] = id
                contact[ContactTable.name] = name
                contact[ContactTable.email] = email
                contact[ContactTable.message] = message
            }
        }
        return insertStatement?.resultedValues?.get(0)?.let { rowToResult(it) }
    }

    override suspend fun getAllContacts(): List<Contact>? {
        return DatabaseFactory.dbQuery {
            ContactTable.selectAll().mapNotNull {
                rowToResult(it)
            }
        }
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

     fun rowToResult(row: ResultRow): Contact? {
        if (row == null) {
            return null
        } else {
            return Contact(
                id = row[ContactTable.id],
                name = row[ContactTable.name],
                email = row[ContactTable.email],
                message = row[ContactTable.message]
            )
        }
    }
}