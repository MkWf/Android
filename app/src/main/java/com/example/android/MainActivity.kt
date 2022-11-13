package com.example.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.android.db.RunDAO
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint //required when we inject inside of any Android component: fragment, activity, service, etc...
class MainActivity : AppCompatActivity() {

    @Inject //will look in our AppModule and see if theres any functions that creates RunDAO. Then itll see that we need a database to create the RUNDao object and create that as well
    lateinit var runDAO: RunDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("runDao", "RUNDAO: ${runDAO.hashCode()}") //to test that it worked
    }
}