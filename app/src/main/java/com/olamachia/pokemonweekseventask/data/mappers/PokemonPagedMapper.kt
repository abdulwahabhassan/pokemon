package com.olamachia.pokemonweekseventask.data.mappers

import com.olamachia.pokemonweekseventask.data.api.models.PokemonPagedApiResponse
import com.olamachia.pokemonweekseventask.domain.entities.PokemonPaged
import javax.inject.Inject

class PokemonPagedMapper @Inject constructor(
    private val pokemonMapper: PokemonMapper
        ) : IMapper<PokemonPaged, PokemonPagedApiResponse> {
    override fun mapToEntity(type: PokemonPagedApiResponse): PokemonPaged {
        return PokemonPaged(
            type.count,
            type.next,
            type.previous,
            pokemonMapper.mapToEntityList(type.results)
        )
    }
}