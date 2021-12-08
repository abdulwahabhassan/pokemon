package com.olamachia.pokemonweekseventask.data.mappers

import com.olamachia.pokemonweekseventask.data.api.models.SpritesApiResponse
import com.olamachia.pokemonweekseventask.domain.entities.Sprites
import javax.inject.Inject

class SpritesMapper @Inject constructor(
    private val otherMapper: OtherMapper
) : IMapper<Sprites, SpritesApiResponse> {

    override fun mapToEntity(type: SpritesApiResponse): Sprites {
        return Sprites(
            type.frontDefault,
            type.frontShiny,
            otherMapper.mapToEntity(type.other)
        )
    }
}