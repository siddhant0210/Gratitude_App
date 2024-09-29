package com.example.gratitude.activities

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.gratitude.R
import com.example.gratitude.ViewModels.SharedViewModel
import com.example.gratitude.databinding.ActivityLandingBinding
import com.example.gratitude.fragments.Bottom1Fragment
import com.example.gratitude.fragments.Bottom2Fragment
import com.example.gratitude.fragments.Bottom3Fragment
import com.example.gratitude.fragments.Bottom4Fragment
import com.example.gratitude.fragments.ChallengesFragment
import com.example.gratitude.fragments.NavFragment
import com.example.gratitude.fragments.StreakFragment
import com.example.gratitude.fragments.ThoughtsFragment
import com.example.gratitude.helper.SELECTEDTAB
import com.example.gratitude.helper.USERNAME
import com.example.gratitude.prefmanager.PrefManager

class LandingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLandingBinding
    private lateinit var prefManager: PrefManager
    private lateinit var viewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingBinding.inflate(layoutInflater)
        prefManager = PrefManager(this)
        viewModel = ViewModelProvider(this)[SharedViewModel::class.java]
        setContentView(binding.root)



        // Set status bar color to transparent
        window.statusBarColor = getColor(R.color.yellow)

//        binding.toolbarTitle.text = "Welcome " + prefManager.getUserName(USERNAME)
        binding.toolbarTitle.visibility = View.VISIBLE


        val selectedTab = intent.getIntExtra("selected-tab", 0)
        binding.bottomNavigation.selectedItemId = when (selectedTab) {
            0 -> R.id.nav_item1
            1 -> R.id.nav_item2
            2 -> R.id.nav_item3
            3 -> R.id.nav_item4
            else -> R.id.nav_item1
        }

        if (savedInstanceState == null) {
            replaceFrag(Bottom1Fragment())
            binding.bottomNavigation.selectedItemId = R.id.nav_item1 // Select first item in bottom navigation
        }

        viewModel.fabClicked.observe(this, Observer { clicked ->
            if(clicked){
                showFragment(ThoughtsFragment())
            }
        })

        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_item1 -> {
                    replaceFrag(Bottom1Fragment())
                    true
                }

                R.id.nav_item2 -> {
                    replaceFrag(Bottom2Fragment())
                    true
                }

                R.id.nav_item3 -> {
                    replaceFrag(Bottom3Fragment())
                    true
                }

                R.id.nav_item4 -> {
                    replaceFrag(Bottom4Fragment())
                    true
                }

                else -> false
            }
        }

        binding.profileIcon.setOnClickListener {
            showFragment(NavFragment()) // Show settings fragment
        }

        binding.challengesButton.setOnClickListener {
            showFragment(ChallengesFragment())
        }

        binding.streakButton.setOnClickListener {
            showFragment(StreakFragment())
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
            .replace(R.id.main_container, fragment)
            .commit()
    }

    private fun replaceFrag(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment).commit()
    }
}

