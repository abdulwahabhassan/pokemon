package com.olamachia.pokemonweekseventask.data.api.models

data class PokemonDetailsApiResponse (
    val id: Long? = null,
    val name: String? = null,
    val abilities: ArrayList<AbilityApiResponse>? = arrayListOf(),
    val forms: ArrayList<SpeciesApiResponse>? = arrayListOf(),
    val height: Long? = null,
    val types: ArrayList<TypeApiResponse>? = arrayListOf(),
    val weight: Long? = null,
    val stats: ArrayList<StatsApiResponse>? = arrayListOf(),
    val sprites: SpritesApiResponse,
    val moves: ArrayList<MovesApiResponse>? = arrayListOf()
        )