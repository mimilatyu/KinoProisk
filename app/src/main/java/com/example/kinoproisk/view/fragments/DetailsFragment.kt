package com.example.kinoproisk.view.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.kinoproisk.R
import com.example.kinoproisk.data.ApiConstants
import com.example.kinoproisk.databinding.FragmentDetailsBinding
import com.example.kinoproisk.domain.Film


class DetailsFragment : Fragment() {
    private lateinit var film: Film
    private lateinit var binding: FragmentDetailsBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewFill()

        binding.detailsFabFavorites.setOnClickListener {
            if(!film.isInFavorites) {
                binding.detailsFabFavorites.setImageResource(R.drawable.ic_baseline_favorite_24)
                film.isInFavorites = true
            } else {
                binding.detailsFabFavorites.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                film.isInFavorites = false
            }
        }

        binding.detailsFab.setOnClickListener{
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(
                    Intent.EXTRA_TEXT,
                "Check out this film: ${film.title} \n\n ${film.description}"
            )

            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, "Share To:"))

        }
    }


    private fun viewFill(){
        film = arguments?.get("film") as Film

        binding.detailsToolbar.title = film.title
        Glide.with(this)
            .load(ApiConstants.IMAGES_URL + "w780" + film.poster)
            .centerCrop()
            .into(binding.detailsPoster)
        binding.detailsDescription.text = film.description

        binding.detailsFabFavorites.setImageResource(
            if(film.isInFavorites) R.drawable.ic_round_favorite
            else R.drawable.ic_baseline_favorite_border_24
        )
    }

}