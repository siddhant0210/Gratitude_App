import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gratitude.R
import com.example.gratitude.activities.LandingActivity
import com.example.gratitude.adapters.ImageSectionAdapter
import com.example.gratitude.databinding.FragmentImageGalleryBinding
import com.example.gratitude.helper.SECTIONNAME
import com.example.gratitude.prefmanager.PrefManager
import com.google.android.material.bottomnavigation.BottomNavigationView

class ImageGalleryFragment : Fragment() {

    private var _binding: FragmentImageGalleryBinding? = null
    private val binding get() = _binding!!

    private lateinit var imageAdapter: ImageSectionAdapter
    private lateinit var imageUris: MutableList<Uri> // Make sure it's mutable
    private lateinit var prefManager: PrefManager
    private lateinit var imageResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentImageGalleryBinding.inflate(inflater, container, false)
        prefManager = PrefManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the shared imageUris list from arguments
        imageUris = arguments?.getParcelableArrayList<Uri>("imageUris") ?: mutableListOf()
        imageAdapter = ImageSectionAdapter(imageUris)
        binding.imageView.layoutManager = LinearLayoutManager(requireContext())
        binding.imageView.adapter = imageAdapter

        setupToolbar()

        // Setup Add button click listener
        binding.addBtn.setOnClickListener {
            openImageChooser()
        }

        // Setup Done button click listener
        binding.doneBtn.setOnClickListener {
            navigateBackToAddPhotosFragment()
        }

        setupImageResultLauncher()
    }

    @SuppressLint("SetTextI18n")
    private fun setupToolbar() {
        val activity = requireActivity() as LandingActivity
        val bottomNav = activity.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.visibility = View.GONE
        val toolbarTitle = activity.findViewById<TextView>(R.id.toolbar_title)
        toolbarTitle.text = "${prefManager.getSectionName(SECTIONNAME)}"
        val toolbar = activity.findViewById<Toolbar>(R.id.toolbar) as androidx.appcompat.widget.Toolbar
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24)
        toolbar.setBackgroundColor(resources.getColor(R.color.white))
        val toolbarImage = activity.findViewById<ImageView>(R.id.profile_icon)
        toolbarImage.visibility = View.GONE
        val streakIcon = activity.findViewById<ImageView>(R.id.streak_button)
        streakIcon.visibility = View.GONE
        val challengeIcon = activity.findViewById<ImageView>(R.id.challenges_button)
        challengeIcon.visibility = View.GONE

        toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    // Launch image picker when the Add button is clicked
    private fun openImageChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true) // Allow multiple images
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        imageResultLauncher.launch(Intent.createChooser(intent, "Select Pictures"))
    }

    // Handle the image result when the user selects images
    private fun setupImageResultLauncher() {
        imageResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let { data ->
                    if (data.clipData != null) {
                        // Handle multiple images
                        val count = data.clipData!!.itemCount
                        for (i in 0 until count) {
                            val uri = data.clipData!!.getItemAt(i).uri
                            addImageToList(uri)
                        }
                    } else {
                        // Handle single image selection
                        data.data?.let { uri ->
                            addImageToList(uri)
                        }
                    }
                }
            }
        }
    }

    // Method to add images to the list and update the adapter
    private fun addImageToList(uri: Uri) {
        imageUris.add(uri) // Add the new image URI to the shared list
        imageAdapter.notifyDataSetChanged() // Notify the adapter to update the RecyclerView
    }

    // Method to navigate back to AddPhotosFragment
    private fun navigateBackToAddPhotosFragment() {
        // Pop the back stack to return to the previous fragment
        parentFragmentManager.popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
