package com.realtor.plugins.data.model

import kotlinx.serialization.Serializable

@Serializable
data class House(
    val houseId: Long,
    val houseTitle: String,
    val imageUrl: String,
    val createdAt: String,
    val categoryId: Long,
    val address: String,
    val type: String,
    val size: String,
    val rooms: String
)
