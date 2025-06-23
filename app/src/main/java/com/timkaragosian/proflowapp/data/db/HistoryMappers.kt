package com.timkaragosian.proflowapp.data.db

import com.timkaragosian.proflowapp.domain.model.HistoryEntry

fun HistoryEntity.asDomain() = HistoryEntry(id = id, inputText = inputText, timeEpochSec = time)

fun HistoryEntry.asEntity() = HistoryEntity(id = id, inputText = inputText, time = timeEpochSec)