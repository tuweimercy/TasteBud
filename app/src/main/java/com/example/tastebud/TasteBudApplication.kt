package com.example.tastebud

import android.app.Application
import com.cloudinary.android.MediaManager

class TasteBudApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val config = mapOf(
            "cloud_name" to "divls8aj2"
        )

        MediaManager.init(this, config)
    }
}