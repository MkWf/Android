package com.example.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.android.adapters.NewsAdapter
import com.example.android.databinding.ActivityMainBinding
import com.example.android.db.ArticleDatabase
import com.example.android.repository.NewsRepository
import com.example.android.ui.NewsViewModel
import com.example.android.ui.NewsViewModelProviderFactory
import com.example.android.ui.fragments.BreakingNewsFragment
import com.example.android.ui.fragments.SavedNewsFragment
import com.example.android.ui.fragments.SearchNewsFragment

class NewsActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //display the fragment in the framelayout
        supportFragmentManager.beginTransaction().replace(R.id.fl_fragment_container, BreakingNewsFragment()).commit()

        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)
    }
}