package com.realtor.plugins.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CategoryWithImages(
    val category: Categories,
    val images: List<Images>
)
