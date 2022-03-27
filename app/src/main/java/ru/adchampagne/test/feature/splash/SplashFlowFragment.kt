package ru.adchampagne.test.feature.splash

import ru.adchampagne.test.Screens
import ru.adchampagne.test.global.ui.fragment.FlowFragment
import com.github.terrakok.cicerone.androidx.AppScreen

class SplashFlowFragment : FlowFragment() {
    override val launchScreen = Screens.Screen.splash()
}
