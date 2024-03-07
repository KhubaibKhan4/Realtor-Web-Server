package com.realtor.plugins.data.model.house

import com.realtor.plugins.data.model.images.Images
import kotlinx.serialization.Serializable

@Serializable
data class HouseWithImages(
    val houses: Houses,
    val images: List<Images>
)
