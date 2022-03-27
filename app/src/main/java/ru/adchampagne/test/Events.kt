package ru.adchampagne.test

sealed interface Event {
    class SampleEvent(val stringData: String, val longData: Long) : Event
}