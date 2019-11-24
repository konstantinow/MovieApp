package com.test.movieapp.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.test.movieapp.App
import com.test.movieapp.R
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import javax.inject.Provider

class MainActivity : MvpActivity(), IMainView {

    @InjectPresenter
    lateinit var mPresenter: MainPresenter

    @Inject
    lateinit var presenterProvider: Provider<MainPresenter>

    @ProvidePresenter
    fun providePresenter(): MainPresenter = presenterProvider.get()

    private lateinit var mMovieAdapter: MoviesAdapter

    init {
        App.mAppComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mMovieAdapter = MoviesAdapter()
        recyclerView.adapter = mMovieAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        swipeRefresh.setOnRefreshListener {
            mPresenter.onRefreshSwiped()
        }
    }

    override fun showNewItems(data: List<MovieData>) {
        data.forEach { Log.d("LOGI", it.title) }
        mMovieAdapter.setData(data)
    }

    override fun showError(state: IMainView.State) {
        when (state) {
            IMainView.State.ERROR_NETWORK_UNAVAILABLE -> {
                showMessage(getString(R.string.error_network))
            }
            IMainView.State.ERROR_UNDEFINED -> {
                showMessage(getString(R.string.error_unnamed))
            }
            IMainView.State.LIST_IS_EMPTY -> {
                showMessage(getString(R.string.list_is_empty))
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showMainProgress() {
        recyclerView.visibility = View.GONE
        mainProgressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        mainProgressBar.visibility = View.GONE
        swipeRefresh.isRefreshing = false
        recyclerView.visibility = View.VISIBLE
    }
}
