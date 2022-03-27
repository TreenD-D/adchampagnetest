package ru.adchampagne.data.preference

import ru.adchampagne.domain.model.enums.Language
import com.tfcporciuncula.flow.FlowSharedPreferences
import com.tfcporciuncula.flow.Preference
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map
import ru.adchampagne.domain.model.auth.AuthState

private const val FIREBASE_TOKEN = "FIREBASE_TOKEN"
private const val AUTH_TOKEN = "AUTH_TOKEN"
private const val WELCOME_SHOWN = "WELCOME_SHOWN"
private const val LANGUAGE = "LANGUAGE"

@OptIn(ExperimentalCoroutinesApi::class)
class PreferencesWrapper(private val preferences: FlowSharedPreferences) {
    val firebaseToken: Preference<String>
        get() = preferences.getString(FIREBASE_TOKEN)
    val authToken: Preference<String?>
        get() = preferences.getNullableString(AUTH_TOKEN)
    val welcomeShown: Preference<Boolean>
        get() = preferences.getBoolean(WELCOME_SHOWN, false)
    val language: Preference<String>
        get() = preferences.getString(LANGUAGE, Language.EN.tag)

    val authStateFlow
        get() = authToken.asFlow().map {
            AuthState(
                !it.isNullOrBlank()
            )
        }

    fun drop() = preferences.clear()
}
