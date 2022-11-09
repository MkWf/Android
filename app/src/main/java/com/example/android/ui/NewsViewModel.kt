package com.example.android.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.models.NewsResponse
import com.example.android.repository.NewsRepository
import com.example.android.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    val newsRepository: NewsRepository
) : ViewModel() {

    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1 //keep track of the page in the viewmodel since data isnt lost during configuration changes

    //Use coroutine scope from viewmodel to make the call. Suspend function is in the repository
    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading()) //post to observers that were loading
        val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage) //the network response
        breakingNews.postValue(handleBreakingNewsResponse(response)) //will return success or error
    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if(response.isSuccessful){
            response.body()?.let{ resultResponse -> //check body not null
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}