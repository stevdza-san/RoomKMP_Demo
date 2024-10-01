package com.stevdza_san.demo.di

import com.stevdza_san.demo.data.getRoomDatabase
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.core.module.dsl.*
import com.stevdza_san.demo.presentation.screen.home.HomeViewModel
import com.stevdza_san.demo.presentation.screen.manage.ManageViewModel
import com.stevdza_san.demo.presentation.screen.details.DetailsViewModel

expect val targetModule: Module

val sharedModule = module {
    single { getRoomDatabase(get()) }
    viewModelOf(::HomeViewModel)
    viewModelOf(::ManageViewModel)
    viewModelOf(::DetailsViewModel)
}

fun initializeKoin(
    config: (KoinApplication.() -> Unit)? = null
) {
    startKoin {
        config?.invoke(this)
        modules(targetModule, sharedModule)
    }
}