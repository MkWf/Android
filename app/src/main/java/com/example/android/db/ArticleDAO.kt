package com.example.android.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.models.Article

@Dao
interface ArticleDAO { //Defines the functions to interact with the database

    //Insert/update with @Insert
    @Insert(onConflict = OnConflictStrategy.REPLACE) //If duplicate article is found, replace it
    suspend fun upsert(article: Article): Long //returns a Long which is the id

    @Query("SELECT * FROM articles") //tablename in Article class
    fun getAllArticles(): LiveData<List<Article>>    //not suspend fun. will return a LiveData which doesn't work with suspend functions

    @Delete
    suspend fun deleteArticle(article: Article)  //delete an article

}