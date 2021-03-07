package com.udacity.asteroidradar.main

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.udacity.asteroidradar.Network.filter
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.Resource
import com.udacity.asteroidradar.database.DatabaseAsteriod
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.domain.DomainAsteriod
import com.udacity.asteroidradar.domain.PictureOfDay

class MainFragment : Fragment() {

    lateinit var adapter: AsteriodAdapter
    val viewModel: MainViewModel by lazy {
        val application = requireNotNull(this.activity).application
        val database = DatabaseAsteriod.getDatabase(application)
        ViewModelProvider(this, AsteroidViewFactory(MainViewRepository(database.asteriodDao())))
            .get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.pic = viewModel

        adapter = AsteriodAdapter(AsteriodAdapter.OnClickListener {
            viewModel.setNavigation(it)
        })
        val manager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        viewModel.statusNavigation.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                viewModel.clearStatusNavigation()
            }
        })

        viewModel.asteroidList.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        //binding.progressBar.visibility = View.GONE
                        if (!it.data.isNullOrEmpty()) adapter.submitList(it.data)
                    }
                    Resource.Status.ERROR ->
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

//                Resource.Status.LOADING ->
//                binding.progressBar.visibility = View.VISIBLE
                }
            }
        })

        viewModel.pictureOfDay.observe(viewLifecycleOwner, Observer {

        })


        binding.asteroidRecycler.layoutManager = manager
        binding.asteroidRecycler.adapter = adapter


        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        viewModel.filterAsteroid(
            when (item.itemId) {
                R.id.show_all_menu -> filter.Show_week.value
                R.id.show_today_menu -> filter.Show_Today.value
                else -> filter.Show_All.value
            }
        ).observe(viewLifecycleOwner, Observer {
            if (it != null) {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        //binding.progressBar.visibility = View.GONE
                        if (!it.data.isNullOrEmpty()) adapter.submitList(it.data)
                    }
                    Resource.Status.ERROR ->
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

//                Resource.Status.LOADING ->
//                binding.progressBar.visibility = View.VISIBLE
                }
            }
        })
        return true
    }
}
