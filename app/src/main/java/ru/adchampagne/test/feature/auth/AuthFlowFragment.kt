package ru.adchampagne.test.feature.auth

import com.github.terrakok.cicerone.androidx.AppScreen
import ru.adchampagne.test.Screens
import ru.adchampagne.test.global.ui.fragment.FlowFragment

class AuthFlowFragment : FlowFragment() {
    override val launchScreen: AppScreen? by lazy {
        Screens.Screen.auth()
    }
}