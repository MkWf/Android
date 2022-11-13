package com.example.android.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.android.db.RunningDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) //our dependencies will be created and destroyed inside of our application during onCreate() and onDestroy()
object AppModule {

    @Singleton //only want one instance. if we didn't use this annotation, every class that needed this dependency would create a new instance
    @Provides  //provides a database
    fun provideRunningDatabase(
        @ApplicationContext app: Context  //tells Dagger to use our app context
    ) = Room.databaseBuilder(
        app,
        RunningDatabase::class.java,
        "running.db"
    )

    @Singleton
    @Provides //dagger will know how to make our database so that we can get a dao
    fun provideRunDao(db: RunningDatabase) = db.getRunDao()
}
//@InstallIn(SingletonComponent::class)  //will be available throughout the lifecylcle of our application
//@InstallIn(ActivityComponent:class) //will only be available during the lifecycle of our activity
//ServiceComponent
//FragmentComponent
//etc..