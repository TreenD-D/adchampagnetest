package ru.adchampagne.test.global.dispatcher.error

import android.content.res.Resources
import com.google.firebase.crashlytics.FirebaseCrashlytics
import ru.adchampagne.test.R
import ru.adchampagne.data.global.NetErrorCode.BAD_REQUEST
import ru.adchampagne.data.global.NetErrorCode.FORBIDDEN
import ru.adchampagne.data.global.NetErrorCode.INTERNAL_SERVER_ERROR
import ru.adchampagne.data.global.NetErrorCode.NOT_FOUND
import ru.adchampagne.data.global.NetErrorCode.UNAUTHORIZED
import kotlinx.coroutines.suspendCancellableCoroutine
import mu.KotlinLogging
import pro.appcraft.lib.navigation.AppRouter
import retrofit2.HttpException
import java.io.IOException
import java.net.NoRouteToHostException
import java.net.SocketTimeoutException
import kotlin.coroutines.resume

private val logger = KotlinLogging.logger {}

class ErrorHandler(var resources: Resources, private val router: AppRouter) {
    fun proceed(
        error: Throwable,
        allowFullscreenStubs: Boolean = true,
        callback: (String) -> Unit
    ) {
        logger.error(error.message, error)
        when {
            // allowFullscreenStubs && ((error is UnknownHostException) || (error is SocketTimeoutException)) -> {
            //     router.startFlow(Screens.NoNetworkStubFlow())
            // }
            // (error is HttpException) && (error.code() == UNAUTHORIZED) -> {
            //     router.newRootFlow(Screens.LoginFlow())
            // }
            else -> {
                callback(getUserMessage(error))
            }
        }
    }

    suspend fun proceedSuspend(error: Throwable): String = suspendCancellableCoroutine {
        logger.error(error.message, error)
        when {
            // error is HttpException && error.code() == UNAUTHORIZED -> {
            //     // Do some actions and interrupt execution
            //     router.newRootScreen(Screens.Flow.auth())
            //     it.cancel()
            // }
            else -> it.resume(getUserMessage(error))
        }
    }

    private fun getUserMessage(error: Throwable): String {
        return when (error) {
            is IOException -> resources.getString(getIOErrorMessage(error))
            // is ServerException -> {
            //     sendCrashlyticsReport(error)
            //     error.message ?: resources.getString(R.string.unknown_error)
            // }
            is HttpException -> resources.getString(getServerErrorMessage(error))
            is SecurityException -> resources.getString(R.string.security_error)
            // is ValidatorException -> resources.getString(error.validatorMessage)
            else -> {
                sendCrashlyticsReport(error)
                resources.getString(R.string.unknown_error)
            }
        }
    }

    private fun getIOErrorMessage(error: IOException): Int {
        return if (error is NoRouteToHostException || error is SocketTimeoutException) {
            R.string.server_not_available_error
        } else {
            R.string.network_error
        }
    }

    private fun getServerErrorMessage(exception: HttpException): Int {
        sendCrashlyticsReport(exception)
        return when (exception.code()) {
            BAD_REQUEST -> R.string.bad_request_error
            UNAUTHORIZED -> R.string.unauthorized_error
            FORBIDDEN -> R.string.forbidden_error
            NOT_FOUND -> R.string.not_found_error
            INTERNAL_SERVER_ERROR -> R.string.internal_server_error
            else -> R.string.unknown_error
        }
    }

    private fun sendCrashlyticsReport(error: Throwable) {
        FirebaseCrashlytics.getInstance().recordException(error)
    }
}
