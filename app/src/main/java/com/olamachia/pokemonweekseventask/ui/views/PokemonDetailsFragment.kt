package com.olamachia.pokemonweekseventask.ui.views

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.olamachia.pokemonweekseventask.ui.glide.GlideImageLoader
import com.olamachia.pokemonweekseventask.databinding.FragmentPokemonDetailsBinding
import com.olamachia.pokemonweekseventask.domain.entities.PokemonDetails
import com.olamachia.pokemonweekseventask.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonDetailsFragment : Fragment() {

    private val args: PokemonDetailsFragmentArgs by navArgs()
    private var _binding: FragmentPokemonDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPokemonDetailsBinding.inflate(inflater, container, false)
        binding.composeView.apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                // Compose
                MaterialTheme {

                    viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                        viewModel.getPokemonDetails(args.pokemonId)
                    }
                    val pokemonDetails by viewModel.pokemonDetails.observeAsState(PokemonDetails())

                    Column(
                        Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally) {

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = "Abilities",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )

                        LazyRow(Modifier.fillMaxWidth()){
                            pokemonDetails.abilities?.size?.let { count ->
                                items(count){ index ->
                                    Box(modifier = Modifier.padding(end = 8.dp)) {
                                        Text(
                                            modifier = Modifier
                                                .background(
                                                    color = Color.Black,
                                                    shape = RoundedCornerShape(8.dp)
                                                )
                                                .padding(8.dp),
                                            text = "${pokemonDetails?.abilities!![index].ability.name}",
                                            fontWeight = FontWeight.Medium,
                                            color = Color.White
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = "Forms",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )

                        LazyRow(Modifier.fillMaxWidth()){
                            pokemonDetails.forms?.size?.let { count ->
                                items(count){ index ->
                                    Box(modifier = Modifier.padding(end = 8.dp)) {
                                        Text(
                                            modifier = Modifier
                                                .background(
                                                    color = Color.Black,
                                                    shape = RoundedCornerShape(8.dp)
                                                )
                                                .padding(8.dp),
                                            text = "${pokemonDetails?.forms!![index].name}",
                                            fontWeight = FontWeight.Medium,
                                            color = Color.White
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = "Types",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )

                        LazyRow(Modifier.fillMaxWidth()){
                            pokemonDetails.types?.size?.let { count ->
                                items(count){ index ->
                                    Box(modifier = Modifier.padding(end = 8.dp)) {
                                        Text(
                                            modifier = Modifier
                                                .background(
                                                    color = Color.Black,
                                                    shape = RoundedCornerShape(8.dp)
                                                )
                                                .padding(8.dp),
                                            text = "${pokemonDetails?.types!![index].type.name}",
                                            fontWeight = FontWeight.Medium,
                                            color = Color.White
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = "Moves",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )

                        LazyRow(Modifier.fillMaxWidth()){
                            pokemonDetails.moves?.size?.let { count ->
                                items(count){ index ->
                                    Box(modifier = Modifier.padding(end = 8.dp).background(
                                        color = Color.Transparent,
                                        shape = RoundedCornerShape(8.dp))) {
                                        Text(
                                            modifier = Modifier
                                                .background(
                                                    color = Color.Black,
                                                    shape = RoundedCornerShape(8.dp)
                                                )
                                                .padding(8.dp),
                                            text = "${pokemonDetails?.moves!![index].move?.name}",
                                            fontWeight = FontWeight.Medium,
                                            color = Color.White
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.pokemonDetails.observe(viewLifecycleOwner, {

            binding.pokemonName.text = it.name
            binding.heightTextView.text = it.height.toString()
            binding.weightTextView.text = it.weight.toString()

            var totalBaseStat = 0

            it.stats?.forEach { stats ->
                totalBaseStat += stats.baseStat!!
            }

            binding.baseStatProgressBar.progress = totalBaseStat
            binding.avgStatTextView.text = totalBaseStat.toString()

            val options = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
            GlideImageLoader(
                binding.pokemonImage,
                binding.photoProgressBar,
                null,
                binding.pokemonName).load(it.sprites?.other?.home?.fontDefault, options)
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}