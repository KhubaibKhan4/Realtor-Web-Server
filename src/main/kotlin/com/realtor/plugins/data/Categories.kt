package com.realtor.plugins.data

import kotlinx.serialization.Serializable

@Serializable
data class Categories(
    val id: Int,
    val name: String,
    val priority: Int
)
