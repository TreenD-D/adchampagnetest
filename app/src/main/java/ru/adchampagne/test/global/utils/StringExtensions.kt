package ru.adchampagne.test.global.utils

import android.util.Patterns

fun String?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()