package com.olamachia.pokemonweekseventask.data.datasource

import android.net.Uri
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.olamachia.pokemonweekseventask.data.api.PokemonApi
import com.olamachia.pokemonweekseventask.data.mappers.PokemonMapper
import com.olamachia.pokemonweekseventask.domain.entities.Pokemon
import javax.inject.Inject


class PokemonRemotePagingSource @Inject constructor(
    private val service: PokemonApi,
    private val mapper: PokemonMapper
    ) : PagingSource<Int, Pokemon>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {

        val offset = params.key ?: POKEMON_STARTING_PAGE_OFFSET

        return try {
            val response= service.getPokemon(offset, POKEMON_LIMIT)
            val pagedResponse = response.body()
            val data = mapper.mapToEntityList(pagedResponse?.results)

            var nextPageOffset: Int? = null
            if (pagedResponse?.next != null) {
                val uri = Uri.parse(pagedResponse.next)
                val nextPageOffsetQuery = uri.getQueryParameter(QUERY_PARAMETER)
                nextPageOffset = nextPageOffsetQuery?.toInt()
            }

            LoadResult.Page(
                data = data,
                prevKey = null,
                nextKey = nextPageOffset
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
        return null
    }

    companion object {
        const val POKEMON_STARTING_PAGE_OFFSET = 0
        const val POKEMON_LIMIT = 20
        const val QUERY_PARAMETER = "offset"
    }
}