package com.example.gratitude.fragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.example.gratitude.R
import com.example.gratitude.activities.LandingActivity
import com.example.gratitude.databinding.FragmentThoughtsBinding
import com.example.gratitude.databinding.FragmentVisionBoardBinding
import com.example.gratitude.helper.SELECTEDTAB
import com.example.gratitude.helper.USERNAME
import com.example.gratitude.helper.VISIONNAME
import com.example.gratitude.prefmanager.PrefManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.chip.Chip

class VisionBoardFragment : Fragment() {

    private lateinit var binding: FragmentVisionBoardBinding
    private lateinit var prefManager: PrefManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentVisionBoardBinding.inflate(inflater, container, false)
        prefManager = PrefManager(requireContext())
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

        toolbar.setNavigationOnClickListener {
            val intent = Intent(requireContext(), LandingActivity::class.java)
            intent.putExtra("selected-tab", 3)
            startActivity(intent)
            requireActivity().finish()
        }

        binding.btnContinue.isEnabled = false

        binding.btnContinue.setOnClickListener {
            prefManager.setVisionName(VISIONNAME, binding.searchBar.text.toString())
            if (binding.btnContinue.isEnabled) {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main_container, SectionFragment())
                    .commit()
            }
        }

        binding.tvName.text = "Hello ${prefManager.getUserName(USERNAME)}"


        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No action needed here
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val isEnabled = !s.isNullOrEmpty()
                binding.btnContinue.isEnabled = isEnabled
                binding.btnContinue.setBackgroundColor(
                    ContextCompat.getColor(requireContext(),
                        if (isEnabled) R.color.yellow else R.color.white)
                )
            }

            override fun afterTextChanged(s: Editable?) {
                // No action needed here
            }
        })


        //   binding.chipgroup.setOnCheckedChangeListener { group, checkedId ->
        //                    val chip = group.findViewById<Chip>(checkedId)
        //                    chip?.let {
        //                        updateSearchBar(it.text.toString())
        //                    }
        //                }

        val chips = listOf(
            binding.chip1,
            binding.chip2,
            binding.chip3,
            binding.chip4,
            binding.chip5,
            binding.chip6,
            binding.chip7,
            binding.chip8,
            binding.chip9,
            binding.chip10,
            binding.chip11,
            binding.chip12
        )

        for (chip in chips) {
            chip.setOnClickListener {
                updateSearchBar(chip.text.toString())
            }
        }
    }



    private fun updateSearchBar(text: String) {
        binding.searchBar.setText(text)
    }
}