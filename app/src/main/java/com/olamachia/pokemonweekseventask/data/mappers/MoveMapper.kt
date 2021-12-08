package com.olamachia.pokemonweekseventask.data.mappers

import com.olamachia.pokemonweekseventask.data.api.models.MoveApiResponse
import com.olamachia.pokemonweekseventask.domain.entities.Move
import javax.inject.Inject

class MoveMapper : IMapper<Move, MoveApiResponse> {
    override fun mapToEntity(type: MoveApiResponse): Move {
        return Move(
            type.name
        )
    }
}