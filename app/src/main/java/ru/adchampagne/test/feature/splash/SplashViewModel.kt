package ru.adchampagne.test.feature.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import ru.adchampagne.domain.interactor.auth.ObserveAuthStateUseCase
import ru.adchampagne.test.global.utils.context

class SplashViewModel(observeAuthStateUseCase: ObserveAuthStateUseCase) : ViewModel() {
    val authStateLiveData: LiveData<Boolean> = observeAuthStateUseCase()
        .take(1)
        .map { it.orNull() }
        .map { it?.isLoggedIn == true }
        .asLiveData(context)

}