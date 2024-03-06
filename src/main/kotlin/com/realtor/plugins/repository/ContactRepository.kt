package com.realtor.plugins.repository

import com.realtor.plugins.dao.contact.ContactDao
import com.realtor.plugins.data.model.Contact
import com.realtor.plugins.data.table.ContactTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.InsertStatement

class ContactRepository : ContactDao {
    override suspend fun insert(name: String, email: String, message: String): Contact? {
        var insertStatement: InsertStatement<Number>? = null
        DatabaseFactory.dbQuery {
            insertStatement = ContactTable.insert { contact ->
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

    override suspend fun getContactById(id: Long): Contact? {
        return DatabaseFactory.dbQuery {
            ContactTable.select { ContactTable.id.eq(id) }
                .map {
                    rowToResult(it)
                }.singleOrNull()
        }
    }

    override suspend fun deleteContactById(id: Long): Int? {
        return DatabaseFactory.dbQuery {
            ContactTable.deleteWhere { ContactTable.id.eq(id) }
        }
    }

    override suspend fun updateContactById(id: Long, name: String, email: String, message: String): Int? {
       return DatabaseFactory.dbQuery {
           ContactTable.update({ContactTable.id.eq(id)}){contact ->
               contact[ContactTable.id] = id
               contact[ContactTable.name] = name
               contact[ContactTable.email] = email
               contact[ContactTable.message]= message
           }
       }
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