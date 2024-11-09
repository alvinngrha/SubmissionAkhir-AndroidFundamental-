package com.example.submissionawal.ui.upcoming

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionawal.EventAdapter
import com.example.submissionawal.databinding.FragmentUpcomingBinding

class UpcomingFragment : Fragment() {

    private var _binding: FragmentUpcomingBinding? = null
    private val upcomingViewModel by viewModels<UpcomingViewModel>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        binding.rvUpcoming.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        binding.rvUpcoming.addItemDecoration(itemDecoration)

        upcomingViewModel.listEvent.observe(viewLifecycleOwner) { listEvent ->
            Log.i("UpcomingFragment", listEvent.listEvents.toString())

            val adapter = EventAdapter()
            adapter.submitList(listEvent.listEvents)
            binding.rvUpcoming.adapter = adapter
        }

        upcomingViewModel.isLoadingUpcoming.observe(viewLifecycleOwner) { isLoadingUpcoming ->
            binding.pbUpcoming2.visibility = if (isLoadingUpcoming) View.VISIBLE else View.GONE
        }

//        override fun onDestroyView() {
//            super.onDestroyView()
//            _binding = null
//        }
    }
}