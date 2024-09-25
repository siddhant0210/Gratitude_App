package com.example.gratitude.fragments

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import android.Manifest
import android.app.AlertDialog
import android.widget.GridLayout
import com.example.gratitude.R
import com.example.gratitude.activities.LandingActivity
import com.example.gratitude.adapters.ImageSectionAdapter
import com.example.gratitude.databinding.FragmentAddPhotosBinding
import com.example.gratitude.helper.SECTIONNAME
import com.example.gratitude.helper.VISIONNAME
import com.example.gratitude.prefmanager.PrefManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar

class AddPhotosFragment : Fragment() {

    private lateinit var binding: FragmentAddPhotosBinding
    private lateinit var prefManager: PrefManager
    private lateinit var getImage: ActivityResultLauncher<Intent>
    private val imageUris = mutableListOf<Uri>()
    private lateinit var imageAdapter: ImageSectionAdapter
    private lateinit var requestPermission: ActivityResultLauncher<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddPhotosBinding.inflate(inflater, container, false)
        prefManager = PrefManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity() as LandingActivity
        val bottomNav = activity.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.visibility = View.VISIBLE
        val toolbarTitle = activity.findViewById<TextView>(R.id.toolbar_title)
        toolbarTitle.text = ""
        val toolbarImage = activity.findViewById<ImageView>(R.id.profile_icon)
        toolbarImage.visibility = View.VISIBLE
        val streakIcon = activity.findViewById<ImageView>(R.id.streak_button)
        streakIcon.visibility = View.VISIBLE
        val challengeIcon = activity.findViewById<ImageView>(R.id.challenges_button)
        challengeIcon.visibility = View.VISIBLE
        val toolbar = activity.findViewById<Toolbar>(R.id.toolbar)
        toolbar.navigationIcon = null
        binding.tvName.text = "${prefManager.getVisionName(VISIONNAME)}"
        binding.imageDescription.text = "${prefManager.getSectionName(SECTIONNAME)}"



        binding.visionImage.setOnClickListener {
            requestStoragePermission()
        }

        requestPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openImageChooser()
            } else {
                showPermissionDeniedSnackbar()
            }
        }


//        getImage =
//            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//                if (result.resultCode == Activity.RESULT_OK) {
//                    val data = result.data
//                    data?.data?.let { uri ->
//                        imageUris.add(uri)  // Add the URI to the list
//                        imageAdapter.notifyItemInserted(imageUris.size - 1)  // Notify adapter
//                        binding.visionImage.visibility = View.GONE
//                    }
//                }
//            }

        getImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let { data ->
                    if (data.clipData != null) {
                        // Handle multiple images
                        val count = data.clipData!!.itemCount
                        for (i in 0 until count) {
                            val uri = data.clipData!!.getItemAt(i).uri
                            imageUris.add(uri)  // Add the URI to the list
                            addImageToGrid(uri)
//                            imageAdapter.notifyItemInserted(imageUris.size - 1)  // Notify adapter
                        }
                        binding.visionImage.visibility = View.GONE  // Hide the add icon
                    } else {
                        // Handle single image selection
                        data.data?.let { uri ->
                            imageUris.add(uri)  // Add the URI to the list
                            addImageToGrid(uri)
//                            imageAdapter.notifyItemInserted(imageUris.size - 1)  // Notify adapter
                            binding.visionImage.visibility = View.GONE  // Hide the add icon
                        }
                    }
                }
            }
        }



        imageAdapter = ImageSectionAdapter(imageUris)
//        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
//        binding.recyclerView.adapter = imageAdapter

    }

    private fun addImageToGrid(uri: Uri?) {
        val imageView = ImageView(requireContext()).apply {
            layoutParams = GridLayout.LayoutParams().apply {
                width = 0
                height = 300
                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
            }
            setImageURI(uri)
            scaleType = ImageView.ScaleType.CENTER_CROP

            // Optionally, you can set a margin around the image
            val layoutParams = layoutParams as GridLayout.LayoutParams
            layoutParams.setMargins(4, 4, 4, 4)
            this.layoutParams = layoutParams
        }
        binding.imageGrid.addView(imageView)
    }

    private fun openImageChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)  // Allow multiple images
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        getImage.launch(Intent.createChooser(intent, "Select Pictures"))
    }


    private fun requestStoragePermission() {
        when {
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_MEDIA_LOCATION) == PackageManager.PERMISSION_GRANTED -> {
                openImageChooser()
            }
            else -> {
                requestPermission.launch(Manifest.permission.ACCESS_MEDIA_LOCATION)  // Request the system permission dialog
            }
        }
    }


    private fun showPermissionDeniedSnackbar() {
        Snackbar.make(binding.root, "Permission to access storage is required to add images.", Snackbar.LENGTH_LONG)
            .setAction("Settings") {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.fromParts("package", requireActivity().packageName, null)
                startActivity(intent)
            }.show()
    }


}