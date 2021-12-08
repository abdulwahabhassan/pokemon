package com.olamachia.pokemonweekseventask.data.mappers

import com.olamachia.pokemonweekseventask.data.api.models.PokemonApiResponse
import com.olamachia.pokemonweekseventask.domain.entities.Pokemon
import javax.inject.Inject

class PokemonMapper : IMapper<Pokemon, PokemonApiResponse> {

    override fun mapToEntity(type: PokemonApiResponse): Pokemon {
        return Pokemon(
            type.name,
            type.url
        )
    }

}