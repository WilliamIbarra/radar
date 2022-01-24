package com.udacity.asteroidradar.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.Constants.SAVED_ASTEROIDS
import com.udacity.asteroidradar.Constants.TODAY_ASTEROIDS
import com.udacity.asteroidradar.Constants.WEEK_ASTEROIDS
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.adapter.AsteroidAdapter
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {


    private val viewModel: MainViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, MainViewModel.Factory(activity.application))[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.asteroidRecycler.adapter = AsteroidAdapter(
            AsteroidAdapter.AsteroidListener { asteroid ->
//                TODO("make a section to call a detail of an asteroid")
                findNavController().navigate(MainFragmentDirections.actionShowDetail(asteroid))
            }
        )

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.show_all_menu -> {
                Log.d("MenuOption", "Show week asteroids")
                viewModel.filterAsteroids(WEEK_ASTEROIDS)
            }
            R.id.show_rent_menu -> {
                Log.d("MenuOption", "Show today asteroids")
                viewModel.filterAsteroids(TODAY_ASTEROIDS)
            }
            R.id.show_buy_menu -> {
                Log.d("MenuOption", "Show saved asteroids")
                viewModel.filterAsteroids(SAVED_ASTEROIDS)
            }
        }

        return true
    }
}
