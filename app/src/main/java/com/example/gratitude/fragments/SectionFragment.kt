package com.example.gratitude.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.gratitude.R
import com.example.gratitude.databinding.FragmentSectionBinding
import com.example.gratitude.helper.SECTIONNAME
import com.example.gratitude.helper.VISIONNAME
import com.example.gratitude.prefmanager.PrefManager


class SectionFragment : Fragment() {
    private lateinit var binding: FragmentSectionBinding
    private lateinit var prefManager: PrefManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSectionBinding.inflate(inflater,container,false)
        prefManager = PrefManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnContinue.isEnabled = false

        binding.tvName2.setOnClickListener {
            val bottomSheet = SectionBottomSheetFragment()
            bottomSheet.show(parentFragmentManager, bottomSheet.tag)
        }

        binding.btnContinue.setOnClickListener {
            prefManager.setVisionName(SECTIONNAME, binding.searchBar.text.toString())
            if (binding.btnContinue.isEnabled) {
//                parentFragmentManager.beginTransaction()
//                    .replace(R.id.main_container, SectionFragment())
//                    .commit()
            }
        }

        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No action needed here
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val isEnabled = !s.isNullOrEmpty()
                binding.btnContinue.isEnabled = true
                binding.btnContinue.setBackgroundColor(
                    ContextCompat.getColor(requireContext(),
                        if (isEnabled) R.color.purple else R.color.white)
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
            binding.chip10
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