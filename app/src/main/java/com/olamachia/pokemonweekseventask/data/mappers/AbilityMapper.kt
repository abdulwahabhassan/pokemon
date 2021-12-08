package com.olamachia.pokemonweekseventask.data.mappers

import com.olamachia.pokemonweekseventask.data.api.models.AbilityApiResponse
import com.olamachia.pokemonweekseventask.domain.entities.Ability
import javax.inject.Inject

class AbilityMapper @Inject constructor(
    private val speciesMapper: SpeciesMapper
) : IMapper<Ability, AbilityApiResponse> {

    override fun mapToEntity(type: AbilityApiResponse): Ability {
        return Ability(
            speciesMapper.mapToEntity(type.ability),
            type.slot
        )
    }
}