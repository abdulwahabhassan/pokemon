package com.olamachia.pokemonweekseventask.domain.usecases

import com.olamachia.pokemonweekseventask.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonDetailsUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository
        ) {
    suspend operator fun invoke(pokemonId: Int) = pokemonRepository.getPokemonDetails(pokemonId)
}