package org.example.llamapp.di

import org.example.llamapp.ui.DuringLoading.DuringLoadingViewModel
import org.example.llamapp.ui.Home.HomeViewModel
import org.example.llamapp.ui.Login.LoginViewModel
import org.example.llamapp.ui.SignUp.SignUpViewModel
import org.koin.dsl.module

val provideviewModelModule = module {
    single<HomeViewModel> { HomeViewModel(get()) }
    single<LoginViewModel> { LoginViewModel(get()) }
    single<SignUpViewModel> { SignUpViewModel(get()) }
    single<DuringLoadingViewModel> { DuringLoadingViewModel() }
}