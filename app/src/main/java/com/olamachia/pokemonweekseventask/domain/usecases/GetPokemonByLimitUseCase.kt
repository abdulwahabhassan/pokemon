package com.olamachia.pokemonweekseventask.domain.usecases

import com.olamachia.pokemonweekseventask.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonByLimitUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {
    suspend operator fun invoke(offset: Int, limit: Int) =
        pokemonRepository.getPokemonByLimit(offset, limit)
}