package com.example.kinoproisk.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kinoproisk.view.MainActivity
import com.example.kinoproisk.R
import com.example.kinoproisk.databinding.FragmentHomeBinding
import com.example.kinoproisk.domain.Film
import com.example.kinoproisk.utils.AnimationHelper
import com.example.kinoproisk.view.rv_adapters.FilmListRecyclerAdapter
import com.example.kinoproisk.view.rv_adapters.TopSpacingItemDecoration
import com.example.kinoproisk.viewmodel.HomeFragmentViewModel
import java.util.*

class HomeFragment : Fragment() {

    private val viewModel by lazy{
        ViewModelProvider.NewInstanceFactory().create(HomeFragmentViewModel::class.java)
    }

    private lateinit var filmsAdapter: FilmListRecyclerAdapter
    private lateinit var  binding: FragmentHomeBinding

    private var filmsDataBase = listOf<Film>()
        set(value) {
            if (field == value) return
            field = value
            filmsAdapter.addItems(field)
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AnimationHelper.performFragmentCircularRevealAnimation(binding.homeFragmentRoot, requireActivity(), 1)


        //нашли RV
        binding.mainRecycler.apply {
            filmsAdapter = FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener{
                override fun click(film: Film) {
                    (requireActivity() as MainActivity).launchDetailsFragment(film)
                }
            })

            adapter = filmsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            val decorator = TopSpacingItemDecoration(8)
            addItemDecoration(decorator)

        }

        viewModel.filmsListLiveData.observe(viewLifecycleOwner, Observer<List<Film>> {
            filmsDataBase = it
        })


        // filmsAdapter.addItems(filmsDataBase)
        searchViewLogic()

    }

    private fun searchViewLogic() {
        binding.searchView.setOnClickListener {
            binding.searchView.isIconified = false
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val result = filmsDataBase.filter{
                    it.title.toLowerCase(Locale.getDefault()).contains(newText!!?.toLowerCase(Locale.getDefault()))
                }
                filmsAdapter.addItems(result)
                return true
            }

        })
    }

}

private fun DiffUtil.DiffResult.dispatchUpdatesTo(adapter: Unit) {
}