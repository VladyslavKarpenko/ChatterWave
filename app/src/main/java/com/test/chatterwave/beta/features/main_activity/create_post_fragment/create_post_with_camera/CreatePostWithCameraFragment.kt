package com.test.chatterwave.beta.features.main_activity.create_post_fragment.create_post_with_camera

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.chi.interngram.echo.R
import com.chi.interngram.echo.databinding.FragmentCreatePostWithCameraBinding
import com.test.chatterwave.beta.di.hilt.assisted.InjectingSavedStateViewModelFactory
import com.test.chatterwave.beta.features.main_activity.MainActivityViewModel
import com.test.chatterwave.beta.utils.NavigationEvent
import com.test.chatterwave.beta.utils.UIResponseState
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.PictureResult
import dagger.Lazy
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CreatePostWithCameraFragment : Fragment() {

    private var _binding: FragmentCreatePostWithCameraBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainActivityViewModel by activityViewModels()

    @Inject
    lateinit var viewModelFactory: Lazy<InjectingSavedStateViewModelFactory>

    private val viewModel: CreatePostWithCameraViewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory.get().create(this)
        )[CreatePostWithCameraViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeOnPhotoTaken()
        observeButtonClick()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreatePostWithCameraBinding.inflate(layoutInflater)
        initDataBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            cameraView.setLifecycleOwner(viewLifecycleOwner)
            changeCameraFacing.setOnClickListener {
                viewModel.changeCameraFacing()
            }

            takePhoto.setOnClickListener {
                cameraView.takePictureSnapshot()
            }

            cancelButton.setOnClickListener {
                viewModel.onCancelButtonClicked()
            }

            cameraView.addCameraListener(object : CameraListener() {
                override fun onPictureTaken(result: PictureResult) {
                    super.onPictureTaken(result)
                    result.toBitmap {
                        if (it != null) {
                            mainViewModel.saveCreatePostPhoto(it)
                        }
                    }
                }
            })
        }
    }

    private fun observeOnPhotoTaken(){
        lifecycleScope.launchWhenStarted {
            mainViewModel.userCreatePostPhoto.collect{
                when(it){
                    is UIResponseState.Success<*> -> findNavController().navigate(R.id.action_createPostWithCameraFragment_to_createPostFragment)
                    else -> {}
                }
            }
        }
    }

    private fun observeButtonClick(){
        lifecycleScope.launchWhenStarted {
            viewModel.onButtonClickEvent.collect{ navEvent ->
                when(navEvent){
                    is NavigationEvent.NavigatePreviousScreen -> {
                        mainViewModel.deleteCreatePostPhoto()
                        findNavController().popBackStack()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun initDataBinding() {
        with(binding) {
            mainActivityViewModel = mainViewModel
            createPostWithCameraVM = viewModel
            lifecycleOwner = this@CreatePostWithCameraFragment
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}