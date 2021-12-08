package com.olamachia.pokemonweekseventask.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.olamachia.pokemonweekseventask.databinding.ItemPokemonBinding
import com.olamachia.pokemonweekseventask.domain.entities.Pokemon
import com.olamachia.pokemonweekseventask.ui.glide.GlideImageLoader

class PokemonRecyclerAdapter (
    private val listOfPokemon: ArrayList<Pokemon>,
    private val clickListener: ItemClickListener
        ) : RecyclerView.Adapter<PokemonRecyclerAdapter.PokemonViewHolder>() {

    interface ItemClickListener {
        fun onItemClick(pokemonId: Int)
    }

    inner class PokemonViewHolder (private val itemBinding : ItemPokemonBinding)
        : RecyclerView.ViewHolder(itemBinding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(
            pokemon: Pokemon?,
            clickListener: ItemClickListener,
        ) = with(itemBinding) {

            pokemonName.text = pokemon?.name

            val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites" +
                    "/pokemon/other/home/${Uri.parse(pokemon?.url).lastPathSegment}.png"

            val options = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            GlideImageLoader(pokemonImage, photoProgressBar, root, pokemonName)
                .load(imageUrl, options)

            root.setOnClickListener {
                Uri.parse(pokemon?.url).lastPathSegment?.let {
                        pokemonId -> clickListener.onItemClick(pokemonId.toInt())
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val viewBinding = ItemPokemonBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(viewBinding)
    }


    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = listOfPokemon[position]
        holder.bind(pokemon, clickListener)
    }

    override fun getItemCount(): Int {
        return listOfPokemon.size
    }
}