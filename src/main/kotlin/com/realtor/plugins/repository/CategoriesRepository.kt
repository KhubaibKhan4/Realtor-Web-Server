package com.realtor.plugins.repository

import com.realtor.plugins.dao.CategoriesDao
import com.realtor.plugins.data.Categories
import com.realtor.plugins.data.CategoriesTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.InsertStatement
import javax.xml.crypto.Data

class CategoriesRepository : CategoriesDao {
    override suspend fun insert(name: String, priority: Int): Categories? {
        var statement: InsertStatement<Number>? = null
        DatabaseFactory.dbQuery {
            statement = CategoriesTable.insert { category ->
                category[CategoriesTable.name] = name
                category[CategoriesTable.priority] = priority
            }
        }
        return statement?.resultedValues?.get(0)?.let { rowToCategory(it) }
    }

    override suspend fun getAllCategories(): List<Categories> =
        DatabaseFactory.dbQuery {
            CategoriesTable.selectAll()
                .mapNotNull {
                    rowToCategory(it)
                }
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

    private fun rowToCategory(row: ResultRow): Categories? {
        if (row == null) {
            return null
        } else {
            return Categories(
                name = row[CategoriesTable.name],
                id = row[CategoriesTable.id],
                priority = row[CategoriesTable.priority]
            )
        }
    }
}