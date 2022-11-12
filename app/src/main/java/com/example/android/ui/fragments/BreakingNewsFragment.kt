package com.example.android.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.NewsActivity
import com.example.android.R
import com.example.android.adapters.NewsAdapter
import com.example.android.databinding.FragmentBreakingNewsBinding
import com.example.android.ui.NewsViewModel
import com.example.android.util.Constants.Companion.QUERY_PAGE_SIZE
import com.example.android.util.Resource


class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    lateinit var binding: FragmentBreakingNewsBinding

    val TAG = "BreakingNewsFragment"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentBreakingNewsBinding.inflate(inflater)
        return binding.root  //return the layout to be displayed 
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel   //get access to the viewmodel through the activity
        setupRecyclerView()

        //Set on item click listener to make the fragment change
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it) //article is the argument name we set in navgraph
            }

            //TWO APPROACHES: WITH A BOTTOM NAV OR WITHOUT
            //1: WITHOUT BOTTOM NAV
            var articleFragment = ArticleFragment() //create the fragment
            articleFragment.arguments = bundle //pass the Article object bundle
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fl_fragment_container, articleFragment).commit()

            //2: WITH BOTTOM NAV FIND NAVCONTROLLER ONLY FOR USING BOTTOM NAVIGATION
            /*findNavController().navigate(R.id.action_breakingNewsFragment_to_articleFragment,
                bundle) //use the id of the action we want to execute from navgraph */
        }

        //Observe the responses
        viewModel.breakingNews.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let{ newsResponse ->    //data not null
                        newsAdapter.differ.submitList(newsResponse.articles.toList()) //pass our data for the adapter differ. NEED TO TURN MUTABLE LIST TO LIST. DIFFER CANT USE MUTABLE LIST
                        val totalPages = newsResponse.totalResults / QUERY_PAGE_SIZE + 2 //We need to see if we're at the last page or not by checking if the total results returned is the same as the query size or not. If it's the same then odds are that there's more articles to be returned unless it just so happened to exactly be the last 20 articles and there's none left. We add +2 because integer divison gets rounded off, so thats why we have to add 1 to that, and the last page of our response will always be empty so we have to add 1 more to that
                        isLastPage = viewModel.breakingNewsPage == totalPages //then we're at our last page
                        if(isLastPage){
                            binding.rvBreakingNews.setPadding(0,0,0,0) //need to reset the padding so that the progressbar has its own space and doesnt overlap the recyclerview
                        }
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

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener = object : RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }

        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            //For example with 20 items. If the first visible item in the list is 17 and theres 3 visible items, then we know the last item is visible
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0 //to make sure scrolling has begun at all
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE   //each query is 20 items, if our total is not more, then there's no more data to fetch
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning && isTotalMoreThanVisible && isScrolling

            if(shouldPaginate){
                viewModel.getBreakingNews("us")
                isScrolling = false
            }

        }
    }

    private fun setupRecyclerView(){
        newsAdapter = NewsAdapter()
        binding.rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@BreakingNewsFragment.scrollListener) //because recyclerView already has a variable called scrollListener, we wouldn't need to do this if we rename our scrollListener to something else
        }
    }

    private fun hideProgressBar(){
        binding.paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar(){
        binding.paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }
}