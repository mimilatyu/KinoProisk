package com.example.kinoproisk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.view.View
import android.view.animation.AnimationUtils
import com.example.kinoproisk.databinding.ActivityMainBinding




class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initNavigation()

        val animation = AnimationUtils.loadAnimation(this, R.anim.button_rotate)
        val animationTrans = AnimationUtils.loadAnimation(this, R.anim.trans_button)

        binding.morris.setOnClickListener{
            binding.morris.startAnimation(animation)
            Toast.makeText(this, "Я люблю тебя, Филлипп Моррис", Toast.LENGTH_SHORT).show()
        }

        binding.philadelphia.setOnClickListener {
            binding.philadelphia.startAnimation(animationTrans)
            Toast.makeText(this, "Филадельфия", Toast.LENGTH_SHORT).show()
        }

        binding.aida.setOnClickListener {
            Toast.makeText(this, "Мой личный штат Айдахо", Toast.LENGTH_SHORT).show()
        }

        binding.lonely.setOnClickListener {
            Toast.makeText(this, "Одинокий мужчина", Toast.LENGTH_SHORT).show()
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