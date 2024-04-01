package com.nisaefendioglu.errorhandler
import android.app.Application

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Thread.setDefaultUncaughtExceptionHandler(ExceptionHandler(applicationContext))
    }
}
