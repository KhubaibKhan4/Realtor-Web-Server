package com.realtor.plugins.repository.images

import com.realtor.plugins.dao.images.ImagesDao
import com.realtor.plugins.data.model.images.Images
import com.realtor.plugins.data.table.images.ImagesTable
import com.realtor.domain.local.DatabaseFactory
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.InsertStatement

class ImagesRepository : ImagesDao {
    override suspend fun insert(houseId: Long, imageUrl: String, description: String): Images? {
        var statement: InsertStatement<Number>? = null
        DatabaseFactory.dbQuery {
            statement = ImagesTable.insert { image ->
                image[ImagesTable.houseId] = houseId
                image[ImagesTable.imageUrl] = imageUrl
                image[ImagesTable.description] = description
            }
        }
        return rowToResult(statement?.resultedValues?.get(0)!!)
    }

    override suspend fun getAllImages(): List<Images>? {
        return DatabaseFactory.dbQuery {
            ImagesTable.selectAll()
                .mapNotNull {
                    rowToResult(it)
                }
        }
    }

    override suspend fun getImagesById(id: Long): Images? {
        return DatabaseFactory.dbQuery {
            ImagesTable.select { ImagesTable.id.eq(id) }
                .map { rowToResult(it) }
                .singleOrNull()
        }
    }

    override suspend fun getImagesListBYHouseId(id: Long): List<Images> {
        return DatabaseFactory.dbQuery {
            ImagesTable.select {
                ImagesTable.houseId.eq(id)
            }.mapNotNull {
                rowToResult(it)
            }
        }
    }

    override suspend fun deleteImagesById(id: Long): Int? {
        return DatabaseFactory.dbQuery {
            ImagesTable.deleteWhere { ImagesTable.id.eq(id) }
        }
    }

    override suspend fun updateImagesById(id: Long, houseId: Long, imageUrl: String, description: String): Int? {
        return DatabaseFactory.dbQuery {
            ImagesTable.update({ ImagesTable.id.eq(id) }) { image ->
                image[ImagesTable.id] = id
                image[ImagesTable.houseId] = houseId
                image[ImagesTable.imageUrl] = imageUrl
                image[ImagesTable.description] = description
            }
        }
    }

    fun rowToResult(row: ResultRow): Images? {
        if (row == null) {
            return null
        } else {
            return Images(
                id = row[ImagesTable.id],
                houseId = row[ImagesTable.houseId],
                imageUrl = row[ImagesTable.imageUrl],
                description = row[ImagesTable.description]
            )
        }
    }
}