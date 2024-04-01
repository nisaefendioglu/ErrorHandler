package com.nisaefendioglu.errorhandler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nisaefendioglu.errorhandler.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        val result: Int? = null
        val value = result!!.toString()
        binding.txtView.text = value
    }
}