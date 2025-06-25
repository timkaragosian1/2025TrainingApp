package com.timkaragosian.proflowapp.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timkaragosian.proflowapp.domain.model.HistoryEntry
import com.timkaragosian.proflowapp.domain.usecase.history.ObserveHistoryUseCase
import com.timkaragosian.proflowapp.domain.usecase.history.SaveHistoryUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HistoryViewModel(
    observeHistory: ObserveHistoryUseCase,
):ViewModel() {
    val history: StateFlow<List<HistoryEntry>> = observeHistory()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
}