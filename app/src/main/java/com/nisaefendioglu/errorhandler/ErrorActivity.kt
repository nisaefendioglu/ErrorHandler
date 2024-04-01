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
