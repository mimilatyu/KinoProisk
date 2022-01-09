package com.example.kinoproisk

import android.app.Application
import com.example.kinoproisk.data.ApiConstants
import com.example.kinoproisk.data.MainRepository
import com.example.kinoproisk.data.TmdbApi
import com.example.kinoproisk.di.DI
import com.example.kinoproisk.domain.Interactor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            //Прикрепляем контекст
            androidContext(this@App)
            //(Опционально) подключаем зависимость
            androidLogger()
            //Инициализируем модули
            modules(listOf(DI.mainModule))
        }
    }
}