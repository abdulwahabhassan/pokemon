package com.olamachia.pokemonweekseventask.data.mappers

import com.olamachia.pokemonweekseventask.data.api.models.OtherApiResponse
import com.olamachia.pokemonweekseventask.domain.entities.Home
import com.olamachia.pokemonweekseventask.domain.entities.Other
import javax.inject.Inject

class OtherMapper @Inject constructor(
    private val homeMapper: HomeMapper
) : IMapper<Other, OtherApiResponse> {
    override fun mapToEntity(type: OtherApiResponse): Other {
        return Other(
            homeMapper.mapToEntity(type.home)
        )
    }
}