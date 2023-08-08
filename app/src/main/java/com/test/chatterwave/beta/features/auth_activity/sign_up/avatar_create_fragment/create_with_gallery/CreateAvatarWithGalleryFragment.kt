package com.test.chatterwave.beta.features.auth_activity.sign_up.avatar_create_fragment.create_with_gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.chi.interngram.echo.databinding.FragmentCreateAvatarWithGalleryBinding
import com.test.chatterwave.beta.di.hilt.assisted.InjectingSavedStateViewModelFactory
import com.test.chatterwave.beta.features.auth_activity.AuthActivity
import com.test.chatterwave.beta.features.auth_activity.AuthViewModel
import com.test.chatterwave.beta.utils.NavigationEvent
import com.test.chatterwave.beta.utils.RECYCLERVIEW_SPAN_FOUR
import com.test.chatterwave.beta.utils.callbacks.OnBackPressedListener
import com.test.chatterwave.beta.utils.toBitmap
import dagger.Lazy
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CreateAvatarWithGalleryFragment : Fragment() {

    private var _binding: FragmentCreateAvatarWithGalleryBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by activityViewModels()

    @Inject
    lateinit var viewModelFactory: Lazy<InjectingSavedStateViewModelFactory>

    private val _adapter by lazy {
        GalleryAdapter(lifecycleOwner = this, onItemClicked = {
            authViewModel.saveUserAvatar(it.toBitmap())
        })
    }

    private val viewModel: CreateAvatarWithGalleryViewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory.get().create(this)
        )[CreateAvatarWithGalleryViewModel::class.java]
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
                authViewModel.saveUserAvatar(it[0].toBitmap())
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateAvatarWithGalleryBinding.inflate(layoutInflater)
        with(binding) {
            authVM = authViewModel
            lifecycleOwner = this@CreateAvatarWithGalleryFragment

            photosRecyclerView.apply {
                layoutManager = GridLayoutManager(activity, RECYCLERVIEW_SPAN_FOUR)
                setHasFixedSize(true)
                adapter = _adapter
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.confirmButton.setOnClickListener {
            viewModel.onNextButtonClicked()
        }
        binding.cancelButton.setOnClickListener {
            viewModel.onCancelButtonClicked()
        }
    }

    override fun onStart() {
        super.onStart()
        (activity as AuthActivity).setOnBackPressedListener(object : OnBackPressedListener {
            override fun onBackPressedClickListener() {
                viewModel.onCancelButtonClicked()
            }

        })
    }

    override fun onStop() {
        super.onStop()
        (activity as AuthActivity).setOnBackPressedListener(null)
    }

    private fun observeOnButtonClick() {
        lifecycleScope.launchWhenStarted {
            viewModel.onButtonClickEvent.collect { navEvent ->
                when (navEvent) {
                    NavigationEvent.NavigateNextScreen -> {
                        authViewModel.saveUserAvatar(binding.avatarImageView.cropBitmap())
                        findNavController().popBackStack()
                    }
                    NavigationEvent.NavigatePreviousScreen -> {
                        authViewModel.deleteUserAvatar()
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