package com.example.kinoproisk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kinoproisk.databinding.ActivityMainBinding




class MainActivity : AppCompatActivity() {

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

    private lateinit var binding: ActivityMainBinding
    private lateinit var filmsAdapter: FilmListRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initNavigation()

        binding.mainRecycler.apply {
            filmsAdapter = FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener{
                override fun click(film: Film) {
                    val bundle = Bundle()
                    bundle.putParcelable("film", film)
                    val intent = Intent(this@MainActivity, DetailsActivity::class.java)
                    intent.putExtras(bundle)
                    startActivity(intent)
                }
            })

            adapter = filmsAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            val decorator = TopSpacingItemDecoration(8)
            addItemDecoration(decorator)

            filmsAdapter.addItems(filmsDataBase)
        }

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

        val refresh = binding.refreshBut
        refresh.setOnClickListener {
            mixing()
        }
    }

    private fun initNavigation(){
        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.settings -> {
                    Toast.makeText(this, "Настройки", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.favorites -> {
                    Toast.makeText(this, "Избранное", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.watch_later -> {
                    Toast.makeText(this, "Посмотреть позже", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.selections -> {
                    Toast.makeText(this, "Подборки", Toast.LENGTH_SHORT).show()
                    true
                }

                else -> false
            }
        }
    }
}

private fun DiffUtil.DiffResult.dispatchUpdatesTo(adapter: Unit) {
}


