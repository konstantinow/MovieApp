package com.test.movieapp.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("page") page: Int? = null,
        @Query("api_key") apiKey: String = API_KEY
    ): Single<PopularMovieResponse>

    companion object {
        val API_KEY: String
            get() = "bed8d240223b5a7278f0550bdcbea600"
        val BASE_IMAGE_URL: String
            get() = "https://image.tmdb.org/t/p/w500"
    }
}