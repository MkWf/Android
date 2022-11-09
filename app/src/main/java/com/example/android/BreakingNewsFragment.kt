package com.example.android

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.android.ui.NewsViewModel


class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    lateinit var viewModel: NewsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel   //get access to the viewmodel through the activity
    }
}