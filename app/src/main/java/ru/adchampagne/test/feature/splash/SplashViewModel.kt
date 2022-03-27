package ru.adchampagne.test.feature.splash

import androidx.lifecycle.ViewModel
import ru.adchampagne.test.Event
import ru.adchampagne.test.global.dispatcher.error.ErrorHandler
import ru.adchampagne.test.global.dispatcher.event.EventDispatcher
import ru.adchampagne.test.global.dispatcher.notifier.Notifier

class SplashViewModel(
    private val errorHandler: ErrorHandler,
    private val notifier: Notifier,
    private val eventDispatcher: EventDispatcher
) : ViewModel(), EventDispatcher.EventListener {
    init {
        eventDispatcher.addEventListener(Event.SampleEvent::class, this)
    }

    override fun onCleared() {
        eventDispatcher.removeEventListener(this)
        super.onCleared()
    }

    override fun onEvent(event: Event) {
        when (event) {
            is Event.SampleEvent -> {
                // Can use info from event.field directly without type cast:
                val x = event.longData
                val y = event.stringData
            }
        }
    }

    fun onAnimationEnd() {
        // TODO
    }
}