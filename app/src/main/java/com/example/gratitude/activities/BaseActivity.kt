package com.example.gratitude.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gratitude.R
import com.example.gratitude.adapters.ImageSliderAdapter
import com.example.gratitude.databinding.ActivityBaseBinding
import com.example.gratitude.helper.LOGIN
import com.example.gratitude.models.ItemImageSlider
import com.example.gratitude.prefmanager.PrefManager

class BaseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBaseBinding
    private lateinit var imageSliderAdapter: ImageSliderAdapter
    private var currentPage = 0
    private lateinit var prefManager: PrefManager
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private val imageList = listOf(
        ItemImageSlider("Welcome to Gratitude","The Mindfulness and Self-Care Tool of INDIA",R.drawable.grati1),
        ItemImageSlider("Build a Grateful mindset","With Guided Prompt and Challenges",R.drawable.grati2),
        ItemImageSlider("Visualize Your Goals","With Powerful Vision Board",R.drawable.grati6),
        ItemImageSlider("Stay inspired", "Using Motivating Quotes", R.drawable.grati5)
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefManager = PrefManager(this)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkForLogin()
        imageSliderAdapter = ImageSliderAdapter(this,imageList)
        binding.viewPager.adapter = imageSliderAdapter

        setUpAutoScroll()

        binding.btnLetsBegin.setOnClickListener{
            val intent = Intent(this, OnBoardingActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun checkForLogin() {
        if(prefManager.getIsLoginComplete(LOGIN)){
            val intent = Intent(this, LandingActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setUpAutoScroll() {
        handler = Handler()
        runnable = object :Runnable{
            override fun run() {
                if(currentPage == imageList.size){
                    currentPage = 0;
                }
                binding.viewPager.setCurrentItem(currentPage++,true)
                handler.postDelayed(this, 4000)
            }
        }
        handler.postDelayed(runnable, 4000)
    }

}