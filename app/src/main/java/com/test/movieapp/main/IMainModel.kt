package com.test.movieapp.main

import io.reactivex.Single

interface IMainModel {
    fun getMovies(): Single<List<MovieData>>
    fun getNextMovies(): Single<List<MovieData>>
    fun resetPageCount()
}