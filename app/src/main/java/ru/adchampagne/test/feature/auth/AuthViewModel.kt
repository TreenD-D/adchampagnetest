package ru.adchampagne.test.feature.auth

import ru.adchampagne.test.global.dispatcher.error.ErrorHandler
import ru.adchampagne.test.global.dispatcher.notifier.Notifier
import ru.adchampagne.test.global.viewmodel.LoaderViewModel

class AuthViewModel(
    private val errorHandler: ErrorHandler,
    private val notifier: Notifier
) : LoaderViewModel() {

}