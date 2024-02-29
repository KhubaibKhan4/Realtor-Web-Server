package com.realtor.plugins.data.table

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object ImagesTable : Table() {
    val id: Column<Int> = integer("id").autoIncrement()
    val categoryId: Column<Int> = integer("category_id").references(CategoriesTable.id)
    val imageUrl: Column<String> = varchar("imageUrl", length = 10000)
    val description: Column<String> = varchar("description", length = 1000)
    override val primaryKey: PrimaryKey = PrimaryKey(id)
}