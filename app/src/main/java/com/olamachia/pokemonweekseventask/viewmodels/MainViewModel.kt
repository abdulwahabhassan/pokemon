package com.olamachia.pokemonweekseventask.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.olamachia.pokemonweekseventask.domain.Result
import com.olamachia.pokemonweekseventask.domain.entities.Pokemon
import com.olamachia.pokemonweekseventask.domain.entities.PokemonDetails
import com.olamachia.pokemonweekseventask.domain.usecases.GetPokemonByLimitUseCase
import com.olamachia.pokemonweekseventask.domain.usecases.GetPokemonDetailsUseCase
import com.olamachia.pokemonweekseventask.domain.usecases.GetPokemonPagedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (
    private val getPokemonPagedUseCase: GetPokemonPagedUseCase,
    private val getPokemonDetailsUseCase: GetPokemonDetailsUseCase,
    private val getPokemonByLimitUseCase: GetPokemonByLimitUseCase
        ): ViewModel() {

    private val _pokemonDetails = MutableLiveData<PokemonDetails>()
        val pokemonDetails = _pokemonDetails

    private val _limitedPokemon = MutableLiveData<ArrayList<Pokemon>>()
        val limitedPokemon = _limitedPokemon

    fun getPokemonPagedStream(): Flow<PagingData<Pokemon>> {
        return getPokemonPagedUseCase.invoke().cachedIn(viewModelScope)
    }

    suspend fun getPokemonByLimit(offset:Int, limit: Int) {
        viewModelScope.launch {
            when (val result = getPokemonByLimitUseCase.invoke(offset, limit)) {
                is Result.Success -> {
                    _limitedPokemon.postValue(result.data.results)
                }
            }
        }
    }

    suspend fun getPokemonDetails(pokemonId: Int) {
        viewModelScope.launch {
            when (val result = getPokemonDetailsUseCase.invoke(pokemonId)) {
                is Result.Success -> {
                    _pokemonDetails.postValue(result.data)
                }
            }
        }
    }

}