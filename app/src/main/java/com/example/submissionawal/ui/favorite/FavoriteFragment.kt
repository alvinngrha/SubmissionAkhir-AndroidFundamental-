package com.example.submissionawal.ui.favorite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionawal.EventAdapter
import com.example.submissionawal.data.local.entity.FavoriteEvent
import com.example.submissionawal.data.remote.response.Event
import com.example.submissionawal.databinding.FragmentFavoriteBinding
import com.example.submissionawal.ui.FavoriteViewModelFactory

class FavoriteFragment : Fragment() {

    private var _fragmentFavoriteBinding: FragmentFavoriteBinding? = null

    private val favoriteViewModel by viewModels<FavoriteViewModel> {
        FavoriteViewModelFactory.getInstance(requireActivity())
    }

    private val binding get() = _fragmentFavoriteBinding

    private val eventAdapter = EventAdapter()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentFavoriteBinding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.rvFavorite?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = eventAdapter
        }

//         Langsung ambil data favorite saat view created
//        favoriteViewModel.getFavoriteEvents()

        // Observe perubahan data
        favoriteViewModel.favoriteEvents.observe(viewLifecycleOwner) { result ->
            Log.d("FavoriteFragment", "Favorite events result: $result")
            if (result != null) {
                binding?.pbFavorite?.visibility = View.GONE
                val favoriteData = convertToEvent(result)
                Log.d("FavoriteFragment", "Received favorite data: $favoriteData")
                eventAdapter.submitList(favoriteData)
                binding?.rvFavorite?.adapter = eventAdapter
            } else {
                binding?.pbFavorite?.visibility = View.VISIBLE
            }
        }
    }

private fun convertToEvent(favoriteEvents: List<FavoriteEvent>): List<Event> {
        return favoriteEvents.map { favorite ->
            Event(
                id = favorite.id,
                name = favorite.name,
                summary = favorite.summary ?: "",
                imageLogo = favorite.imageLogo ?: "",
                mediaCover = favorite.imageLogo ?: "",
                beginTime = "",
                category = "",
                cityName = "",
                description = "",
                endTime = "",
                link = "",
                ownerName = "",
                quota = 0,
                registrants = 0
            )
        }
    }

    override fun onStart() {
        super.onStart()
        favoriteViewModel.getFavorites()
    }

    override fun onDestroy() {
        super.onDestroy()
        _fragmentFavoriteBinding = null
    }


}