package com.olamachia.pokemonweekseventask.domain.usecases

import com.olamachia.pokemonweekseventask.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonPagedUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository
        ) {
    operator fun invoke() = pokemonRepository.getPokemonPagedResultStream()
}