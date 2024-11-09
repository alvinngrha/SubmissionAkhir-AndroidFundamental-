package com.example.submissionawal

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissionawal.data.remote.response.Event
import com.example.submissionawal.databinding.ListEventBinding
import com.example.submissionawal.ui.detailEvent.DetailEventActivity

class EventAdapter : ListAdapter<Event, EventAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event, holder.itemView.context)
    }

    class MyViewHolder(val binding: ListEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: Event, context: Context){
            binding.tvEventname.text = "${event.name}"
            binding.tvCityname.text = "📍 ${event.cityName}"
            binding.tvOwnername.text = " ${event.ownerName}"
            Glide.with(context)
                .load(event.mediaCover)
                .into(binding.ivEvent)

            binding.cvEvent.setOnClickListener {
                val moveIntent = Intent(context, DetailEventActivity::class.java)
                moveIntent.putExtra(EXTRA_EVENT, event.id)
                context.startActivity(moveIntent)

            }
        }
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Event>() {
            override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
                return oldItem == newItem
            }
        }

        const val EXTRA_EVENT = "extra_event"
    }
}
