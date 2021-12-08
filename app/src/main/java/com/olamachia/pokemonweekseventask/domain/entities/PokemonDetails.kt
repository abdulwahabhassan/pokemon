package com.olamachia.pokemonweekseventask.domain.entities

data class PokemonDetails (
    val id: Long? = null,
    val name: String? = null,
    val abilities: ArrayList<Ability>? = arrayListOf(),
    val forms: ArrayList<Species>? = arrayListOf(),
    val height: Long? = null,
    val types: ArrayList<Type>? = arrayListOf(),
    val weight: Long? = null,
    val sprites: Sprites? = null,
    val stats: ArrayList<Stats>? = arrayListOf(),
    val moves: ArrayList<Moves>? = arrayListOf()
        )