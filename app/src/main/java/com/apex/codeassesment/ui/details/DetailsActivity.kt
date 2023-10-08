package com.apex.codeassesment.ui.details

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.apex.codeassesment.R
import com.apex.codeassesment.data.model.Coordinates
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.databinding.ActivityDetailsBinding
import com.apex.codeassesment.ui.location.LocationActivity
import com.bumptech.glide.Glide

// TODO (3 points): Convert to Kotlin
// TODO (3 points): Remove bugs or crashes if any
// TODO (1 point): Add content description to images
// TODO (2 points): Add tests
class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = ActivityDetailsBinding.inflate(
            layoutInflater
        )
        setContentView(binding.root)


        // TODO (1 point): Use Glide to load images
        bindDataAndImageLoad()

    }

    private fun bindDataAndImageLoad() {
        val user = intent.getParcelableExtra<User>("saved-user-key")
        val name = user!!.name
        Glide.with(this).load(user.picture).placeholder(R.drawable.no_image_found)
            .into(binding.detailsImage)
        binding.detailsName.text = getString(R.string.details_name, name!!.first, name.last)
        binding.detailsEmail.text = getString(
            R.string.details_email,
            user.gender ?: "no email found"
        )
        binding.detailsAge.text = user.dob!!.age.toString()
        val coordinates = user.location!!.coordinates
        binding.detailsLocation.text =
            getString(R.string.details_location, coordinates!!.latitude, coordinates.longitude)
        binding.detailsLocationButton.setOnClickListener {
            navigateLocation(
                coordinates
            )
        }
    }

    private fun navigateLocation(coordinates: Coordinates) {
        val intent = Intent(this, LocationActivity::class.java)
            .putExtra("user-latitude-key", coordinates.latitude)
            .putExtra("user-longitude-key", coordinates.longitude)
        startActivity(intent)
    }
}