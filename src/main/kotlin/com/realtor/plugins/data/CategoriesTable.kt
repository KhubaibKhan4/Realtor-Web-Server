package com.realtor.plugins.data

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object CategoriesTable : Table() {

    val id: Column<Int> = integer("id").autoIncrement()
    val name: Column<String> = varchar("name", length = 51)
    val priority: Column<Int> = integer("priority")

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}