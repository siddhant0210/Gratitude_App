package com.example.gratitude.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.gratitude.R
import com.example.gratitude.databinding.ActivityOnBoardingBinding
import com.example.gratitude.helper.LOGIN
import com.example.gratitude.helper.USERNAME
import com.example.gratitude.prefmanager.PrefManager

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding
    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        prefManager = PrefManager(this)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnContinue.isEnabled = false
        binding.btnContinue.setOnClickListener {
            val name = binding.etUsername.text.toString()
            prefManager.setUserName(USERNAME, name)
            val intent = Intent(this, LandingActivity::class.java)
            if (binding.btnContinue.isEnabled) {
                startActivity(intent)
                finish()
            }
            prefManager.setIsLoginComplete(LOGIN, true)
        }

        binding.btnBack.setOnClickListener {
            val intent = Intent(this, BaseActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.etUsername.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No action needed here
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.btnContinue.isEnabled = !s.isNullOrEmpty()

                binding.btnContinue.isEnabled = true
                binding.btnContinue.setBackgroundColor(
                    if (binding.btnContinue.isEnabled) {
                        getColor(R.color.purple)
                    } else {
                        getColor(R.color.white)
                    }
                )
            }

            override fun afterTextChanged(s: Editable?) {
                // No action needed here
            }
        })
    }
}
