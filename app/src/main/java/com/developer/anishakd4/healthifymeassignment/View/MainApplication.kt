package com.developer.anishakd4.healthifymeassignment.View

import android.app.Application
import net.danlew.android.joda.JodaTimeAndroid

class MainApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        JodaTimeAndroid.init(this);
    }
}