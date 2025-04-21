package org.example.llamapp.di

import org.example.llamapp.networking.HomeRepository
import org.example.llamapp.networking.LoginRepository
import org.example.llamapp.networking.SignUpRepository
import org.koin.dsl.module

val provideRepositoryModule = module {
    single<HomeRepository> { HomeRepository(get()) }
    single<LoginRepository> { LoginRepository() }
    single<SignUpRepository> { SignUpRepository() }
}