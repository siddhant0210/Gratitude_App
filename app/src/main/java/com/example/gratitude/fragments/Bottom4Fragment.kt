package com.example.gratitude.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gratitude.R
import com.example.gratitude.databinding.FragmentBottom4Binding


class Bottom4Fragment : Fragment() {

    private lateinit var binding : FragmentBottom4Binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottom4Binding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.arrowbtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_container, VisionBoardFragment())
                .commit()
        }
    }
}