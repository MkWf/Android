package com.example.android.ui

import androidx.lifecycle.ViewModel
import com.example.android.repository.NewsRepository

class NewsViewModel(
    val newsRepository: NewsRepository
) : ViewModel() {
}