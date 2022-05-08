package com.example.kinoproisk

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.example.kinoproisk.di.AppComponent
import com.example.kinoproisk.di.DaggerAppComponent
import com.example.kinoproisk.di.modules.DatabaseModule
import com.example.kinoproisk.di.modules.DomainModule
import com.example.kinoproisk.view.notification.NotificationConstants.CHANNEL_ID
import com.example.remote_module.DaggerRemoteComponent


class App : Application() {
    lateinit var dagger: AppComponent
    var isPromoShown = false

    override fun onCreate() {
        super.onCreate()
        instance = this

        val remoteProvider = DaggerRemoteComponent.create()
        dagger = DaggerAppComponent.builder()
            .remoteProvider(remoteProvider)
            .databaseModule(DatabaseModule())
            .domainModule(DomainModule(this))
            .build()

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "WatchLaterChannel"
            val descriptionText = "KinoProisk notification Channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            mChannel.description = descriptionText
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }

    }

    companion object {
        lateinit var instance: App
            private set
    }
}