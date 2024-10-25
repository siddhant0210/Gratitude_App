package com.example.gratitude.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.gifdecoder.GifHeader
import com.example.gratitude.R
import com.example.gratitude.adapters.JournalAdapter.JournalViewHolder
import com.example.gratitude.models.AffirmationData

class AffirmationAdapter(private val items : List<AffirmationData>) : RecyclerView.Adapter<AffirmationAdapter.AffirmationViewHolder>() {

    class AffirmationViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val image: ImageView = view.findViewById(R.id.item_image)
        val imgText : TextView = view.findViewById(R.id.item_text)
        val imgdesc : TextView = view.findViewById(R.id.item_text_desc)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AffirmationAdapter.AffirmationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_layout,parent, false)
        return AffirmationViewHolder(view)
    }

    override fun onBindViewHolder(holder: AffirmationAdapter.AffirmationViewHolder, position: Int) {
        val item = items[position]

        Glide.with(holder.itemView.context)
            .load(item.image)
            .into(holder.image)

        holder.imgText.text = item.imgText
        holder.imgdesc.text = item.imgDesc
    }

    override fun getItemCount(): Int {
        return items.size
    }
}