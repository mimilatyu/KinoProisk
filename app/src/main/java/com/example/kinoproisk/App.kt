package com.example.kinoproisk

import android.app.Application
import com.example.kinoproisk.data.MainRepository
import com.example.kinoproisk.domain.Interactor

class App: Application() {
    lateinit var repo: MainRepository
    lateinit var interactor: Interactor

    override fun onCreate() {
        super.onCreate()
        instance = this
        repo = MainRepository()
        interactor = Interactor(repo)
    }

    companion object {
        lateinit var instance: App
        private set
    }
}