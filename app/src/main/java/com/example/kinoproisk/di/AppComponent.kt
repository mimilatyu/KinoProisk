package com.example.kinoproisk.di

import com.example.kinoproisk.di.modules.DatabaseModule
import com.example.kinoproisk.di.modules.DomainModule
import com.example.kinoproisk.di.modules.RemoteModule
import com.example.kinoproisk.viewmodel.HomeFragmentViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(

    modules = [
        RemoteModule::class,
        DatabaseModule::class,
        DomainModule::class
    ]
)
interface AppComponent {
    fun inject(homeFragmentViewModel: HomeFragmentViewModel)
}