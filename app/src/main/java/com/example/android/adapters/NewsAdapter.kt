package com.example.android.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.databinding.ItemArticlePreviewBinding
import com.example.android.models.Article
import com.example.android.models.Source

class NewsAdapter() : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    //Use diffutil for adapter updates instead of notifydatasetchanged. Will see the difference between
    //the two lists and only update the difference.
    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url //can be any means of comparison, but check if they're the same
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem  //check if the same object
        }
    }

    //DifferUtil takes adapter and the callback
    val differ = AsyncListDiffer(this, differCallback)

    inner class ArticleViewHolder(val binding: ItemArticlePreviewBinding): RecyclerView.ViewHolder(binding.root) //binding the layout

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(   //inflate the item layout
            ItemArticlePreviewBinding.inflate(
                LayoutInflater.from(parent.context),
                    parent,
                false))
    }

    //Bind the view to our article item
    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position] //get article from current position
        holder.binding.apply{
            Glide.with(holder.itemView).load(article.urlToImage).into(ivArticleImage)  //bind all the views in the layout
            tvSource.text = article.source?.name
            tvTitle.text = article.title
            tvDescription.text = article.description
            tvPublishedAt.text = article.publishedAt
            holder.itemView.setOnClickListener { //set an onclicklistener to the view itself
                onItemClickListener?.let { it(article) }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    //A listener that takes an Article as a param and returns nothing, and nullable. Set to null by default
    //Will hold onto the listener that gets called in the fragment
    private var onItemClickListener: ((Article) -> Unit)? = null

    //Pass a listener that takes an Article as a parameter and returns nothing....above
    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }
}