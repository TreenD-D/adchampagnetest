package ru.adchampagne.test.di

import pro.appcraft.lib.navigation.AppRouter
import com.github.terrakok.cicerone.Cicerone
import org.koin.dsl.module

internal val navigationModule = module {
    val cicerone: Cicerone<AppRouter> = Cicerone.create(AppRouter())
    single { cicerone.router }
    single { cicerone.getNavigatorHolder() }
}