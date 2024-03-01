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
@Serializable
data class HouseResponse(
    val success: Boolean,
    val houseId: Long? = null,
    val message: String? = null
)

@Serializable
data class HousesResponse(
    val success: Boolean,
    val houses: List<House> = emptyList(),
    val message: String? = null
)