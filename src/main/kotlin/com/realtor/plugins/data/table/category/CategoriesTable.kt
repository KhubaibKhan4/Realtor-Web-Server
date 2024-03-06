package com.realtor.plugins.data.table.category

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object CategoriesTable : Table(name = "Categories") {

    val id: Column<Long> = long("id").autoIncrement()
    val name: Column<String> = varchar("name", length = 51).uniqueIndex()
    val priority: Column<Int> = integer("priority")
    val totalHouses: Column<Int> = integer("totalHouses")

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}