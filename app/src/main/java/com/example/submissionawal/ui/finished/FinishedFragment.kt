package com.example.submissionawal.ui.finished

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
import com.example.submissionawal.databinding.FragmentFinishedBinding

class FinishedFragment : Fragment() {

    private var _binding: FragmentFinishedBinding? = null
    private val finishedViewModel by viewModels<FinishedViewModel>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentFinishedBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        binding.rvFinished.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        binding.rvFinished.addItemDecoration(itemDecoration)

        finishedViewModel.listEvent.observe(viewLifecycleOwner) { listEvent ->
            Log.i("FinishedFragment", listEvent.listEvents.toString())

            val adapter = EventAdapter()
            adapter.submitList(listEvent.listEvents)
            binding.rvFinished.adapter = adapter
        }

        finishedViewModel.isLoadingFinished.observe(viewLifecycleOwner) { isLoadingFinished ->
            binding.pbFinished2.visibility = if (isLoadingFinished) View.VISIBLE else View.GONE
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}
    }