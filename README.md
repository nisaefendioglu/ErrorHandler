
# Error Handler

Error Handler is a utility code snippet for Android applications that allows graceful handling of uncaught exceptions.

## Screens

![Screenshot_20240402_134423](https://github.com/nisaefendioglu/ErrorHandler/assets/48391281/a0607561-c6d7-4d6e-ab5e-c3e0bff6b5e5)


## Usage

1. **Add ExceptionHandler to your project:**

   Add the `ExceptionHandler` class to your project. This class captures uncaught exceptions and displays device information along with the stack trace when an error occurs.

2. **Create ErrorActivity:**

   Create an `ErrorActivity` class in your project. This activity is responsible for displaying the error details to the user.

3. **Set Default Uncaught Exception Handler:**

   Create a custom `Application` class and set the `ExceptionHandler` as the default uncaught exception handler in the `onCreate()` method.

4. **Update AndroidManifest.xml:**

   Update your AndroidManifest.xml file to include the custom `Application` class.

## How to Use

1. Add the `ExceptionHandler` class to your project:

```kotlin
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
            append("💥 Your App Crashed 🥲$LINE_SEPARATOR")
            append("\n***** DEVICE INFORMATION *****$LINE_SEPARATOR")
            append("Brand: ${Build.BRAND} $LINE_SEPARATOR")
            append("Device: ${Build.DEVICE} $LINE_SEPARATOR")
            append("Model: ${Build.MODEL} $LINE_SEPARATOR")
            append("\n***** FIRMWARE *****$LINE_SEPARATOR")
            append("SDK: ${Build.VERSION.SDK_INT} $LINE_SEPARATOR")
            append("Release: ${Build.VERSION.RELEASE} $LINE_SEPARATOR")
            append("\n💣 CAUSE OF ERROR 💣$LINE_SEPARATOR")
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
```

2. Create an `ErrorActivity` in your project:

```kotlin
package com.nisaefendioglu.errorhandler

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nisaefendioglu.errorhandler.databinding.ActivityErrorBinding
import kotlin.system.exitProcess

class ErrorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityErrorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityErrorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val errorText = intent.getStringExtra("error")
        binding.errorTextView.text = errorText

        binding.closeButton.setOnClickListener {
            exitProcess(0)
        }
    }
}
```

3. Create a custom `Application` class and set the `ExceptionHandler` as the default uncaught exception handler:

```kotlin
package com.nisaefendioglu.errorhandler

import android.app.Application

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Thread.setDefaultUncaughtExceptionHandler(ExceptionHandler(applicationContext))
    }
}
```

4. Update your AndroidManifest.xml to include the `MyApplication` class:

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <application
        android:name=".MyApplication"
        android:allowBackup="true"
        android:dataExtractionRules="@xml/data_extraction_rules"
        android:fullBackupContent="@xml/backup_rules"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.ErrorHandler"
        tools:targetApi="31">
        <activity
            android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <activity android:name=".ErrorActivity"/>
    </application>

</manifest>
```

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
