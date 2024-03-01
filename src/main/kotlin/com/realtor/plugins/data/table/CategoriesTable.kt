package com.realtor.plugins.data.table

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object CategoriesTable : Table(name = "Categories") {

    val id: Column<Int> = integer("id").autoIncrement()
    val name: Column<String> = varchar("name", length = 51)
    val priority: Column<Int> = integer("priority")

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}