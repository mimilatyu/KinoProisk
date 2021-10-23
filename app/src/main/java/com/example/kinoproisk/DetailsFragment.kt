package com.example.kinoproisk


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kinoproisk.databinding.FragmentDetailsBinding
import com.google.android.material.snackbar.Snackbar


class DetailsFragment : Fragment() {
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

        addSnackbar()
        viewFill()
    }


    private fun viewFill(){
        val film = arguments?.get("film") as Film

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