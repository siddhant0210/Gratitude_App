package com.example.gratitude.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gratitude.R
import com.example.gratitude.adapters.ImageSectionAdapter
import com.example.gratitude.databinding.FragmentImageGalleryBinding

class ImageGalleryFragment : Fragment() {

    private var _binding: FragmentImageGalleryBinding? = null
    private val binding get() = _binding!!

    private lateinit var imageAdapter: ImageSectionAdapter
    private lateinit var imageUris: List<Uri>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentImageGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageUris = arguments?.getParcelableArrayList("imageUris") ?: emptyList()
        imageAdapter = ImageSectionAdapter(imageUris)
        binding.imageView.layoutManager = LinearLayoutManager(requireContext())
        binding.imageView.adapter = imageAdapter
    }


}