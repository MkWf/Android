package com.example.android.models

import androidx.room.Entity
import androidx.room.PrimaryKey

//Our table class
@Entity(
    tableName = "articles"
)
data class Article( //table
    @PrimaryKey(autoGenerate = true) //will autoincrement each id for us
    var id: Int? = null, //primary key, unique for each article. Set null because not every article we fetch from retrofit will be saved in the database

    val author: String,  //each parameter is a table column
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
)