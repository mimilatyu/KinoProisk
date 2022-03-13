package com.example.kinoproisk

import android.app.Application
import com.example.kinoproisk.di.AppComponent
import com.example.kinoproisk.di.DaggerAppComponent
import com.example.kinoproisk.di.modules.DatabaseModule
import com.example.kinoproisk.di.modules.DomainModule
import com.example.remote_module.DaggerRemoteComponent


class App : Application() {
    lateinit var dagger: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this

        val remoteProvider = DaggerRemoteComponent.create()
        dagger = DaggerAppComponent.builder()
            .remoteProvider(remoteProvider)
            .databaseModule(DatabaseModule())
            .domainModule(DomainModule(this))
            .build()

    }

    companion object {
        lateinit var instance: App
            private set
    }
}