package com.example.kinoproisk


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kinoproisk.databinding.FragmentDetailsBinding
import com.google.android.material.snackbar.Snackbar


class DetailsFragment : Fragment() {
    private lateinit var film: Film
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
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
        binding.detailsPoster.setImageResource(film.poster)
        binding.detailsDescription.text = film.description

        binding.detailsFabFavorites.setImageResource(
            if(film.isInFavorites) R.drawable.ic_round_favorite
            else R.drawable.ic_baseline_favorite_border_24
        )
    }

}