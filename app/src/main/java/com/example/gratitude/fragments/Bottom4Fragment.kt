package com.example.gratitude.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gratitude.R
import com.example.gratitude.databinding.FragmentBottom4Binding
import com.example.gratitude.helper.ISIMAGE
import com.example.gratitude.prefmanager.PrefManager


class Bottom4Fragment : Fragment() {

    private lateinit var binding : FragmentBottom4Binding
    private lateinit var prefManager: PrefManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottom4Binding.inflate(layoutInflater, container, false)
        prefManager = PrefManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(prefManager.getIsSectionMade(ISIMAGE)){
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_container, AddPhotosFragment())
                .commit()
        }
        else {
            binding.arrowbtn.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main_container, VisionBoardFragment())
                    .commit()
            }
        }
    }
}