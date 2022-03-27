package ru.adchampagne.test.feature.profile

import com.github.terrakok.cicerone.androidx.AppScreen
import ru.adchampagne.test.Screens
import ru.adchampagne.test.global.ui.fragment.FlowFragment

class ProfileFlowFragment : FlowFragment() {
    override val launchScreen: AppScreen? by lazy {
        Screens.Screen.profile()
    }
}
