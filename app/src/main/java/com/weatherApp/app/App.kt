package com.weatherApp.app

import android.app.Application
import android.content.Context
import com.weatherApp.di.apiModule
import com.weatherApp.di.networkModule
import com.weatherApp.di.repositoryModule
import com.weatherApp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

import org.koin.core.logger.Level


class App:Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            androidLogger(Level.DEBUG)
            modules(listOf(viewModelModule,repositoryModule,networkModule, apiModule))
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

    }
}