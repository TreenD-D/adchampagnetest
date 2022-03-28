package ru.adchampagne.test.feature.auth

import androidx.lifecycle.viewModelScope
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.adchampagne.domain.interactor.auth.LoginUseCase
import ru.adchampagne.domain.interactor.auth.RecoverUseCase
import ru.adchampagne.domain.interactor.auth.RegisterUseCase
import ru.adchampagne.domain.model.auth.AuthParams
import ru.adchampagne.test.R
import ru.adchampagne.test.global.dispatcher.error.ErrorHandler
import ru.adchampagne.test.global.dispatcher.notifier.Notifier
import ru.adchampagne.test.global.dispatcher.notifier.SystemMessage
import ru.adchampagne.test.global.utils.asLiveData
import ru.adchampagne.test.global.viewmodel.LoaderViewModel

class AuthViewModel(
    private val loginUseCase: LoginUseCase,
    private val recoverUseCase: RecoverUseCase,
    private val registerUseCase: RegisterUseCase,
    private val errorHandler: ErrorHandler,
    private val notifier: Notifier
) : LoaderViewModel() {

    private val _loginEvent: LiveEvent<Boolean> = LiveEvent()
    val loginLiveData = _loginEvent.asLiveData()

    fun login(login: String, password: String) {
        showProgress()
        viewModelScope.launch() {
            //simulating delay
            delay(2000)
            val params = AuthParams(username = login, password = password)
            loginUseCase(params)
                .mapLeft { R.string.message_auth_invalid }
                .fold(
                    {
                        if(it != null){
                            hideProgress()
                            _loginEvent.value = true
                        }
                        else {
                            notifier.sendMessage(R.string.message_auth_invalid, level = SystemMessage.Level.ERROR)
                            onError()
                        }

                    },
                    {
                        notifier.sendMessage(it, level = SystemMessage.Level.ERROR)
                        onError()
                    }
                )
        }
    }

    fun register(login: String, password: String) {
        showProgress()
        viewModelScope.launch() {
            //simulating delay
            delay(2000)
            val params = AuthParams(username = login, password = password)
            registerUseCase(params)
                .mapLeft { R.string.message_register_failed }
                .fold(
                    {
                        if(it != null){
                            hideProgress()
                            _loginEvent.value = true
                        }
                        else{
                            notifier.sendMessage(R.string.message_register_duplicate, level = SystemMessage.Level.ERROR)
                            onError()
                        }

                    },
                    {
                        notifier.sendMessage(it, level = SystemMessage.Level.ERROR)
                        onError()
                    }
                )
        }
    }

    fun recover(login: String) {
        showProgress()
        viewModelScope.launch() {
            //simulating delay
            delay(2000)
            recoverUseCase(login)
                .mapLeft { R.string.message_recover_failed }
                .fold(
                    {
                        if (it) {
                            hideProgress()
                            notifier.sendMessage(
                                R.string.recover_success,
                                level = SystemMessage.Level.NORMAL
                            )
                        } else {
                            notifier.sendMessage(
                                R.string.recover_no_mail,
                                level = SystemMessage.Level.ERROR
                            )
                            onError()
                        }
                    },
                    {
                        notifier.sendMessage(it, level = SystemMessage.Level.ERROR)
                        onError()
                    }
                )
        }
    }

}