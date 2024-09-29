package com.example.gratitude.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gratitude.R

class ImageSectionAdapter(private val images: MutableList<Uri>) : RecyclerView.Adapter<ImageSectionAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageSectionAdapter.ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_items, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageSectionAdapter.ImageViewHolder, position: Int) {
        holder.imageView.setImageURI(images[position])
    }

    override fun getItemCount(): Int {
        return images.size
    }
    fun updateImageUris(newImages: List<Uri>) {
        images.addAll(newImages) // Add the new images
        notifyDataSetChanged() // Notify the adapter to refresh the RecyclerView
    }
}