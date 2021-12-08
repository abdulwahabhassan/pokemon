package com.olamachia.pokemonweekseventask.data.api.models

import com.google.gson.annotations.SerializedName

data class StatsApiResponse (
    @SerializedName("base_stat")
    val baseStat: Int? = 0,
    val stat: StatApiResponse
        )