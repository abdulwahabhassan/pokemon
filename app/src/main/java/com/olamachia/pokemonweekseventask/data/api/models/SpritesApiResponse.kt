package com.olamachia.pokemonweekseventask.data.api.models

import com.google.gson.annotations.SerializedName
import com.olamachia.pokemonweekseventask.domain.entities.Other

data class SpritesApiResponse (
    @SerializedName("front_default")
    val frontDefault: String? = null,
    @SerializedName("front_shiny")
    val frontShiny: String? = null,
    val other: OtherApiResponse
)
