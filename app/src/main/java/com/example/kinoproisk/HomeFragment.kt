package com.example.kinoproisk

import android.content.Intent
import android.os.Bundle
import android.transition.Scene
import android.transition.Slide
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kinoproisk.databinding.FragmentHomeBinding
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var filmsAdapter: FilmListRecyclerAdapter
    val filmsDataBase = listOf(
        Film("Brokeback Mountain", R.drawable.brokeback_mountain, "Ennis and Jack are two shepherds who develop a sexual and emotional relationship. Their relationship becomes complicated when both of them get married to their respective girlfriends."),
        Film("My Own Private Idaho", R.drawable.aida, "Two best friends living on the streets of Portland as hustlers embark on a journey of self discovery and find their relationship stumbling along the way."),
        Film("Pride", R.drawable.pride, "U.K. gay activists work to help miners during their lengthy strike of the National Union of Mineworkers in the summer of 1984."),
        Film("Milk", R.drawable.milk, "The story of Harvey Milk and his struggles as an American gay activist who fought for gay rights and became California's first openly gay elected official."),
        Film("A Single Man", R.drawable.lonely, "An English professor, one year after the sudden death of his boyfriend, is unable to cope with his typical days in 1960s Los Angeles."),
        Film("I Love You Phillip Morris", R.drawable.phil, "A cop turns con man once he comes out of the closet. Once imprisoned, he meets the second love of his life, whom he'll stop at nothing to be with."),
        Film("Philadelphia", R.drawable.philadel, "When a man with HIV is fired by his law firm because of his condition, he hires a homophobic small time lawyer as the only willing advocate for a wrongful dismissal suit."),
        Film("Bohemian Rhapsody", R.drawable.rapsody, "The story of the legendary British rock band Queen and lead singer Freddie Mercury, leading up to their famous performance at Live Aid (1985)."),
        Film("Tom at the Farm", R.drawable.tom, "A grieving man meets his lover's family, who were not aware of their son's sexual orientation.")
    )


    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val scene = Scene.getSceneForLayout(binding.homeFragmentRoot, R.layout.merge_home_screen_content, requireContext())
        //val searchSlide = Slide(Gravity.TOP).addTarget(binding.includeHelper.searchView)
        //val recyclerSlide = Slide(Gravity.BOTTOM).addTarget(binding.includeHelper.mainRecycler)
        //val customTransition = TransitionSet().apply {
        //    duration = 500
        //    addTransition(recyclerSlide)
        //    addTransition(searchSlide)
        //}
        //TransitionManager.go(scene, customTransition)

        //нашли RV
        binding.includeHelper.mainRecycler.apply {
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

        filmsAdapter.addItems(filmsDataBase)



        //добавил в разметку кнопку чтобы перемешать айтемы (для того, чтобы был повод реализовать DiffUtil),
        //потому что пока не очень понимаю,
        //как будут добавляться новые фильмы пользователем
        fun mixing() {
            val oldList = filmsDataBase
            val newList = filmsDataBase.shuffled()
            val filmDiff = FilmDiff(oldList, newList)
            val diffResult = DiffUtil.calculateDiff(filmDiff)
            val adapter = filmsAdapter.addItems(newList)
            diffResult.dispatchUpdatesTo(adapter)
        }

        val refresh = binding.includeHelper.refreshBut
        refresh.setOnClickListener {
            mixing()
        }
        searchViewLogic()
    }

    private fun searchViewLogic() {
        binding.includeHelper.searchView.setOnClickListener {
            binding.includeHelper.searchView.isIconified = false
        }

        binding.includeHelper.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
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