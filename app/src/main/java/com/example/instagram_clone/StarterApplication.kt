package com.example.instagram_clone

import android.app.Application
import com.parse.Parse
import com.parse.ParseACL

class StarterApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Parse.enableLocalDatastore(this)


        // Add your initialization code here
        Parse.initialize(
            Parse.Configuration.Builder(applicationContext)
                .applicationId("myappID")
                .clientKey("!Y=:urEJOEF4")
                .server("http://3.110.195.12:80/parse/")
                .build()
        )

        val defaultACL = ParseACL()
        defaultACL.publicReadAccess = true
        defaultACL.publicWriteAccess = true
        ParseACL.setDefaultACL(defaultACL, true)
    }
}