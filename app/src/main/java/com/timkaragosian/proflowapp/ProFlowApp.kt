package com.timkaragosian.proflowapp

import android.app.Application
import com.timkaragosian.proflowapp.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class ProFlowApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ProFlowApp)
            modules(
                appModules
            )
        }
    }
}