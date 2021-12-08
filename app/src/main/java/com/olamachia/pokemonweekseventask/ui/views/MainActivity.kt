package com.olamachia.pokemonweekseventask.ui.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.olamachia.pokemonweekseventask.R
import com.olamachia.pokemonweekseventask.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(view.findViewById(R.id.toolbar))

        navController = findNavController(R.id.mainNavGraphFragmentContainerView)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.okButton.setOnClickListener {

            var offset = 0
            var limit = 0

            if(validateInputs(
                    binding.offsetEdiText.text?.toString(),
                    binding.limitEdiText.text?.toString())
            ) {
                offset = binding.offsetEdiText.text?.toString()?.trim()?.toInt()!!
                limit = binding.limitEdiText.text?.toString()?.trim()?.toInt()!!
            } else {
                Toast.makeText(
                    this,
                    "Input must be a number and not empty",
                    Toast.LENGTH_LONG).show()
            }
            navController.navigateUp()
            navController.navigate(
                R.id.searchFragment,
                bundleOf(
                    OFF_SET to offset,
                    LIMIT to limit
                )
            )
        }
    }

    override fun onSupportNavigateUp() = navController.navigateUp(appBarConfiguration) ||
            super.onSupportNavigateUp()

    private fun validateInputs(offset: String?, limit: String?): Boolean {

        return offset?.trim()?.isNotEmpty() == true &&
                offset.trim().isDigitsOnly() &&
                limit?.trim()?.isNotEmpty() == true &&
                limit.trim().isDigitsOnly()

    }

    companion object {
        const val OFF_SET = "OFFSET"
        const val LIMIT = "LIMIT"
    }

}