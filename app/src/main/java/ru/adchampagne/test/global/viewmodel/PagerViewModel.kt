package ru.adchampagne.test.global.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import pro.appcraft.lib.pagination.BasePaginator
import pro.appcraft.lib.pagination.OffsetPaginator

sealed interface PagingResult {
    class Data<T>(val data: List<T>, val show: Boolean) : PagingResult

    class EmptyError(val show: Boolean, val error: Throwable?) : PagingResult

    class EmptyProgress(val show: Boolean) : PagingResult

    class EmptyView(val show: Boolean) : PagingResult

    class ErrorMessage(val error: Throwable) : PagingResult

    class PageProgress(val show: Boolean) : PagingResult

    class RefreshProgress(val show: Boolean) : PagingResult
}

class LiveDataPagerController<T> : BasePaginator.ViewController<T> {
    @Suppress("MemberVisibilityCanBePrivate")
    val pagingResultLiveData: MutableLiveData<PagingResult> = MutableLiveData()

    override fun showData(show: Boolean, data: List<T>) {
        pagingResultLiveData.value = PagingResult.Data(data, show)
    }

    override fun showEmptyError(show: Boolean, error: Throwable?) {
        pagingResultLiveData.value = PagingResult.EmptyError(show, error)
    }

    override fun showEmptyProgress(show: Boolean) {
        pagingResultLiveData.value = PagingResult.EmptyProgress(show)
    }

    override fun showEmptyView(show: Boolean) {
        pagingResultLiveData.value = PagingResult.EmptyView(show)
    }

    override fun showErrorMessage(error: Throwable) {
        pagingResultLiveData.value = PagingResult.ErrorMessage(error)
    }

    override fun showPageProgress(show: Boolean) {
        pagingResultLiveData.value = PagingResult.PageProgress(show)
    }

    override fun showRefreshProgress(show: Boolean) {
        pagingResultLiveData.value = PagingResult.RefreshProgress(show)
    }
}

@Suppress("MemberVisibilityCanBePrivate")
abstract class PagerViewModel<T : Any>(pageLimit: Int = 10) : ViewModel() {
    protected val controller = LiveDataPagerController<T>()
    protected val pager: OffsetPaginator<T> =
        OffsetPaginator(viewModelScope, ::pageLoader, controller, pageLimit)

    protected abstract suspend fun pageLoader(offset: Int, limit: Int): List<T>

    override fun onCleared() {
        pager.release()
        super.onCleared()
    }

    protected fun paginate() {
        pager.restart()
    }

    fun reload() {
        pager.refresh()
    }

    fun loadNext() {
        pager.loadNext()
    }
}
