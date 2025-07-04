package com.example.mymovies.presentation

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.mymovies.R

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val text = findViewById<TextView>(R.id.textView)
        var s = ""
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mainViewModel.movieList.observe(this){
            Log.d("MainActivity", "Result\n $it")

            it.forEach { movie -> s += "${movie.name}\n" }
            text.text = s
        }

        val btn = findViewById<Button>(R.id.request_btn)


        btn.setOnClickListener {
            mainViewModel.loadMovies()

        }
    }
}