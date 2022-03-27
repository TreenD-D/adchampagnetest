package ru.adchampagne.domain.global.extensions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import pro.appcraft.either.AsyncEither
import pro.appcraft.either.UnsafeEffect
import pro.appcraft.either.getOrHandle

/**
 * Flattens AsyncEither<L, R> flow into values flow, supplying default value for any Left elements
 *
 * @param callback callback for transforming Left to value of type R
 * @return Flattened flow of type R
 */
fun <L, R> Flow<AsyncEither<L, R>>.flatten(callback: suspend (L) -> R): Flow<R> =
    map { it.getOrHandle(callback) }

/**
 * Flattens AsyncEither<L, R> flow into values flow, running error catching callback.
 *
 * Note that this method suppresses any Left elements instead of transforming them,
 * thus producing side effects
 * @param callback callback for catching a Left value, empty by default
 * @return Flattened flow of type R
 */
@UnsafeEffect
fun <L, R> Flow<AsyncEither<L, R>>.flattenCatching(callback: suspend (Throwable) -> Unit = {}): Flow<R> =
    map { it.unsafeGet() }.catch { callback(it) }

/**
 * Folds each AsyncEither<L,R> value with success and error callbacks.
 * Unlike [flattenCatching], doesn't produce side effects
 *
 * See [AsyncEither.fold] for more info
 */
suspend inline fun <L, K, R, O> Flow<AsyncEither<L, R>>.collectFolding(
    crossinline ifRight: suspend (R) -> O,
    crossinline ifLeft: suspend (L) -> K
) = collect { it.fold(ifRight, ifLeft) }
