package com.timkaragosian.proflowapp.data.network

import kotlinx.serialization.Serializable

@Serializable
data class TodoDto(
    val id: String,
    val todo:String,
    val completed: Boolean,
    val timestamp: Long
)
