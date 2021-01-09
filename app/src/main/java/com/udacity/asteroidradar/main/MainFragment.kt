package com.udacity.asteroidradar.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.database.DatabaseAsteriod
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.domain.DomainAsteriod

class MainFragment : Fragment() {

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




        binding.viewModel = viewModel

        val adapter = AsteriodAdapter(AsteriodAdapter.OnClickListener {
            viewModel.setNavigation(it)
        })
        val manager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        viewModel.statusNavigation.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                viewModel.clearStatusNavigation()
            }
        })

        viewModel.asteriodList.observe(viewLifecycleOwner, Observer {
            Log.i("yarab", it.toString())
            adapter.submitList(it)
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
        when (item.itemId) {
            R.id.show_all_menu -> showAll()

        }
        return true
    }

    private fun showAll() {
        viewModel.showAll()
    }
}
