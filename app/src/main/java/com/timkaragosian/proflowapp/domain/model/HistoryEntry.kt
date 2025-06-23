package com.timkaragosian.proflowapp.domain.model

data class HistoryEntry(
    val id: Long,
    val inputText: String,
    val timeEpochSec: Long,
)
