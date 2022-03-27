package ru.adchampagne.domain.global

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import pro.appcraft.either.AsyncCatching
import pro.appcraft.either.AsyncEither
import pro.appcraft.either.eitherCatching

abstract class FlowUseCase<R>(private val coroutineDispatcher: CoroutineDispatcher) {
    operator fun invoke(): Flow<AsyncCatching<R>> {
        return execute()
            .map { AsyncEither.Right(it) }
            .catch { AsyncEither.Left(it) }
            .flowOn(coroutineDispatcher)
    }

    @Throws(RuntimeException::class)
    protected abstract fun execute(): Flow<R>
}

abstract class FlowUseCaseWithParams<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {
    operator fun invoke(parameters: P): Flow<AsyncCatching<R>> {
        return execute(parameters)
            .map { AsyncEither.Right(it) }
            .catch { AsyncEither.Left(it) }
            .flowOn(coroutineDispatcher)
    }

    @Throws(RuntimeException::class)
    protected abstract fun execute(parameters: P): Flow<R>
}

abstract class UseCase<R>(private val coroutineDispatcher: CoroutineDispatcher) {
    suspend operator fun invoke(): AsyncCatching<R> = withContext(coroutineDispatcher) {
        ::execute.eitherCatching()
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(): R
}

abstract class UseCaseWithParams<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {
    suspend operator fun invoke(parameters: P): AsyncCatching<R> =
        withContext(coroutineDispatcher) {
            AsyncEither.catch { execute(parameters) }
        }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameters: P): R
}
