package com.timkaragosian.proflowapp.di

import com.timkaragosian.proflowapp.domain.usecase.history.ObserveHistoryUseCase
import com.timkaragosian.proflowapp.domain.usecase.history.SaveHistoryUseCase
import com.timkaragosian.proflowapp.domain.usecase.home.AddItemUseCase
import com.timkaragosian.proflowapp.domain.usecase.home.GetTodoUseCase
import com.timkaragosian.proflowapp.presentation.history.HistoryViewModel
import com.timkaragosian.proflowapp.presentation.home.HomeViewModel
import io.mockk.mockk
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val testModule = module {
    single<GetTodoUseCase> { mockk(relaxed = true) }
    single<AddItemUseCase> { mockk(relaxed = true) }
    single<SaveHistoryUseCase> { mockk(relaxed = true) }
    single<ObserveHistoryUseCase> { mockk(relaxed = true) }

    viewModel {
        HomeViewModel(
            getTodo = get(),
            addItem = get(),
            saveHistory = get()
        )
    }

    viewModel {
        HistoryViewModel(
            observeHistory = get()
        )
    }
}