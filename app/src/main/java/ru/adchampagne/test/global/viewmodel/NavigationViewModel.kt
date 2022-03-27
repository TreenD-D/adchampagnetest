package ru.adchampagne.test.global.viewmodel

import com.github.terrakok.cicerone.androidx.AppScreen
import pro.appcraft.lib.navigation.AppRouter
import pro.appcraft.lib.navigation.FlowNavigationViewModel

class NavigationViewModel(appRouter: AppRouter) : FlowNavigationViewModel(appRouter) {
    fun startFlow(screen: AppScreen) = router.startFlow(screen)

    fun replaceFlow(screen: AppScreen) = router.replaceFlow(screen)

    fun newRootFlow(screen: AppScreen) = router.newRootFlow(screen)

    fun finishFlow() = router.finishFlow()

    fun navigateTo(screen: AppScreen) = router.navigateTo(screen)

    fun exit() = router.exit()
}
