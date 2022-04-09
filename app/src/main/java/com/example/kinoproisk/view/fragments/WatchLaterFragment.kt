package com.example.kinoproisk.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kinoproisk.data.Entity.Film
import com.example.kinoproisk.databinding.FragmentWatchLaterBinding
import com.example.kinoproisk.utils.AnimationHelper
import com.example.kinoproisk.utils.AutoDisposable
import com.example.kinoproisk.utils.addTo
import com.example.kinoproisk.view.MainActivity
import com.example.kinoproisk.view.rv_adapters.TopSpacingItemDecoration
import com.example.kinoproisk.view.rv_adapters.WatchLaterAdapter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers


class WatchLaterFragment : Fragment() {

    private lateinit var binding: FragmentWatchLaterBinding
    private lateinit var filmsAdapter: WatchLaterAdapter
    private val autoDisposable = AutoDisposable()







    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentWatchLaterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AnimationHelper.performFragmentCircularRevealAnimation(binding.watchLaterFragmentRoot, requireActivity(), 3)

        initRecycler()

    }


    private fun initRecycler() {
        binding.watchLaterRecycler.apply {
            filmsAdapter =
                WatchLaterAdapter(object : WatchLaterAdapter.OnItemClickListener {
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