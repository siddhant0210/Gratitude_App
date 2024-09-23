package com.example.gratitude.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.example.gratitude.R
import com.example.gratitude.activities.LandingActivity
import com.example.gratitude.databinding.FragmentThoughtsBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class ThoughtsFragment : Fragment() {

    private lateinit var binding: FragmentThoughtsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentThoughtsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        toolbar.setBackgroundColor(resources.getColor(R.color.blue))

        toolbar.setNavigationOnClickListener {
            val intent = Intent(requireActivity(), LandingActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            requireActivity().finish()
        }
    }

}