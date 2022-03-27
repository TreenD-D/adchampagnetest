package ru.adchampagne.test.di

import android.content.Context
import ru.adchampagne.test.BuildConfig
import ru.adchampagne.data.network.interceptor.ErrorResponseInterceptor
import ru.adchampagne.data.network.interceptor.HeaderInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import java.io.File
import java.util.concurrent.TimeUnit

private const val RESPONSES_PATH = "responses"
private const val CACHE_MAX_SIZE = 100 * 1024 * 1024 // 100 MB
private const val TIMEOUT_MS = 60000L // 60 sec

@OptIn(ExperimentalSerializationApi::class)
internal val networkModule = module {
    fun getCache(context: Context): Cache? {
        var cache: Cache? = null
        try {
            val httpCacheDirectory = File(context.cacheDir, RESPONSES_PATH)
            cache = Cache(httpCacheDirectory, CACHE_MAX_SIZE.toLong())
        } catch (_: Exception) {
        }

        return cache
    }

    fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else HttpLoggingInterceptor.Level.NONE

        return httpLoggingInterceptor
    }

    factory(named("AuthClient")) {
        OkHttpClient.Builder()
            .addInterceptor(getHttpLoggingInterceptor())
            .addInterceptor(HeaderInterceptor(get()))
            .addInterceptor(ErrorResponseInterceptor(get()))
            .cache(getCache(androidContext()))
            .callTimeout(TIMEOUT_MS, TimeUnit.MILLISECONDS)
            .connectTimeout(TIMEOUT_MS, TimeUnit.MILLISECONDS)
            .readTimeout(TIMEOUT_MS, TimeUnit.MILLISECONDS)
            .writeTimeout(TIMEOUT_MS, TimeUnit.MILLISECONDS)
            .build()
    }

    factory(named("CommonClient")) {
        OkHttpClient.Builder()
            .addInterceptor(getHttpLoggingInterceptor())
            .addInterceptor(ErrorResponseInterceptor(get()))
            .cache(getCache(androidContext()))
            .callTimeout(TIMEOUT_MS, TimeUnit.MILLISECONDS)
            .connectTimeout(TIMEOUT_MS, TimeUnit.MILLISECONDS)
            .readTimeout(TIMEOUT_MS, TimeUnit.MILLISECONDS)
            .writeTimeout(TIMEOUT_MS, TimeUnit.MILLISECONDS)
            .build()
    }

    val contentType = "application/json".toMediaType()

    fun getBuilder(
        okHttpClient: OkHttpClient
    ): Retrofit.Builder {
        val json = Json {
            encodeDefaults = true
            ignoreUnknownKeys = true
        }
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
    }

    factory(named("CommonBuilder")) {
        getBuilder(okHttpClient = get(named("CommonClient")))
    }

    factory(named("AuthBuilder")) {
        getBuilder(okHttpClient = get(named("AuthClient")))
    }

    single(named("AuthRetrofit")) {
        get<Retrofit.Builder>(named("AuthBuilder"))
            .baseUrl(ru.adchampagne.data.BuildConfig.API_ENDPOINT).build()
    }

    single(named("CommonRetrofit")) {
        get<Retrofit.Builder>(named("CommonBuilder"))
            .baseUrl(ru.adchampagne.data.BuildConfig.API_ENDPOINT).build()
    }
}