package com.test.chatterwave.beta.features.main_activity.create_post_fragment.create_post_with_gallery

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
import com.chi.interngram.echo.R
import com.chi.interngram.echo.databinding.FragmentCreatePostWithGalleryBinding
import com.test.chatterwave.beta.di.hilt.assisted.InjectingSavedStateViewModelFactory
import com.test.chatterwave.beta.features.auth_activity.sign_up.avatar_create_fragment.create_with_gallery.GalleryAdapter
import com.test.chatterwave.beta.features.main_activity.MainActivityViewModel
import com.test.chatterwave.beta.utils.NavigationEvent
import com.test.chatterwave.beta.utils.RECYCLERVIEW_SPAN_FOUR
import com.test.chatterwave.beta.utils.toBitmap
import dagger.Lazy
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CreatePostWithGalleryFragment : Fragment() {

    private var _binding: FragmentCreatePostWithGalleryBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainActivityViewModel by activityViewModels()

    @Inject
    lateinit var viewModelFactory: Lazy<InjectingSavedStateViewModelFactory>

    private val viewModel: CreatePostWithGalleryViewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory.get().create(this)
        )[CreatePostWithGalleryViewModel::class.java]
    }

    private val _adapter by lazy {
        GalleryAdapter(lifecycleOwner = this, onItemClicked = {
            mainViewModel.saveCreatePostPhoto(it.toBitmap())
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
                mainViewModel.saveCreatePostPhoto(it[0].toBitmap())
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreatePostWithGalleryBinding.inflate(layoutInflater)
        initBindings()
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

    private fun initBindings(){
        with(binding) {
            mainActivityViewModel = mainViewModel
            lifecycleOwner = this@CreatePostWithGalleryFragment

            photosRecyclerView.apply {
                layoutManager = GridLayoutManager(activity, RECYCLERVIEW_SPAN_FOUR)
                setHasFixedSize(true)
                adapter = _adapter
            }
        }
    }

    private fun observeOnButtonClick() {
        lifecycleScope.launchWhenStarted {
            viewModel.onButtonClickEvent.collect { navEvent ->
                when (navEvent) {
                    NavigationEvent.NavigateNextScreen -> {
                        mainViewModel.saveCreatePostPhoto(binding.postImageView.cropBitmap())
                        findNavController().navigate(R.id.action_createPostWithGalleryFragment_to_createPostFragment)
                    }
                    NavigationEvent.NavigatePreviousScreen -> {
                        mainViewModel.deleteCreatePostPhoto()
                        findNavController().popBackStack()
                    }
                    else -> {}
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}