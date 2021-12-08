package com.olamachia.pokemonweekseventask.data.api.models

class PokemonPagedApiResponse (
    val count: Long? = null,
    val next: String? = null,
    val previous: String? = null,
    val results: ArrayList<PokemonApiResponse>? = arrayListOf()
        )