package com.olamachia.pokemonweekseventask.data.mappers

import com.olamachia.pokemonweekseventask.data.api.models.StatApiResponse
import com.olamachia.pokemonweekseventask.domain.entities.Stat

class StatMapper : IMapper<Stat, StatApiResponse> {
    override fun mapToEntity(type: StatApiResponse): Stat {
        return Stat(
            type.name
        )
    }

}
