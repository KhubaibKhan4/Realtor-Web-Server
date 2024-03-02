package com.realtor.plugins.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CategoryWithHouses(
    val category: Categories,
    val houses: List<HouseWithImages>
)
