package com.realtor.plugins.data.model.category

import com.realtor.plugins.data.model.HouseWithImages
import kotlinx.serialization.Serializable

@Serializable
data class CategoryWithHouses(
    val category: Categories,
    val houses: List<HouseWithImages>
)
