package com.electro.light.app

import android.app.Application
import com.electro.light.app.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class LightApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@LightApp)
            modules(viewModelModule, networkModule, databaseModule, repositoryModule)
        }
    }
}
