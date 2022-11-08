package com.example.android.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.android.R
import com.example.android.databinding.ActivityNewsBinding

class NewsActivity : AppCompatActivity() {

    lateinit var binding: ActivityNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Init view binding
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Get the fragment from the activity layout
        binding.bottomNavigationView.setupWithNavController(supportFragmentManager.findFragmentById(R.id.newsNavHostFragment)!!.findNavController())

        //Get the titles from the fragments in the menu and update the toolbar with the name when the fragment is changed
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.breakingNewsFragment, R.id.savedNewsFragment, R.id.searchNewsFragment))
        setupActionBarWithNavController(supportFragmentManager.findFragmentById(R.id.newsNavHostFragment)!!.findNavController(), appBarConfiguration)
    }
}