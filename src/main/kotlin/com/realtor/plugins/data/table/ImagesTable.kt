package com.realtor.plugins.data.table

import com.realtor.plugins.data.table.HousesTable.references
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object ImagesTable : Table(name = "Images") {
    val id: Column<Long> = long("id").uniqueIndex().autoIncrement()
    val imageUrl: Column<String> = varchar("imageUrl", length = 10000)
    val description: Column<String> = varchar("description", length = 1000)
    val houseId: Column<Long> = long("houseId").references(HousesTable.id, onDelete = ReferenceOption.CASCADE)
    override val primaryKey: PrimaryKey = PrimaryKey(id)
}