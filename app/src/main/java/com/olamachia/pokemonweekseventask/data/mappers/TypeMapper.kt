package com.olamachia.pokemonweekseventask.data.mappers

import com.olamachia.pokemonweekseventask.data.api.models.TypeApiResponse
import com.olamachia.pokemonweekseventask.domain.entities.Type
import javax.inject.Inject

class TypeMapper @Inject constructor(
    private val speciesMapper: SpeciesMapper
) : IMapper<Type, TypeApiResponse> {

    override fun mapToEntity(type: TypeApiResponse): Type {
        return Type(
            type.slot,
            speciesMapper.mapToEntity(type.type)
        )
    }
}