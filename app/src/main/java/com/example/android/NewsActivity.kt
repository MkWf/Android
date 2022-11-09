package com.example.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.android.databinding.ActivityMainBinding
import com.example.android.db.ArticleDatabase
import com.example.android.repository.NewsRepository
import com.example.android.ui.NewsViewModel
import com.example.android.ui.NewsViewModelProviderFactory

class NewsActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)

        //init adapter then pass it to the recyclerview
        //binding.recyclerview.adapter = NewsAdapter()
    }
}