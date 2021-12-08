package com.olamachia.pokemonweekseventask.data.api

import com.olamachia.pokemonweekseventask.data.api.models.PokemonDetailsApiResponse
import com.olamachia.pokemonweekseventask.data.api.models.PokemonPagedApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET("pokemon")
    suspend fun getPokemon(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<PokemonPagedApiResponse>

    @GET("pokemon/{id}")
    suspend fun getPokemonDetails(
        @Path("id") pokemonId: Int,
    ) : Response<PokemonDetailsApiResponse>

}