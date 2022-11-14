package com.example.android.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.android.repositories.MainRepository

class MainViewModel @ViewModelInject constructor(
    val mainRepository: MainRepository //can be injected without needing a provides in AppModule since a RunDAO is available to make the repository
): ViewModel() {
}