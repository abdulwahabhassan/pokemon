package com.olamachia.pokemonweekseventask.data.mappers

import com.olamachia.pokemonweekseventask.data.api.models.StatsApiResponse
import com.olamachia.pokemonweekseventask.domain.entities.Stats
import javax.inject.Inject

class StatsMapper@Inject constructor(
    private val statMapper: StatMapper
) : IMapper<Stats, StatsApiResponse> {
    override fun mapToEntity(type: StatsApiResponse): Stats {
        return Stats(
            type.baseStat,
            statMapper.mapToEntity(type.stat)
        )
    }
}