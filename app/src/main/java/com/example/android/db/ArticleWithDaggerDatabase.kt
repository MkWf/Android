package com.example.android.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.android.models.Article

@Database(
    entities = [Article::class], //pass in an array of entity classes, we only have 1
    version = 1 //when we make db changes, we change this number to let room know to migrate our old db to the new db
)
@TypeConverters(Converters::class) //so room knows to use this converter for Source objects
abstract class ArticleWithDaggerDatabase : RoomDatabase(){  //All room dbs need to be abstract
    abstract fun getArticleDao(): ArticleDAO //implementation will happen behind the scenes by room

    //NO LONGER NEEDED WHEN USING DAGGER COMPARED TO ARTICLE DATABASE
    /*
    companion object { //to create our database
        @Volatile //other threads can see when another thread changes this instance
        private var instance: ArticleDatabase? = null
        private val LOCK = Any() //to make sure only a single instance of this database

        operator fun invoke(content: Context) = instance ?: synchronized(LOCK) {  //called when we instantiate the database
            instance ?: createDatabase(content).also {instance = it} //after we lock, make sure it's still null, then create
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ArticleDatabase::class.java,  //our database
                "article_db.db"  //create database name
            ).build()
    }*/
}