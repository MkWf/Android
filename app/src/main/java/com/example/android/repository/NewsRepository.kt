package com.example.android.repository

import com.example.android.api.RetrofitInstance
import com.example.android.db.ArticleDatabase
import com.example.android.models.Article

class NewsRepository(
    val db: ArticleDatabase
) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchForNews(searchQuery, pageNumber)

    suspend fun upsert(article: Article) = db.getArticleDao().upsert(article)

    fun getSavedNews() = db.getArticleDao().getAllArticles() //not suspend because it returns LiveData and room does it for us in a separate thread anyway

    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)

}

