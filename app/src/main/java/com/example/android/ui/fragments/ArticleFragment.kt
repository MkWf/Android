package com.example.android.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.example.android.NewsActivity
import com.example.android.R
import com.example.android.databinding.FragmentArticleBinding
import com.example.android.ui.NewsViewModel

class ArticleFragment : Fragment(R.layout.fragment_article) {

    lateinit var viewModel: NewsViewModel
    lateinit var binding: FragmentArticleBinding
    val args: ArticleFragmentArgs by navArgs()  //ArticleFragmentArgs generated for us. getting the data from another argument

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentArticleBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel

        val article = args.article  //get current article passed to us
        binding.webView.apply {
            webViewClient = WebViewClient()
            if(article.url != null){ loadUrl(article.url)} //display the article in the webview instead of phone browser
        }

        binding.fab.setOnClickListener {
            if(article.author == null){ //added null check for author because alot of articles have null authors and causes NON NULL CONSTRAINT crash. but saving and seeing the articles in saved fragments works
                article.author = "123"
            }
            viewModel.saveArticle(article)
        }
    }
}