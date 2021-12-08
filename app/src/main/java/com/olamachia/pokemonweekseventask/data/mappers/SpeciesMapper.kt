package com.olamachia.pokemonweekseventask.data.mappers

import com.olamachia.pokemonweekseventask.data.api.models.SpeciesApiResponse
import com.olamachia.pokemonweekseventask.domain.entities.Species
import javax.inject.Inject

class SpeciesMapper : IMapper<Species, SpeciesApiResponse> {

    override fun mapToEntity(type: SpeciesApiResponse): Species {
        return Species(
            type.name,
            type.url
        )
    }
}