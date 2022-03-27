package ru.adchampagne.test.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.map
import ru.adchampagne.domain.global.extensions.flatten
import ru.adchampagne.domain.interactor.auth.ObserveAuthStateUseCase
import ru.adchampagne.test.global.utils.context

class AppViewModel(observeAuthStateUseCase: ObserveAuthStateUseCase) : ViewModel() {
    val loggedInLivedata = observeAuthStateUseCase()
        .drop(1) // Drop initial value; only listen for changes
        .flatten { null }
        .map { it?.isLoggedIn ?: false }
        .distinctUntilChanged()
        .asLiveData(context)
}