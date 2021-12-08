package com.olamachia.pokemonweekseventask.data.mappers

import com.olamachia.pokemonweekseventask.data.api.models.HomeApiResponse
import com.olamachia.pokemonweekseventask.domain.entities.Home

class HomeMapper : IMapper<Home, HomeApiResponse> {
    override fun mapToEntity(type: HomeApiResponse): Home {
        return Home(
            type.fontDefault,
            type.frontShiny
        )
    }
}