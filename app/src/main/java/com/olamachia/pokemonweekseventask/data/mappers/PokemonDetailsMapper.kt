package com.olamachia.pokemonweekseventask.data.mappers

import com.olamachia.pokemonweekseventask.data.api.models.PokemonDetailsApiResponse
import com.olamachia.pokemonweekseventask.domain.entities.PokemonDetails
import javax.inject.Inject

class PokemonDetailsMapper @Inject constructor(
    private val abilityMapper: AbilityMapper,
    private val speciesMapper: SpeciesMapper,
    private val typeMapper: TypeMapper,
    private val spritesMapper: SpritesMapper,
    private val statsMapper: StatsMapper,
    private val movesMapper: MovesMapper

) : IMapper<PokemonDetails, PokemonDetailsApiResponse> {

    override fun mapToEntity(type: PokemonDetailsApiResponse): PokemonDetails {
        return PokemonDetails (
            type.id,
            type.name,
            abilityMapper.mapToEntityList(type.abilities),
            speciesMapper.mapToEntityList(type.forms),
            type.height,
            typeMapper.mapToEntityList(type.types),
            type.weight,
            spritesMapper.mapToEntity(type.sprites),
            statsMapper.mapToEntityList(type.stats),
            movesMapper.mapToEntityList(type.moves)
        )
    }

}