package com.test.movieapp.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.MvpActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.test.movieapp.App
import com.test.movieapp.R
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import javax.inject.Provider

class MainActivity : MvpActivity(), IMainView {
    companion object {
        const val TAG = "MainActivity"
    }

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
        Log.d(TAG, "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mMovieAdapter = MoviesAdapter()
        recyclerView.adapter = mMovieAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = (recyclerView.layoutManager ?: return) as LinearLayoutManager
                with(layoutManager) {
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                    if (firstVisibleItemPosition >= 0 &&
                        (childCount + firstVisibleItemPosition) >= itemCount
                    ) {
                        Log.d(TAG, "onPageScrolled")
                        mPresenter.onPageScrolled()
                    }
                }
            }
        })
        recyclerView.setHasFixedSize(true)

        swipeRefresh.setOnRefreshListener {
            mPresenter.onRefreshSwiped()
        }
    }

    override fun showNewItems(data: List<MovieData>) {
        Log.d(TAG, "showNewItems")
        mMovieAdapter.addData(data)
    }

    override fun setItems(data: List<MovieData>) {
        Log.d(TAG, "setItems")
        hideProgress()
        mMovieAdapter.setData(data)
    }

    override fun showError(state: IMainView.State) {
        Log.d(TAG, "showError $state")
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
        Log.d(TAG, "showMessage: $message")
        recyclerView.visibility = View.GONE
        mainProgressBar.visibility = View.GONE
        tvMessage.text = message
        tvMessage.visibility = View.VISIBLE
    }

    override fun showMainProgress() {
        Log.d(TAG, "showMainProgress")
        recyclerView.visibility = View.GONE
        tvMessage.visibility = View.GONE
        mainProgressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        Log.d(TAG, "hideProgress")
        tvMessage.visibility = View.GONE
        mainProgressBar.visibility = View.GONE
        swipeRefresh.isRefreshing = false
        recyclerView.visibility = View.VISIBLE
    }
}
