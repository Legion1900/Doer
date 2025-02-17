package com.legion1900.doer

import android.app.Application
import com.legion1900.doer.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DoerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@DoerApp)
            modules(appModule)
        }
    }
}
