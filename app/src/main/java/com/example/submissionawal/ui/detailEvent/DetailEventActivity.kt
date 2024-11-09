package com.example.submissionawal.ui.detailEvent

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.example.submissionawal.EventAdapter
import com.example.submissionawal.R
import com.example.submissionawal.data.local.entity.FavoriteEvent
import com.example.submissionawal.data.remote.response.DetailEventResponse
import com.example.submissionawal.data.remote.response.Event
import com.example.submissionawal.databinding.ActivityDetailEventBinding
import com.example.submissionawal.ui.FavoriteViewModelFactory
import com.example.submissionawal.ui.favorite.FavoriteViewModel

class DetailEventActivity : AppCompatActivity() {

    private lateinit var detailEventBinding: ActivityDetailEventBinding

    private val detailEventViewModel by viewModels<DetailEventViewModel>()

    private val favoriteViewModel by viewModels<FavoriteViewModel> {
        FavoriteViewModelFactory.getInstance(this)
    }

    private var isFavorite = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailEventBinding = ActivityDetailEventBinding.inflate(layoutInflater)
        setContentView(detailEventBinding.root)

        // Dapatkan eventId dari intent
        val eventId = intent.getIntExtra(EventAdapter.EXTRA_EVENT, 0)

        // Observe loading state untuk menampilkan progress bar
        detailEventViewModel.isLoadingDetailEvent.observe(this) { isLoading ->
            detailEventBinding.pbDetail.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        // Panggil API untuk mendapatkan detail event
        detailEventViewModel.detailEvent(eventId)

        // Observe data dari ViewModel
        detailEventViewModel.detailEvent.observe(this) { detailEventResponse ->
            detailEventResponse?.let {
                displayEventDetail(it)
                checkFavoriteStatus(eventId)
            }
        }
    }

    private fun displayEventDetail(detailEventResponse: DetailEventResponse) {
        val event = detailEventResponse.event
        detailEventBinding.apply {
            tvEventName.text = event.name
            tvOwnerName.text = event.ownerName
            tvDescription.text = HtmlCompat.fromHtml(
                event.description.toString(),
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
            tvCityName.text = event.cityName
            tvBeginTime.text = event.beginTime
            tvEndTime.text = event.endTime
            tvRegistrants.text = "Registrants: ${event.registrants}"
            tvQuota.text = "Quota: ${event.quota - event.registrants}"
            tvCategory.text = event.cityName

            // Gunakan Glide untuk load image
            Glide.with(this@DetailEventActivity)
                .load(event.mediaCover)
                .into(detailEventBinding.ivMediaCover)

            supportActionBar?.title = event?.ownerName

            detailEventBinding.btnRegister.setOnClickListener{
                // Buat intent untuk membuka link di browser
                val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(event.link)
                    startActivity(intent)
                }
        }
            detailEventBinding.floatingBtnFavorite.setOnClickListener {
                moveFavorite(event)
                updateFavoriteIcon()
            }
    }

    private fun moveFavorite(event: Event) {

        val favoriteEvent = FavoriteEvent(
            id = event.id,
            name = event.name,
            summary = event.summary,
            imageLogo = event.mediaCover,
            favorite = true
        )

        if (isFavorite) {
            favoriteViewModel.delete(favoriteEvent)
            Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show()
        } else {
            favoriteViewModel.insert(favoriteEvent)
            Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show()
        }
        isFavorite = !isFavorite
        updateFavoriteIcon()
    }

    private fun updateFavoriteIcon() {
        detailEventBinding.floatingBtnFavorite.setImageResource(
            if (isFavorite) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24
        )
    }

    private fun checkFavoriteStatus(eventId: Int) {
        favoriteViewModel.isFavorite(eventId).observe(this) { isFav ->
            isFavorite = isFav ?: false
            updateFavoriteIcon()
        }
    }
}
