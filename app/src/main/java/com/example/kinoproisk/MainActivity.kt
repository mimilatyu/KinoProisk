package com.example.kinoproisk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.view.View



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickToastMenu(view: View){
        Toast.makeText(this, "Меню", Toast.LENGTH_SHORT).show()
    }

    fun onClickToastFavor(view: View){
        Toast.makeText(this, "Избранное", Toast.LENGTH_SHORT).show()
    }

    fun onClickToastLater(view: View){
        Toast.makeText(this, "Посмотреть позже", Toast.LENGTH_SHORT).show()
    }

    fun onClickToastMenuColl(view: View){
        Toast.makeText(this, "Подборки", Toast.LENGTH_SHORT).show()
    }

    fun onClickToastMenuSet(view: View){
        Toast.makeText(this, "Настройки", Toast.LENGTH_SHORT).show()
    }







}