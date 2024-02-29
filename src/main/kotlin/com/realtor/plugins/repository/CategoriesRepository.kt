package com.realtor.plugins.repository

import com.realtor.plugins.dao.CategoriesDao
import com.realtor.plugins.data.model.Categories
import com.realtor.plugins.data.model.CategoryWithHouses
import com.realtor.plugins.data.model.Houses
import com.realtor.plugins.data.table.CategoriesTable
import com.realtor.plugins.data.table.HousesTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.InsertStatement

class CategoriesRepository : CategoriesDao {
    override suspend fun insert(name: String, priority: Int): Categories? {
        var statement: InsertStatement<Number>? = null
        DatabaseFactory.dbQuery {
            statement = CategoriesTable.insert { category ->
                category[CategoriesTable.name] = name
                category[CategoriesTable.priority] = priority
            }
        }
        return rowToCategory(statement?.resultedValues?.get(0) !!)
    }

    override suspend fun getAllCategories(): List<Categories> =
        DatabaseFactory.dbQuery {
            CategoriesTable.selectAll().mapNotNull {
                    rowToCategory(it)
                }
        }


    override suspend fun getCategoryById(id: Int): Categories? =
        DatabaseFactory.dbQuery {
            CategoriesTable.select { CategoriesTable.id.eq(id) }
                .map {
                    rowToCategory(it)
                }.singleOrNull()
        }


    override suspend fun deleteCategoryById(id: Int): Int =
        DatabaseFactory.dbQuery {
            CategoriesTable.deleteWhere { CategoriesTable.id.eq(id) }
        }

    override suspend fun updateCategory(id: Int, name: String, priority: String): Int =
        DatabaseFactory.dbQuery {
            CategoriesTable.update({ CategoriesTable.id.eq(id) }) { category ->
                category[CategoriesTable.name] = name
                category[CategoriesTable.id] = id
            }
        }

    override suspend fun getCategoryWithHouses(): List<CategoryWithHouses> {
        return DatabaseFactory.dbQuery {
            CategoriesTable.selectAll().map {categoryRow ->
                val category = rowToCategory(categoryRow)
                val houses = HousesTable.select { HousesTable.categoryId eq category?.id!! }.map {houseRow ->
                    rowToHouse(houseRow)
                }
                CategoryWithHouses(category!!,houses)
            }
        }
    }
    private fun rowToHouse(row: ResultRow): Houses {
        return Houses(
            id = row[HousesTable.id],
            categoryId = row[HousesTable.categoryId],
            title = row[HousesTable.title],
            price = row[HousesTable.price],
            type = row[HousesTable.type],
            size = row[HousesTable.size],
            rooms = row[HousesTable.rooms]
        )
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