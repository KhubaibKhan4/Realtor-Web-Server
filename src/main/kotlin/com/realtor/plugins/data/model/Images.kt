package com.realtor.plugins.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Images(
    val imageUrl: String,
    val description: String,
)
