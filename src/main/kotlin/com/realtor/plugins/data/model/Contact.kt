package com.realtor.plugins.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Contact(
    val id: Long,
    val name: String,
    val email: String,
    val message: String
)
