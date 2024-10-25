package com.example.gratitude.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat.getColor
import com.example.gratitude.R
import com.example.gratitude.activities.LandingActivity
import com.example.gratitude.databinding.FragmentThoughtsBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale
import java.util.Random

class ThoughtsFragment : Fragment() {

    private lateinit var binding: FragmentThoughtsBinding
    private var lastColor: String? = null
    private var newColor: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentThoughtsBinding.inflate(inflater, container, false)
        setRandomBackgroundColor()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTodayDate()
        val activity = requireActivity() as LandingActivity
        val bottomNav = activity.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.visibility = View.GONE
        val toolbarTitle = activity.findViewById<TextView>(R.id.toolbar_title)
        toolbarTitle.text = ""
        val toolbarImage = activity.findViewById<ImageView>(R.id.profile_icon)
        toolbarImage.visibility = View.GONE
        val streakIcon = activity.findViewById<ImageView>(R.id.streak_button)
        streakIcon.visibility = View.GONE
        val challengeIcon = activity.findViewById<ImageView>(R.id.challenges_button)
        challengeIcon.visibility = View.GONE
        val toolbar = activity.findViewById<Toolbar>(R.id.toolbar)
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24)
        toolbar.setBackgroundColor(Color.parseColor(newColor))

        toolbar.setNavigationOnClickListener {
            val intent = Intent(requireActivity(), LandingActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    private fun setTodayDate() {
        val dateFormat = SimpleDateFormat("dd MMM, yyyy", Locale.getDefault())
        val today = dateFormat.format(Date())
        binding.date.text = "Today: $today"
    }

    // Function to generate a random hex color
    private fun generateRandomHexColor(): String {
        val random = Random()
        val color = String.format("#%06X", random.nextInt(0xFFFFFF + 1))
        return color
    }

    private fun setRandomBackgroundColor() {
        do {
            newColor = generateRandomHexColor()
        } while (newColor == lastColor) // It should change everytime

        lastColor = newColor
        // Set the background color
        binding.root.setBackgroundColor(Color.parseColor(newColor))
        binding.checkIcon.setBackgroundColor(Color.parseColor((newColor)))

        val window: Window = requireActivity().window
        window.statusBarColor = Color.parseColor(newColor)
    }


}