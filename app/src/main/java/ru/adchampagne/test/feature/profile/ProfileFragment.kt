package ru.adchampagne.test.feature.profile

import android.os.Bundle
import android.view.View
import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.appcraft.lib.utils.common.addSystemWindowInsetToPadding
import ru.adchampagne.test.Screens
import ru.adchampagne.test.databinding.FragmentProfileBinding
import ru.adchampagne.test.global.ui.fragment.BaseFragment
import ru.adchampagne.test.global.utils.BindingProvider
import ru.adchampagne.test.global.utils.viewObserve

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    private val viewModel: ProfileViewModel by viewModel()

    override val bindingProvider: BindingProvider<FragmentProfileBinding> =
        FragmentProfileBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.addSystemWindowInsetToPadding(top = true, bottom = true)
        initListeners()
        initObservers()
    }

    private fun initListeners() {

    }

    private fun initObservers() {
        viewObserve(viewModel.logoutLiveData) { isLoggedOut ->
            if (isLoggedOut) {
                navigation.newRootFlow(Screens.Flow.auth())
            }
        }
    }
}
