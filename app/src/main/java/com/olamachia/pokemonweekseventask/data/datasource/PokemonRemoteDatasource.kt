package com.olamachia.pokemonweekseventask.data.datasource

import com.olamachia.pokemonweekseventask.domain.Result
import com.olamachia.pokemonweekseventask.domain.entities.PokemonPaged
import com.olamachia.pokemonweekseventask.domain.entities.PokemonDetails

interface PokemonRemoteDatasource {
    suspend fun getRemotePokemonDetails(pokemonId: Int) : Result<PokemonDetails>
    suspend fun getRemotePokemonByLimit(offset: Int, limit: Int) : Result<PokemonPaged>
}