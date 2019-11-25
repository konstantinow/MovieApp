package com.test.movieapp.main

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.*


interface IMainView : MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showMainProgress()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideProgress()

    @StateStrategyType(SingleStateStrategy::class)
    fun setItems(data: List<MovieData>)

    @StateStrategyType(AddToEndStrategy::class)
    fun showNewItems(data: List<MovieData>)

    @StateStrategyType(SkipStrategy::class)
    fun showError(state: State)

    enum class State {
        ERROR_NETWORK_UNAVAILABLE,
        ERROR_UNDEFINED,
        LIST_IS_EMPTY
    }
}