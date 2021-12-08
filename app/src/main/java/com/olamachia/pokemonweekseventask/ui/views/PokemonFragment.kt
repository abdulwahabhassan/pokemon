package com.olamachia.pokemonweekseventask.ui.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.olamachia.pokemonweekseventask.databinding.FragmentPokemonBinding
import com.olamachia.pokemonweekseventask.ui.adapter.PokemonPagingAdapter
import com.olamachia.pokemonweekseventask.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class PokemonFragment : Fragment(), PokemonPagingAdapter.ItemClickListener {

    private var _binding: FragmentPokemonBinding? = null
    private val binding get() = _binding!!
    private lateinit var pokemonAdapter: PokemonPagingAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPokemonBinding.inflate(inflater, container, false)
        recyclerView = binding.pokemonRecyclerView
        swipeRefreshLayout = binding.swipeRefreshLayout
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pokemonAdapter = PokemonPagingAdapter(this)
        recyclerView.adapter = pokemonAdapter

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated{
            viewModel.getPokemonPagedStream().collectLatest {
                    pagingData ->
                pokemonAdapter.submitData(pagingData)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            pokemonAdapter.loadStateFlow.collectLatest { loadStates ->
                when (loadStates.refresh) {
                    is LoadState.Error -> {
                        Toast.makeText(
                            context,
                            "Error fetching pokemon! Retrying..",
                            Toast.LENGTH_SHORT).show()
                        pokemonAdapter.retry()
                    }
                    is LoadState.Loading -> {
                        swipeRefreshLayout.isRefreshing = true
                    }
                    is LoadState.NotLoading -> {
                        swipeRefreshLayout.isRefreshing = false
                    }
                }

                when (loadStates.append) {
                    is LoadState.Error -> {
                        Toast.makeText(
                            context,
                            "Error fetching more pokemon! Retrying..",
                            Toast.LENGTH_SHORT).show()
                        pokemonAdapter.retry()
                    }
                    is LoadState.Loading -> {
                        swipeRefreshLayout.isRefreshing = true
                    }
                    is LoadState.NotLoading -> {
                        if (loadStates.append.endOfPaginationReached) {
                            swipeRefreshLayout.isRefreshing = false
                        }
                    }
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onPokemonClicked(pokemonId: Int) {
        val action = PokemonFragmentDirections
                .actionPokemonFragmentToPokemonDetailsFragment(pokemonId)
            findNavController().navigate(action)
    }
}
