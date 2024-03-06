package com.realtor.plugins.data.model.category

import kotlinx.serialization.Serializable

@Serializable
data class Categories(
    val id: Long,
    val name: String,
    val priority: Int
)
