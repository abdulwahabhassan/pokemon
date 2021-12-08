package com.olamachia.pokemonweekseventask.domain.entities

data class PokemonPaged (
    val count: Long? = null,
    val next: String? = null,
    val previous: String? = null,
    val results: ArrayList<Pokemon>? = arrayListOf()
        )