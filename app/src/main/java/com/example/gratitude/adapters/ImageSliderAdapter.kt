package com.example.gratitude.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.example.gratitude.databinding.ImageSliderItemBinding
import com.example.gratitude.models.ItemImageSlider

class ImageSliderAdapter(
    private val context: Context,
    private var imageList: List<ItemImageSlider>
) : PagerAdapter() {

    override fun getCount(): Int = imageList.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding = ImageSliderItemBinding.inflate(LayoutInflater.from(context), container, false)
        binding.imageView.setImageResource(imageList[position].image)
        binding.tvHeading.text = imageList[position].heading
        binding.SubtvHeading.text = imageList[position].subheading

        container.addView(binding.root)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}
