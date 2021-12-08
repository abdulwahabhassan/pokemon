package com.olamachia.pokemonweekseventask.data.mappers

import com.olamachia.pokemonweekseventask.data.api.models.MovesApiResponse
import com.olamachia.pokemonweekseventask.domain.entities.Moves
import javax.inject.Inject

class MovesMapper @Inject constructor(
    private val moveMapper: MoveMapper
) : IMapper<Moves, MovesApiResponse> {
    override fun mapToEntity(type: MovesApiResponse): Moves {
        return Moves(
            moveMapper.mapToEntity(type.move)
        )
    }
}