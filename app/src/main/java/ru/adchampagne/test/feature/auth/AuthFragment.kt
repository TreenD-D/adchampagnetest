package ru.adchampagne.test.feature.auth

import android.os.Bundle
import android.view.View
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.appcraft.lib.utils.common.addSystemWindowInsetToPadding
import pro.appcraft.lib.utils.common.hideKeyboard
import pro.appcraft.lib.utils.common.setGone
import pro.appcraft.lib.utils.common.setVisible
import ru.adchampagne.test.R
import ru.adchampagne.test.Screens
import ru.adchampagne.test.databinding.FragmentAuthBinding
import ru.adchampagne.test.global.ui.fragment.BaseFragment
import ru.adchampagne.test.global.utils.BindingProvider
import ru.adchampagne.test.global.utils.isValidEmail
import ru.adchampagne.test.global.utils.viewObserve
import ru.adchampagne.test.global.viewmodel.LoadState

class AuthFragment : BaseFragment<FragmentAuthBinding>() {
    private val viewModel: AuthViewModel by viewModel()

    override val bindingProvider: BindingProvider<FragmentAuthBinding> =
        FragmentAuthBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.addSystemWindowInsetToPadding(top = true, bottom = true)
        initListeners()
        initObservers()
    }

    private fun initListeners() {
        binding.authLoginButton.setOnClickListener {
            if(validateMail() && validatePassword()) {
                hideKeyboard()
                viewModel.login(
                    login = binding.authLoginInputView.text?.toString() ?:"",
                    password = binding.authPasswordInputView.text?.toString() ?:""
                )
            }
        }
        binding.authRecoverButton.setOnClickListener {
            if(validateMail()) {
                hideKeyboard()
                viewModel.recover(
                    login = binding.authLoginInputView.text?.toString() ?:""
                )
            }
        }
        binding.authRegisterButton.setOnClickListener {
            if(validateMail() && validatePassword()) {
                hideKeyboard()
                viewModel.register(
                    login = binding.authLoginInputView.text?.toString() ?:"",
                    password = binding.authPasswordInputView.text?.toString() ?:""
                )
            }
        }


    }

    private fun initObservers() {
        viewObserve(viewModel.loadLiveData) { loadState ->
            when (loadState) {
                is LoadState.Loading -> {
                    binding.progressView.setVisible()
                    binding.authLoginButton.isEnabled = false
                    binding.authRecoverButton.isEnabled = false
                    binding.authRegisterButton.isEnabled = false
                }
                is LoadState.Loaded, is LoadState.Error -> {
                    binding.progressView.setGone()
                    binding.authLoginButton.isEnabled = true
                    binding.authRecoverButton.isEnabled = true
                    binding.authRegisterButton.isEnabled = true
                }
            }
        }

        viewObserve(viewModel.loginLiveData){
            navigation.newRootFlow(Screens.Flow.profile())
        }

    }

    private fun validateMail(): Boolean {
        val isMailNotEmpty =
            validateRequiredView(binding.authLoginInputView, binding.authLoginInputLayout)
        val isMailValid = binding.authLoginInputView.text.toString().isValidEmail()
        if (!isMailValid) binding.authLoginInputLayout.error =
            getString(R.string.error_invalid_mail)
        return (isMailNotEmpty && isMailValid)
    }

    private fun validatePassword() =
        validateRequiredView(binding.authPasswordInputView, binding.authPasswordInputLayout)


    private fun validateRequiredView(
        textview: TextInputEditText,
        layout: TextInputLayout
    ): Boolean {
        return if (textview.text.isNullOrBlank()) {
            layout.error = getString(
                R.string.required_field_error
            )
            false
        } else {
            layout.error = null
            true
        }
    }
}