package com.test.movieapp.main

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType


interface IMainView : MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showMainProgress()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideProgress()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showNewItems(data: List<MovieData>)

    @StateStrategyType(SkipStrategy::class)
    fun showError(state: State)

    enum class State {
        ERROR_NETWORK_UNAVAILABLE,
        ERROR_UNDEFINED,
        LIST_IS_EMPTY
    }
}