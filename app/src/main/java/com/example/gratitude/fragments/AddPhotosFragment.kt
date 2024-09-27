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
import android.widget.PopupMenu
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
    private lateinit var imageResultLauncher: ActivityResultLauncher<Intent>
    private val imageUris = mutableListOf<Uri>()
    private lateinit var imageAdapter: ImageSectionAdapter
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddPhotosBinding.inflate(inflater, container, false)
        prefManager = PrefManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupFabClickListener()
        setupImageClickListener()
        setupPermissionRequestLauncher()
        setupImageResultLauncher()
        initializeImageAdapter()


        // Set up the popup menu button
        binding.overflowButton.setOnClickListener { showPopupMenu(it) }
    }

    private fun openImageGalleryFragment() {
        val imageGalleryFragment = ImageGalleryFragment().apply {
            arguments = Bundle().apply {
                putParcelableArrayList("imageUris", ArrayList(imageUris))
            }
        }
        parentFragmentManager.beginTransaction()
            .replace(R.id.main_container, imageGalleryFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setupToolbar() {
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

        binding.tvName.text = prefManager.getVisionName(VISIONNAME)
        binding.imageDescription.text = prefManager.getSectionName(SECTIONNAME)
    }

    private fun setupFabClickListener() {
        binding.extendedFab.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                .replace(R.id.main_container, SubscriptionFragment())
                .commit()
        }
    }

    private fun setupImageClickListener() {
        binding.visionImage.setOnClickListener {
            requestStoragePermission()
        }
    }

    private fun setupPermissionRequestLauncher() {
        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openImageChooser()
            } else {
                showPermissionDeniedSnackbar()
            }
        }
    }

    private fun setupImageResultLauncher() {
        imageResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let { data ->
                    if (data.clipData != null) {
                        // Handle multiple images
                        val count = data.clipData!!.itemCount
                        for (i in 0 until count) {
                            val uri = data.clipData!!.getItemAt(i).uri
                            addImageToGrid(uri)
                        }
                        binding.visionImage.visibility = View.GONE // Hide the add icon
                    } else {
                        // Handle single image selection
                        data.data?.let { uri ->
                            addImageToGrid(uri)
                            binding.visionImage.visibility = View.GONE // Hide the add icon
                        }
                    }
                }
            }
        }
    }

    private fun initializeImageAdapter() {
        imageAdapter = ImageSectionAdapter(imageUris)
        // Setup RecyclerView with imageAdapter if necessary
        // binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        // binding.recyclerView.adapter = imageAdapter
    }

    private fun addImageToGrid(uri: Uri) {
        imageUris.add(uri)
        val imageView = ImageView(requireContext()).apply {
            layoutParams = GridLayout.LayoutParams().apply {
                width = 0
                height = 300
                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
            }
            setImageURI(uri)
            scaleType = ImageView.ScaleType.CENTER_CROP

            // Set margins around the image
            val layoutParams = layoutParams as GridLayout.LayoutParams
            layoutParams.setMargins(4, 4, 4, 4)
            this.layoutParams = layoutParams

            setOnClickListener {
                openImageGalleryFragment()
            }
        }
        binding.imageGrid.addView(imageView)
    }

    private fun openImageChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true) // Allow multiple images
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        imageResultLauncher.launch(Intent.createChooser(intent, "Select Pictures"))
    }

    private fun requestStoragePermission() {
        when {
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_MEDIA_LOCATION) == PackageManager.PERMISSION_GRANTED -> {
                openImageChooser()
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_MEDIA_LOCATION) // Request permission
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

    private fun showPopupMenu(view: View) {
        val popup = PopupMenu(requireContext(), view)
        popup.menuInflater.inflate(R.menu.photos_menu, popup.menu)

        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_one -> {
                    true
                }
                R.id.action_two -> {
                    true
                }
                R.id.action_three -> {
                    true
                }
                R.id.action_four -> {
                    true
                }
                R.id.action_five ->{
                    true
                }
                R.id.action_siz ->{
                    removeAllImages()
                    true
                }
                else -> false
            }
        }

        popup.show() // Show the popup menu
    }

    private fun removeAllImages() {
        imageUris.clear() // Clear the list of image URIs
        binding.imageGrid.removeAllViews() // Remove all views from the grid
        binding.visionImage.visibility = View.VISIBLE // Show the add icon again
        // Notify the adapter if you're using one
        imageAdapter.notifyDataSetChanged()
    }

}
