package com.example.kinoproisk.di


import com.example.kinoproisk.di.modules.DatabaseModule
import com.example.kinoproisk.di.modules.DomainModule
import com.example.kinoproisk.viewmodel.HomeFragmentViewModel
import com.example.kinoproisk.viewmodel.SettingsFragmentViewModel
import com.example.remote_module.RemoteProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [RemoteProvider::class],
    modules = [
        DatabaseModule::class,
        DomainModule::class
    ]
)
interface AppComponent {
    fun inject(homeFragmentViewModel: HomeFragmentViewModel)
    fun inject(settingsFragmentViewModel: SettingsFragmentViewModel)
}