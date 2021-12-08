package com.olamachia.pokemonweekseventask.ui.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.olamachia.pokemonweekseventask.R
import com.olamachia.pokemonweekseventask.databinding.FragmentPokemonBinding
import com.olamachia.pokemonweekseventask.databinding.FragmentPokemonDetailsBinding
import com.olamachia.pokemonweekseventask.databinding.FragmentSearchBinding
import com.olamachia.pokemonweekseventask.ui.adapter.PokemonRecyclerAdapter
import com.olamachia.pokemonweekseventask.viewmodels.MainViewModel
import kotlinx.coroutines.launch


class SearchFragment : Fragment(), PokemonRecyclerAdapter.ItemClickListener {
    private val args: SearchFragmentArgs by navArgs()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerViewAdapter: PokemonRecyclerAdapter
    private lateinit var recyclerview: RecyclerView
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        recyclerview = binding.pokemonRecyclerView
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val offset = arguments?.getInt(OFF_SET) ?: 0
        val limit = arguments?.getInt(LIMIT) ?: 0

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.getPokemonByLimit(offset, limit)
            }
        }

        viewModel.limitedPokemon.observe(viewLifecycleOwner) { pokemon ->
            recyclerViewAdapter = PokemonRecyclerAdapter(pokemon, this)
            recyclerview.adapter = recyclerViewAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemClick(pokemonId: Int) {
        val action = SearchFragmentDirections
            .actionSearchFragmentToPokemonDetailsFragment(pokemonId)
        findNavController().navigate(action)
    }

    companion object {
            const val OFF_SET = "OFFSET"
            const val LIMIT = "LIMIT"
    }

}