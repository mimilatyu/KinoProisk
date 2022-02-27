package com.example.kinoproisk.view.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kinoproisk.databinding.FragmentHomeBinding
import com.example.kinoproisk.data.Entity.Film
import com.example.kinoproisk.utils.AnimationHelper
import com.example.kinoproisk.view.MainActivity
import com.example.kinoproisk.view.rv_adapters.FilmListRecyclerAdapter
import com.example.kinoproisk.view.rv_adapters.TopSpacingItemDecoration
import com.example.kinoproisk.viewmodel.HomeFragmentViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import okhttp3.Dispatcher

import java.util.*

class HomeFragment : Fragment() {
    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(HomeFragmentViewModel::class.java)
    }
    private lateinit var filmsAdapter: FilmListRecyclerAdapter
    private lateinit var binding: FragmentHomeBinding
    private lateinit var scope: CoroutineScope
    private var filmsDataBase = listOf<Film>()
        set(value) {
            if (field == value) return
            field = value
            filmsAdapter.addItems(field)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AnimationHelper.performFragmentCircularRevealAnimation(binding.homeFragmentRoot, requireActivity(), 1)

        initSearchView()
        initPullToRefresh()
        initRecycler()

        scope = CoroutineScope(Dispatchers.IO).also { scope ->
            scope.launch {
                viewModel.filmsListData.collect {
                    withContext(Dispatchers.Main) {
                        filmsAdapter.addItems(it)
                        filmsDataBase = it
                    }
                }
            }

            scope.launch {
                for (element in viewModel.showProgressBar) {
                    launch(Dispatchers.Main) {
                        binding.progressBar.isVisible = element
                    }
                }
            }
        }

    }

    override fun onStop() {
        super.onStop()
        scope.cancel()
    }

    private fun initPullToRefresh() {
        binding.pullToRefresh.setOnRefreshListener {

            filmsAdapter.items.clear()
            viewModel.getFilms()
            binding.pullToRefresh.isRefreshing = false
        }
    }

    private fun initSearchView() {
        binding.searchView.setOnClickListener {
            binding.searchView.isIconified = false
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    filmsAdapter.addItems(filmsDataBase)
                    return true
                }
                val result = filmsDataBase.filter {
                    it.title.toLowerCase(Locale.getDefault())
                        .contains(newText.toLowerCase(Locale.getDefault()))
                }
                filmsAdapter.addItems(result)
                return true
            }
        })
    }

    private fun initRecycler() {
        binding.mainRecycler.apply {
            filmsAdapter =
                FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener {
                    override fun click(film: Film) {
                        (requireActivity() as MainActivity).launchDetailsFragment(film)
                    }
                })

            adapter = filmsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            val decorator = TopSpacingItemDecoration(8)
            addItemDecoration(decorator)
        }
    }





}

private fun Any.setOnQueryTextListener(onQueryTextListener: SearchView.OnQueryTextListener) {

}


