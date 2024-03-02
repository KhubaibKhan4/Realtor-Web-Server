package com.realtor.plugins.data.model

import kotlinx.serialization.Serializable

@Serializable
data class HouseWithImages(
    val houses: Houses,
    val images: List<Images>
)
