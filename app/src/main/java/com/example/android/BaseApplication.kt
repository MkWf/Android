package com.example.android

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlin.text.Typography.dagger

//Will inject our dependencies for our classes at compile-time
@HiltAndroidApp
class BaseApplication: Application() {
//
}