package com.example.android.di

import android.app.Application
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class) //our dependencies will be created and destroyed inside of our application during onCreate() and onDestroy()
object AppModule {

    
}
//@InstallIn(SingletonComponent::class)  //will be available throughout the lifecylcle of our application
//@InstallIn(ActivityComponent:class) //will only be available during the lifecycle of our activity
//ServiceComponent
//FragmentComponent
//etc..