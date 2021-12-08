package com.olamachia.pokemonweekseventask.data.datasource

import android.util.Log
import com.olamachia.pokemonweekseventask.data.api.PokemonApi
import com.olamachia.pokemonweekseventask.data.mappers.PokemonDetailsMapper
import com.olamachia.pokemonweekseventask.data.mappers.PokemonPagedMapper
import com.olamachia.pokemonweekseventask.domain.Result
import com.olamachia.pokemonweekseventask.domain.entities.PokemonDetails
import com.olamachia.pokemonweekseventask.domain.entities.PokemonPaged
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonRemoteDatasourceImpl @Inject constructor(
    private val service: PokemonApi,
    private val pokemonPagedMapper: PokemonPagedMapper,
    private val pokemonDetailsMapper: PokemonDetailsMapper,
    private val dispatcherIO: CoroutineDispatcher
        ) : PokemonRemoteDatasource {

    override suspend fun getRemotePokemonDetails(pokemonId: Int): Result<PokemonDetails> =
        withContext(dispatcherIO) {
            try {
                val response = service.getPokemonDetails(pokemonId)
                if (response.isSuccessful) {
                    return@withContext Result.Success(
                        pokemonDetailsMapper.mapToEntity(response.body()!!)
                    )
                } else {
                    return@withContext Result.Error(
                        Exception(response.message())
                    )
                }
            }catch (e: Exception) {
                return@withContext Result.Error(e)
            }
        }

    override suspend fun getRemotePokemonByLimit(offset: Int, limit: Int): Result<PokemonPaged> =
        withContext(dispatcherIO) {
            try {
                val response = service.getPokemon(offset, limit)
                if (response.isSuccessful) {
                    return@withContext Result.Success(
                        pokemonPagedMapper.mapToEntity(response.body()!!)
                    )
                } else {
                    return@withContext Result.Error(
                        Exception(response.message())
                    )
                }
            }catch (e: Exception) {
                return@withContext Result.Error(e)
            }
        }
}