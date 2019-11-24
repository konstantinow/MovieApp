package com.test.movieapp.main

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.test.movieapp.network.MovieApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(
    private val mMovieApi: MovieApi,
    private val mMainModel: IMainModel
) :
    MvpPresenter<IMainView>() {

    private var mNetworkDisposable: Disposable? = null
    private var mMovieApiDisposable: Disposable? = null
    private var mIsNetworkAvailable = false

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        Log.d("LOGI", "onFirstViewAttach")
        viewState.showMainProgress()

        mNetworkDisposable = ReactiveNetwork
            .observeInternetConnectivity()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mIsNetworkAvailable = it
                if (it) {
                    getData()
                } else {
                    viewState.hideProgress()
                    viewState.showError(IMainView.State.ERROR_NETWORK_UNAVAILABLE)
                }
            }, {
                mIsNetworkAvailable = false
                it.printStackTrace()
                viewState.hideProgress()
                viewState.showError(IMainView.State.ERROR_UNDEFINED)
            })
    }

    private fun getData() {
        if (!mIsNetworkAvailable) {
            viewState.hideProgress()
            viewState.showError(IMainView.State.ERROR_NETWORK_UNAVAILABLE)
            return
        }
        mMovieApiDisposable?.dispose()
        mMovieApiDisposable = mMainModel.getMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.hideProgress()
                viewState.showNewItems(it)
            }, {
                it.printStackTrace()
                viewState.hideProgress()
                viewState.showError(IMainView.State.ERROR_UNDEFINED)
            })
    }

    private fun onPageScrolled() {
        if (!mIsNetworkAvailable) {
            viewState.hideProgress()
            viewState.showError(IMainView.State.ERROR_NETWORK_UNAVAILABLE)
            return
        }
        mMovieApiDisposable?.dispose()
        mMovieApiDisposable = mMainModel.getNextMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.hideProgress()
                viewState.showNewItems(it)
            }, {
                it.printStackTrace()
                viewState.hideProgress()
                viewState.showError(IMainView.State.ERROR_UNDEFINED)
            })
    }

    fun onRefreshSwiped() {
        if (!mIsNetworkAvailable) {
            viewState.hideProgress()
            viewState.showError(IMainView.State.ERROR_NETWORK_UNAVAILABLE)
            return
        }
        mMainModel.resetPageCount()
        getData()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("LOGI", "onDestroy")
        mMovieApiDisposable?.dispose()
        mNetworkDisposable?.dispose()
    }
}