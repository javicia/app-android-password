package com.javier.passlive.Encryption

import android.app.Application
import org.koin.core.context.startKoin


class MyApplication : Application {
    override fun onCreate() {
        super.onCreate()
        //start Koin
        startKoin(listOf(moduleApp))
    }
}