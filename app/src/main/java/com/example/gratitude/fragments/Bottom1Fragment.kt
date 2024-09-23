package com.example.gratitude.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gratitude.R
import com.example.gratitude.ViewModels.SharedViewModel
import com.example.gratitude.adapters.JournalAdapter
import com.example.gratitude.databinding.FragmentBottom1Binding
import com.example.gratitude.helper.USERNAME
import com.example.gratitude.models.JournalEntry
import com.example.gratitude.prefmanager.PrefManager

class Bottom1Fragment : Fragment() {

    private lateinit var binding: FragmentBottom1Binding
    private lateinit var prefManager: PrefManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: SharedViewModel
    private lateinit var journalAdapter: JournalAdapter
    private val journalEntries = mutableListOf<JournalEntry>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottom1Binding.inflate(layoutInflater, container, false)
        prefManager = PrefManager(requireContext())
        viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.journal.text = "${prefManager.getUserName(USERNAME)}'s Journal "
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        journalAdapter = JournalAdapter(journalEntries)
        binding.recyclerView.adapter = journalAdapter

        addSampleData()
        binding.extendedFab.setOnClickListener {
            viewModel.fabClicked.value = true
        }
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    binding.extendedFab.shrink()
                } else if (dy < 0) {
                    binding.extendedFab.extend()
                }
            }
        })
    }

    private fun addSampleData() {
        journalEntries.add(
            JournalEntry(
                1,
                "Today I am grateful for my family.Today I am grateful for my family.Today I am grateful for my family.Today I am grateful for my family.Today I am grateful for my family.Today I am grateful for my family.Today I am grateful for my family. "
            )
        )
        journalEntries.add(JournalEntry(2, "I enjoyed a beautiful sunset."))
        journalEntries.add(JournalEntry(3, "I learned something new today."))
        journalEntries.add(JournalEntry(4, "I had a great conversation with a friend."))
        journalEntries.add(JournalEntry(5, "I completed a challenging project at work."))

        // Notify the adapter about the new data
        journalAdapter.notifyDataSetChanged()
    }
}