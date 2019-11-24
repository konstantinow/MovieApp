package com.test.movieapp.main

import com.test.movieapp.network.MovieApi
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainModel @Inject constructor(private val mMovieApi: MovieApi) : IMainModel {
    private var pageIndex = 0

    override fun getMovies(): Single<List<MovieData>> {
        return mMovieApi.getPopularMovies()
            .map { response ->
                val list = arrayListOf<MovieData>()
                response.results.forEach {
                    list.add(
                        MovieData(
                            it.title,
                            it.overview,
                            MovieApi.BASE_IMAGE_URL + it.poster_path
                        )
                    )
                }
                return@map list
            }
    }

    override fun getNextMovies(): Single<List<MovieData>> {
        pageIndex++
        return mMovieApi.getPopularMovies(pageIndex)
            .map { response ->
                val list = arrayListOf<MovieData>()
                response.results.forEach {
                    list.add(
                        MovieData(
                            it.title,
                            it.overview,
                            MovieApi.BASE_IMAGE_URL + it.poster_path
                        )
                    )
                }
                return@map list
            }
    }

    override fun resetPageCount() {
        pageIndex = 0
    }
}