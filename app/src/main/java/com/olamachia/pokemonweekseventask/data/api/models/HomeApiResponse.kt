package com.olamachia.pokemonweekseventask.data.api.models

import com.google.gson.annotations.SerializedName

data class HomeApiResponse (
    @SerializedName("front_default")
    val fontDefault: String? = null,
    @SerializedName("front_shiny")
    val frontShiny: String? = null
)
