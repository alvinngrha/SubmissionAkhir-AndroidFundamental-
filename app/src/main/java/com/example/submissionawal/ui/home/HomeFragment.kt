package com.example.submissionawal.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionawal.EventAdapter
import com.example.submissionawal.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var homeFragmentBinding: FragmentHomeBinding
    private val homeViewModel by viewModels<HomeViewModel>()
    private lateinit var searchAdapter: EventAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        homeFragmentBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return homeFragmentBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView untuk Upcoming Events
        homeFragmentBinding.rvHome1.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val upcomingAdapter = EventAdapter()
        homeFragmentBinding.rvHome1.adapter = upcomingAdapter

        // RecyclerView untuk Finished Events
        homeFragmentBinding.rvHome2.layoutManager = LinearLayoutManager(context)
        val finishedAdapter = EventAdapter()
        homeFragmentBinding.rvHome2.adapter = finishedAdapter

        // Observe data untuk Upcoming Events
        homeViewModel.listEventUpcoming.observe(viewLifecycleOwner) { listEventUpcoming ->
            upcomingAdapter.submitList(listEventUpcoming.listEvents)
        }

        // Observe data untuk Finished Events
        homeViewModel.listEventFinished.observe(viewLifecycleOwner) { listEventFinished ->
            finishedAdapter.submitList(listEventFinished.listEvents)
        }

        homeViewModel.upcomingIsLoading.observe(viewLifecycleOwner) { upcomingIsLoading ->
            homeFragmentBinding.pbUpcoming.visibility = if (upcomingIsLoading) View.VISIBLE else View.GONE
        }

        homeViewModel.finishedIsLoading.observe(viewLifecycleOwner) { finishedIsLoading ->
            homeFragmentBinding.pbFinished.visibility = if (finishedIsLoading) View.VISIBLE else View.GONE
        }

        // Setup RecyclerView untuk Search Results
        homeFragmentBinding.rvSearchResults.layoutManager = LinearLayoutManager(context)
        searchAdapter = EventAdapter()
        homeFragmentBinding.rvSearchResults.adapter = searchAdapter

        // Observe search results
        homeViewModel.searchList.observe(viewLifecycleOwner) { listEventResponse ->
            if (listEventResponse != null) {
                // Show search results and hide upcoming and finished events
                searchAdapter.submitList(listEventResponse.listEvents)
                homeFragmentBinding.rvSearchResults.visibility = View.VISIBLE
                homeFragmentBinding.rvHome1.visibility = View.GONE
                homeFragmentBinding.rvHome2.visibility = View.GONE
                homeFragmentBinding.tvHome1.visibility = View.GONE // Hide title "Upcoming Event"
                homeFragmentBinding.tvHome2.visibility = View.GONE // Hide title "Finished Event"
            } else {
                // Show upcoming and finished events and hide search results
                homeFragmentBinding.rvSearchResults.visibility = View.GONE
                homeFragmentBinding.rvHome1.visibility = View.VISIBLE
                homeFragmentBinding.rvHome2.visibility = View.VISIBLE
                homeFragmentBinding.tvHome1.visibility = View.VISIBLE // Show title "Upcoming Event"
                homeFragmentBinding.tvHome2.visibility = View.VISIBLE // Show title "Finished Event"
            }
        }

        // Implementasi search
        homeFragmentBinding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null && query.isNotEmpty()) {
                    homeViewModel.searchEvents(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        homeFragmentBinding.searchView.setOnClickListener {
            homeFragmentBinding.searchView.isIconified = false // Membuka SearchView penuh
    }

        // Panggil API untuk mendapatkan data event upcoming dan finished
        homeViewModel.getUpcomingEvents() // Memanggil event yang upcoming
        homeViewModel.getFinishedEvents() // Memanggil event yang finished
    }
}

