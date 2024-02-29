package com.realtor.plugins.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Houses(
    val id: Int,
    val categoryId: Int,
    val title: String,
    val price: String,
    val type: String,
    val size: String,
    val rooms: String,
)
