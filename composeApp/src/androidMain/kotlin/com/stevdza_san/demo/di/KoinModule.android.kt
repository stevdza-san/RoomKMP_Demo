package com.stevdza_san.demo.di

import com.stevdza_san.demo.database.getDatabaseBuilder
import org.koin.dsl.module

actual val targetModule = module {
    single { getDatabaseBuilder(context = get()) }
}