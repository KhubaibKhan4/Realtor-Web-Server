package com.realtor.plugins.data.table

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object ImagesTable : Table() {
    val imageUrl: Column<String> = varchar("imageUrl", length = 2000)
    val description: Column<String> = varchar("description", length = 1000)
}