package com.realtor.plugins.data.table

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime

object HouseTable : Table(name = "house") {
    val houseId = long(name = "house_id").uniqueIndex()
    val title = varchar(name = "title", length = 600)
    val imageUrl = varchar(name = "image_url", length = 600)
    val address = varchar(name = "address", length = 600)
    val type = varchar(name = "type", length = 600)
    val size = varchar(name = "size", length = 600)
    val rooms = varchar(name = "rooms", length = 600)
    val categoryId =  integer(name = "user_id").references(ref = CategoriesTable.id, onDelete = ReferenceOption.CASCADE)
    val createdAt = datetime(name = "created_at").defaultExpression(defaultValue = CurrentDateTime)
}