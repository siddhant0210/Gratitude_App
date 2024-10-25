package com.example.gratitude.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gratitude.R
import com.example.gratitude.adapters.AffirmationAdapter
import com.example.gratitude.databinding.FragmentBottom2Binding
import com.example.gratitude.models.AffirmationData
import com.example.gratitude.prefmanager.PrefManager


class Bottom2Fragment : Fragment() {

    private lateinit var binding: FragmentBottom2Binding
    private lateinit var prefManager: PrefManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var affirmationAdapter: AffirmationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBottom2Binding.inflate(layoutInflater, container, false)
        prefManager = PrefManager(requireContext())
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        val affirmationList = listOf(
            AffirmationData(
                "https://blog.gratefulness.me/content/images/size/w1000/2024/05/jade-story-root-yourself-in-joy.jpg",
                "Affirmation 1",
                "Description 1"
            ),
            AffirmationData(
                "https://blog.gratefulness.me/content/images/size/w1000/2024/05/jade-story-root-yourself-in-joy.jpg",
                "Affirmation 2",
                "Description goes here"
            ),
            AffirmationData(
                "https://blog.gratefulness.me/content/images/size/w1000/2024/05/jade-story-root-yourself-in-joy.jpg",
                "Affirmation 3",
                "Description 3"
            ),
            AffirmationData(
                "https://blog.gratefulness.me/content/images/size/w1000/2024/05/jade-story-root-yourself-in-joy.jpg",
                "Affirmation 4",
                "Description 4"
            ),
            AffirmationData(
                "https://blog.gratefulness.me/content/images/size/w1000/2024/05/jade-story-root-yourself-in-joy.jpg",
                "Affirmation 5",
                "Description 5"
            ),
            AffirmationData(
                "https://blog.gratefulness.me/content/images/size/w1000/2024/05/jade-story-root-yourself-in-joy.jpg",
                "Affirmation 6",
                "Description 6"
            ),
            AffirmationData(
                "https://blog.gratefulness.me/content/images/size/w1000/2024/05/jade-story-root-yourself-in-joy.jpg",
                "Affirmation 7",
                "Description 7"
            ),
            AffirmationData(
                "https://blog.gratefulness.me/content/images/size/w1000/2024/05/jade-story-root-yourself-in-joy.jpg",
                "Affirmation 8",
                "Description 8"
            ),
            AffirmationData(
                "https://blog.gratefulness.me/content/images/size/w1000/2024/05/jade-story-root-yourself-in-joy.jpg",
                "Affirmation 9",
                "Description 9"
            ),
            AffirmationData(
                "https://blog.gratefulness.me/content/images/size/w1000/2024/05/jade-story-root-yourself-in-joy.jpg",
                "Affirmation 10",
                "Description 10"
            )

        )

        affirmationAdapter = AffirmationAdapter(affirmationList)
        binding.recyclerView.adapter = affirmationAdapter
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}