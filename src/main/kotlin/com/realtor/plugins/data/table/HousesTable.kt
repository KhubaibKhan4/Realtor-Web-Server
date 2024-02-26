package com.realtor.plugins.data.table

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object HousesTable : Table() {
    val id: Column<Int> = integer("id").autoIncrement()
    val title: Column<String> = varchar("title", length = 1500)
    val price: Column<String> = varchar("price", length = 500)
    val type: Column<String> = varchar("type", length = 500)
    val size: Column<String> = varchar("size", length = 500)
    val rooms: Column<Int> = integer("rooms")

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}