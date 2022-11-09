package com.example.android.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.repository.NewsRepository

//By default a viewmodel can't have a constructor with a parameter
//This factory takes the repository and uses a factory to create a view model with a parameter as the repository
class NewsViewModelProviderFactory(
    val newsRepository: NewsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(newsRepository) as T
    }
}