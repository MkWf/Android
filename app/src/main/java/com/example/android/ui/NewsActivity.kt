package com.example.android.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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

        //Do something when its a specific destination. THis example, hide the bottomnav
        val navHostFragment = supportFragmentManager.findFragmentById((R.id.newsNavHostFragment))
        navHostFragment!!.findNavController()
            .addOnDestinationChangedListener{_, destination, _ ->
                when(destination.id) {
                    R.id.savedNewsFragment, R.id.breakingNewsFragment -> binding.bottomNavigationView.visibility = View.VISIBLE
                }
            }

        //Add badge number to bottomnav icon
        binding.bottomNavigationView.getOrCreateBadge(R.id.savedNewsFragment).apply{
            number = 10
            isVisible = true
        }
    }
}