package com.olamachia.pokemonweekseventask.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder

import com.google.gson.Gson
import com.olamachia.pokemonweekseventask.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit


class ServiceModule {

    private val httpClient by lazy {
        val httpClientBuilder = OkHttpClient.Builder()
        val httpLoggingInterceptor = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClientBuilder.addInterceptor(httpLoggingInterceptor)
        }

        httpClientBuilder.interceptors().add(httpLoggingInterceptor)
        httpClientBuilder.build()
    }


    private fun getRetrofit(baseUrl: String): Retrofit {

        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    }

    fun createPokemonApi(baseUrl: String): PokemonApi {
        return getRetrofit(baseUrl).create(PokemonApi::class.java)
    }

}