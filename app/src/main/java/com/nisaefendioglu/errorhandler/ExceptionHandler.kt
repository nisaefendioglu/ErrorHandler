package com.nisaefendioglu.errorhandler

import android.content.Context
import android.content.Intent
import android.os.Build
import java.io.PrintWriter
import java.io.StringWriter
import kotlin.system.exitProcess

class ExceptionHandler(private val context: Context) : Thread.UncaughtExceptionHandler {

    private val LINE_SEPARATOR = System.getProperty("line.separator")

    override fun uncaughtException(thread: Thread, exception: Throwable) {
        val errorReport = StringBuilder().apply {
            append("**** App Name ****$LINE_SEPARATOR")
            append("\n***** DEVICE INFORMATION *****$LINE_SEPARATOR")
            append("Brand: ${Build.BRAND} $LINE_SEPARATOR")
            append("Device: ${Build.DEVICE} $LINE_SEPARATOR")
            append("Model: ${Build.MODEL} $LINE_SEPARATOR")
            append("\n***** FIRMWARE *****$LINE_SEPARATOR")
            append("SDK: ${Build.VERSION.SDK_INT} $LINE_SEPARATOR")
            append("Release: ${Build.VERSION.RELEASE} $LINE_SEPARATOR")
            append("\n***** CAUSE OF ERROR *****$LINE_SEPARATOR")
            append(getStackTrace(exception))
        }

        launchErrorActivity(errorReport.toString())
        exitApplication()
    }

    private fun getStackTrace(exception: Throwable): String {
        val stringWriter = StringWriter()
        exception.printStackTrace(PrintWriter(stringWriter))
        return stringWriter.toString()
    }

    private fun launchErrorActivity(error: String) {
        val intent = Intent(context, ErrorActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("error", error)
        }
        context.startActivity(intent)
    }

    private fun exitApplication() {
        android.os.Process.killProcess(android.os.Process.myPid())
        exitProcess(10)
    }
}
