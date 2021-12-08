package com.olamachia.pokemonweekseventask.data.repository

import android.util.Log
import androidx.paging.InvalidatingPagingSourceFactory
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.olamachia.pokemonweekseventask.data.datasource.PokemonRemoteDatasource
import com.olamachia.pokemonweekseventask.domain.Result
import com.olamachia.pokemonweekseventask.domain.entities.Pokemon
import com.olamachia.pokemonweekseventask.domain.entities.PokemonDetails
import com.olamachia.pokemonweekseventask.domain.entities.PokemonPaged
import com.olamachia.pokemonweekseventask.domain.repository.PokemonRepository
import com.olamachia.pokemonweekseventask.data.datasource.PokemonRemotePagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonRemotePagingSource: PokemonRemotePagingSource,
    private val pokemonRemoteDatasource: PokemonRemoteDatasource
        ): PokemonRepository {

    override fun getPokemonPagedResultStream(): Flow<PagingData<Pokemon>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                prefetchDistance = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = InvalidatingPagingSourceFactory{ pokemonRemotePagingSource }
        ).flow
    }

    override suspend fun getPokemonByLimit(offset: Int, limit: Int): Result<PokemonPaged> {
        return pokemonRemoteDatasource.getRemotePokemonByLimit(offset, limit)
    }

    override suspend fun getPokemonDetails(pokemonId: Int): Result<PokemonDetails> {
        return pokemonRemoteDatasource.getRemotePokemonDetails(pokemonId)
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }

}