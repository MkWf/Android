package com.example.android

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.adapters.NewsAdapter
import com.example.android.databinding.FragmentSearchNewsBinding
import com.example.android.ui.NewsViewModel
import com.example.android.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment : Fragment(R.layout.fragment_search_news){
    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    lateinit var binding: FragmentSearchNewsBinding
    val TAG = "SearchNewsFragment"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSearchNewsBinding.inflate(inflater)
        return binding.root  //return the layout to be displayed
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel   //get access to the viewmodel through the activity
        setupRecyclerView()

        //Execute delayed search while typing with coroutines
        var job: Job? = null
        binding.etSearch.addTextChangedListener { editable ->
            job?.cancel() //any time we start typing, cancel the job and start a new job
            job = MainScope().launch {
                delay(500L) //delay the search for 500ms between keystrokes
                editable?.let { //if editable not null
                    if(editable.toString().isNotEmpty()){ //and not empty
                        viewModel.searchNews(editable.toString()) //search
                    }
                }
            }
        }

        //Observe the responses
        viewModel.searchNews.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let{ newsResponse ->    //data not null
                        newsAdapter.differ.submitList(newsResponse.articles) //pass our data for the adapter differ
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG, "An error occured: $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun setupRecyclerView(){
        newsAdapter = NewsAdapter()
        binding.rvSearchNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun hideProgressBar(){
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar(){
        binding.paginationProgressBar.visibility = View.VISIBLE
    }
}