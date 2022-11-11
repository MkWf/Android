package com.example.android.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.NewsActivity
import com.example.android.R
import com.example.android.adapters.NewsAdapter
import com.example.android.databinding.FragmentBreakingNewsBinding
import com.example.android.ui.NewsViewModel
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
        binding.rvBreakingNews.apply {
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