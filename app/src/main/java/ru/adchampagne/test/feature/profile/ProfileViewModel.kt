package ru.adchampagne.test.feature.profile

import androidx.lifecycle.viewModelScope
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.launch
import ru.adchampagne.domain.interactor.auth.LogoutUseCase
import ru.adchampagne.test.global.dispatcher.error.ErrorHandler
import ru.adchampagne.test.global.dispatcher.notifier.Notifier
import ru.adchampagne.test.global.utils.asLiveData
import ru.adchampagne.test.global.viewmodel.LoaderViewModel

class ProfileViewModel(
    private val logoutUseCase: LogoutUseCase,
    private val errorHandler: ErrorHandler,
    private val notifier: Notifier
) : LoaderViewModel() {

    private val logoutEvent = LiveEvent<Boolean>()
    val logoutLiveData = logoutEvent.asLiveData()

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
                .fold({ logoutEvent.value = true }, ::onError)
        }
    }
}