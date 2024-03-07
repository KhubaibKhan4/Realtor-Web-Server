package com.realtor.plugins.data.model.images

import kotlinx.serialization.Serializable

@Serializable
data class Images(
    val id: Long,
    val houseId: Long,
    val imageUrl: String,
    val description: String,
)
