package com.realtor.plugins.data.table

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object ContactTable : Table(name = "Contact") {
    val id: Column<Int> = integer("id").autoIncrement()
    val name: Column<String> = varchar("name", length = 500)
    val email: Column<String> = varchar("email", length = 500)
    val message: Column<String> = varchar("message", length = 2000)
    override val primaryKey: PrimaryKey = PrimaryKey(id)
}