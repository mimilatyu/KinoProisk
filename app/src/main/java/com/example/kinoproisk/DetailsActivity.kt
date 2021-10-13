package com.example.kinoproisk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kinoproisk.databinding.ActivityDetailsBinding
import com.google.android.material.snackbar.Snackbar

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewFill()
        addSnackbar()


    }

    private fun viewFill(){
        val film = intent.extras?.get("film") as Film

        binding.detailsToolbar.title = film.title
        binding.detailsPoster.setImageResource(film.poster)
        binding.detailsDescription.text = film.description
    }

    private fun addSnackbar(){
        val snackbar = Snackbar.make(binding.detailsAc, "Типо добавили в Избранное", Snackbar.LENGTH_SHORT)
        binding.detailsTofav.setOnClickListener{
            snackbar.show()
        }

        val snackbar2 = Snackbar.make(binding.detailsAc, "Типо Посмотрите этот фильм позже", Snackbar.LENGTH_SHORT)
        binding.detailsLater.setOnClickListener{
            snackbar2.show()
        }
    }
}