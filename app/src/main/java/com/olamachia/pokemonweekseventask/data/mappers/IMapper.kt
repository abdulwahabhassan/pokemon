package com.olamachia.pokemonweekseventask.data.mappers

interface IMapper<E, R> {

    fun mapToEntity(type: R): E

    fun mapToEntityList(models: ArrayList<R>?): ArrayList<E> {
        val list = arrayListOf<E>()
        models?.forEach {
            list.add(mapToEntity(it))
        }
        return list
    }
}