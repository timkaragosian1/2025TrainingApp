package com.timkaragosian.proflowapp.di

import androidx.room.Room
import com.timkaragosian.proflowapp.BuildConfig
import com.timkaragosian.proflowapp.data.db.AppDatabase
import com.timkaragosian.proflowapp.data.network.HttpClientProvider
import com.timkaragosian.proflowapp.data.network.TodoApi
import com.timkaragosian.proflowapp.data.repository.HistoryRepositoryImpl
import com.timkaragosian.proflowapp.data.repository.TodoRepositoryImpl
import com.timkaragosian.proflowapp.data.resourcesprovider.FlowAppResourceProvider
import com.timkaragosian.proflowapp.data.resourcesprovider.ResourceProvider
import com.timkaragosian.proflowapp.domain.auth.AuthRepository
import com.timkaragosian.proflowapp.domain.auth.FakeAuthRepository
import com.timkaragosian.proflowapp.domain.usecase.GetTodoUseCase
import com.timkaragosian.proflowapp.domain.usecase.ObserveHistoryUseCase
import com.timkaragosian.proflowapp.domain.usecase.SaveHistoryUseCase
import com.timkaragosian.proflowapp.presentation.home.HomeViewModel
import com.timkaragosian.proflowapp.presentation.signin.SignInViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val networkModule = module {
    single { HttpClientProvider.instance }

    single { TodoApi(get()) }
    single { TodoRepositoryImpl(get()) }
    single { GetTodoUseCase(get()) }

    single { HistoryRepositoryImpl(get()) }
    single { SaveHistoryUseCase(get()) }
    single { ObserveHistoryUseCase(get()) }
    single<AuthRepository> { FakeAuthRepository() }
    single<ResourceProvider> {FlowAppResourceProvider(androidContext())}

    viewModel { SignInViewModel(get(), get()) }
    viewModel { HomeViewModel(get(), get(), get()) }
}

val roomModule = module {
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
}