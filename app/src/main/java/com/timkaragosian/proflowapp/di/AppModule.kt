package com.timkaragosian.proflowapp.di

import androidx.room.Room
import com.timkaragosian.proflowapp.data.db.AppDatabase
import com.timkaragosian.proflowapp.data.network.HttpClientProvider
import com.timkaragosian.proflowapp.data.network.TodoApi
import com.timkaragosian.proflowapp.data.repository.HistoryRepositoryImpl
import com.timkaragosian.proflowapp.data.repository.TodoRepositoryImpl
import com.timkaragosian.proflowapp.data.resourcesprovider.FlowAppResourceProvider
import com.timkaragosian.proflowapp.data.resourcesprovider.ResourceProvider
import com.timkaragosian.proflowapp.domain.auth.AuthRepository
import com.timkaragosian.proflowapp.domain.auth.FakeAuthRepository
import com.timkaragosian.proflowapp.domain.usecase.flowresult.CompleteTodoTaskUseCase
import com.timkaragosian.proflowapp.domain.usecase.flowresult.DeleteTodoTaskUseCase
import com.timkaragosian.proflowapp.domain.usecase.home.GetTodoUseCase
import com.timkaragosian.proflowapp.domain.usecase.history.ObserveHistoryUseCase
import com.timkaragosian.proflowapp.domain.usecase.history.SaveHistoryUseCase
import com.timkaragosian.proflowapp.domain.usecase.home.AddItemUseCase
import com.timkaragosian.proflowapp.presentation.flowresult.FlowResultViewModel
import com.timkaragosian.proflowapp.presentation.history.HistoryViewModel
import com.timkaragosian.proflowapp.presentation.home.HomeViewModel
import com.timkaragosian.proflowapp.presentation.signin.SignInViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {
    single { HttpClientProvider.instance }

    single { TodoApi(get()) }
    single { TodoRepositoryImpl(get()) }
    single { GetTodoUseCase(get()) }
    single { DeleteTodoTaskUseCase(get()) }
    single { CompleteTodoTaskUseCase(get()) }
    viewModel { HomeViewModel(get(),get(),get() ) }
    viewModel { FlowResultViewModel(get(),get(), get() ) }

    single { HistoryRepositoryImpl(get()) }
    single { SaveHistoryUseCase(get()) }
    single { ObserveHistoryUseCase(get()) }
    single { AddItemUseCase(get()) }
    single { FlowAppResourceProvider(androidContext()) }
    viewModel { HistoryViewModel(get() ) }

    single<AuthRepository> { FakeAuthRepository() }
    single<ResourceProvider> { FlowAppResourceProvider(androidContext()) }

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "proflow.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<AppDatabase>().historyDao() }

    viewModel { SignInViewModel(get(), get()) }
}