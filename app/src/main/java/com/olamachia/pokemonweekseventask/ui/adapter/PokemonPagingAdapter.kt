package com.olamachia.pokemonweekseventask.ui.adapter

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.olamachia.pokemonweekseventask.ui.glide.GlideImageLoader
import com.olamachia.pokemonweekseventask.databinding.ItemPokemonBinding
import com.olamachia.pokemonweekseventask.domain.Result
import com.olamachia.pokemonweekseventask.domain.entities.Pokemon
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import kotlin.coroutines.cancellation.CancellationException


class PokemonPagingAdapter constructor(
    private val clickListener: ItemClickListener
        ) : PagingDataAdapter<Pokemon, PokemonPagingAdapter.PokemonViewHolder>(POKEMON_COMPARATOR) {

    interface ItemClickListener {
        fun onPokemonClicked(pokemonId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val viewBinding = ItemPokemonBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(viewBinding)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(viewHolder: PokemonViewHolder, position: Int) {
        val animation = android.view.animation.AnimationUtils
            .loadAnimation(viewHolder.itemView.context, android.R.anim.slide_in_left)
        viewHolder.bind(getItem(position), clickListener, viewHolder)
        viewHolder.itemView.startAnimation(animation)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class PokemonViewHolder (private val itemBinding : ItemPokemonBinding)
        : RecyclerView.ViewHolder(itemBinding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        @SuppressLint("SetTextI18n")
        fun bind(
            pokemon: Pokemon?,
            clickListener: ItemClickListener,
            viewHolder: PokemonViewHolder
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
                        pokemonId -> clickListener.onPokemonClicked((pokemonId.toInt()))
                }
            }
        }
    }

    companion object {
        private val POKEMON_COMPARATOR = object : DiffUtil.ItemCallback<Pokemon>() {
            override fun areItemsTheSame(
                oldItem: Pokemon,
                newItem: Pokemon
            ): Boolean {
                return Uri.parse(oldItem.url).lastPathSegment ==
                        Uri.parse(newItem.url).lastPathSegment
            }

            override fun areContentsTheSame(
                oldItem: Pokemon,
                newItem: Pokemon
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}

