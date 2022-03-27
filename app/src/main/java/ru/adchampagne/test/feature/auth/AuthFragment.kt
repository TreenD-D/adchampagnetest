package ru.adchampagne.test.feature.auth

import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.adchampagne.test.databinding.FragmentAuthBinding
import ru.adchampagne.test.global.ui.fragment.BaseFragment
import ru.adchampagne.test.global.utils.BindingProvider

class AuthFragment : BaseFragment<FragmentAuthBinding>() {
    private val viewModel: AuthViewModel by viewModel()

    override val bindingProvider: BindingProvider<FragmentAuthBinding> =
        FragmentAuthBinding::inflate
}