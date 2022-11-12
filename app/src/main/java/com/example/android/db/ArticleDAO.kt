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

    //Queries cant use coroutine suspend functions

    //GET ALL ARTICLES
    @Query("SELECT * FROM articles")

    //GET ALL ARTICLES AND ORDER BY A COLUMN AND SORT
    @Query("SELECT * FROM articles ORDER BY author DESC")
    @Query("SELECT * FROM articles ORDER BY author ASC")

    //ADD TOTAL VALUES IN A COLUMN
    @Query("SELECT SUM(author) FROM articles")

    //GET AVG VALUE OF A COLUMN
    @Query("SELECT AVG(author) FROM articles")
}