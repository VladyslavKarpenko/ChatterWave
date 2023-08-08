package com.test.chatterwave.beta.features.main_activity.edit_profile_fragment.edit_with_gallery_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.chi.interngram.echo.databinding.FragmentEditAvatarGalleryBinding
import com.test.chatterwave.beta.di.hilt.assisted.InjectingSavedStateViewModelFactory
import com.test.chatterwave.beta.features.auth_activity.sign_up.avatar_create_fragment.create_with_gallery.GalleryAdapter
import com.test.chatterwave.beta.features.main_activity.MainActivityViewModel
import com.test.chatterwave.beta.features.main_activity.ui.MainActivity
import com.test.chatterwave.beta.utils.NavigationEvent
import com.test.chatterwave.beta.utils.RECYCLERVIEW_SPAN_FOUR
import com.test.chatterwave.beta.utils.callbacks.OnBackPressedListener
import com.test.chatterwave.beta.utils.toBitmap
import dagger.Lazy
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EditAvatarGalleryFragment : Fragment() {

    private var _binding: FragmentEditAvatarGalleryBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainActivityViewModel by activityViewModels()

    @Inject
    lateinit var viewModelFactory: Lazy<InjectingSavedStateViewModelFactory>

    private val viewModel: EditAvatarGalleryViewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory.get().create(this)
        )[EditAvatarGalleryViewModel::class.java]
    }

    private val _adapter by lazy {
        GalleryAdapter(lifecycleOwner = this, onItemClicked = {
            mainViewModel.saveUserNewAvatar(it.toBitmap())
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeFetchImages()
        observeOnButtonClick()
        viewModel.getImages()
    }

    private fun observeFetchImages() {
        lifecycleScope.launchWhenStarted {
            viewModel.userPhotos.collect {
                _adapter.differ.submitList(it)
                mainViewModel.saveUserNewAvatar(it[0].toBitmap())
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditAvatarGalleryBinding.inflate(layoutInflater)
        initDataBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nextButton.setOnClickListener {
            viewModel.onNextButtonClicked()
        }
        binding.cancelButton.setOnClickListener {
            viewModel.onCancelButtonClicked()
        }
    }

    private fun observeOnButtonClick() {
        lifecycleScope.launchWhenStarted {
            viewModel.onButtonClickEvent.collect { navEvent ->
                when (navEvent) {
                    NavigationEvent.NavigateNextScreen -> {
                        mainViewModel.saveUserAvatar(binding.postImageView.cropBitmap())
                        findNavController().popBackStack()
                    }
                    NavigationEvent.NavigatePreviousScreen -> {
                        mainViewModel.deleteEditAvatarNew()
                        findNavController().popBackStack()
                    }
                    else -> {}
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setOnBackPressedListener(object : OnBackPressedListener {
            override fun onBackPressedClickListener() {
                mainViewModel.deleteEditAvatarNew()
                mainViewModel.deleteEditAvatar()
                findNavController().popBackStack()
            }
        })
    }

    override fun onStop() {
        super.onStop()
        (activity as MainActivity).setOnBackPressedListener(null)
    }

    private fun initDataBinding() {
        with(binding) {
            mainActivityViewModel = mainViewModel
            editAvatarGalleryViewModel = viewModel
            lifecycleOwner = this@EditAvatarGalleryFragment

            photosRecyclerView.apply {
                layoutManager = GridLayoutManager(activity, RECYCLERVIEW_SPAN_FOUR)
                setHasFixedSize(true)
                adapter = _adapter
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}