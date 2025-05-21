package com.example.smartcomposter

import android.app.Application
import com.example.smartcomposter.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SmartComposterApp:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@SmartComposterApp)
            modules(appModule)
        }
    }
}