package com.example.gratitude.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gratitude.R
import com.example.gratitude.models.JournalEntry

class JournalAdapter(private val entries : List<JournalEntry>) : RecyclerView.Adapter<JournalAdapter.JournalViewHolder>() {

    class JournalViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val journalEntryText: TextView = view.findViewById(R.id.journal_entry_text)
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): JournalAdapter.JournalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.journal_items,parent, false)
        return JournalViewHolder(view)
    }

    override fun onBindViewHolder(holder: JournalAdapter.JournalViewHolder, position: Int) {
        holder.journalEntryText.text = entries[position].content
    }

    override fun getItemCount(): Int {
        return entries.size
    }

}