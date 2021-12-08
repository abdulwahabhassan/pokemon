package com.olamachia.pokemonweekseventask.domain.repository

import androidx.paging.PagingData
import com.olamachia.pokemonweekseventask.data.api.models.PokemonApiResponse
import com.olamachia.pokemonweekseventask.domain.entities.PokemonPaged
import com.olamachia.pokemonweekseventask.domain.entities.PokemonDetails
import com.olamachia.pokemonweekseventask.domain.Result
import com.olamachia.pokemonweekseventask.domain.entities.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    fun getPokemonPagedResultStream(): Flow<PagingData<Pokemon>>
    suspend fun getPokemonDetails(pokemonId: Int) : Result<PokemonDetails>
    suspend fun getPokemonByLimit(offset: Int, limit: Int) : Result<PokemonPaged>
}