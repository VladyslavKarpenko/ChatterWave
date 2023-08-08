package com.test.chatterwave.beta.features.auth_activity.sign_up.avatar_create_fragment.create_with_camera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.chi.interngram.echo.databinding.FragmentCreateAvatarBinding
import com.test.chatterwave.beta.di.hilt.assisted.InjectingSavedStateViewModelFactory
import com.test.chatterwave.beta.features.auth_activity.AuthViewModel
import com.test.chatterwave.beta.utils.NavigationEvent
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.PictureResult
import dagger.Lazy
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class CreateAvatarWithCameraFragment : Fragment() {

    private var _binding: FragmentCreateAvatarBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by activityViewModels()

    @Inject
    lateinit var viewModelFactory: Lazy<InjectingSavedStateViewModelFactory>

    private val viewModel: CreateAvatarWithCameraViewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory.get().create(this)
        )[CreateAvatarWithCameraViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeButtonClick()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateAvatarBinding.inflate(layoutInflater)
        initDataBinding()
        return binding.root
    }

    private fun initDataBinding() {
        with(binding) {
            authVM = authViewModel
            cameraVM = viewModel
            lifecycleOwner = this@CreateAvatarWithCameraFragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authViewModel.deleteUserAvatar()
        initBinding()
    }

    private fun initBinding() {
        with(binding){
            cameraView.setLifecycleOwner(viewLifecycleOwner)
            changeCameraFacing.setOnClickListener {
                viewModel.changeCameraFacing()
            }

            cameraView.addCameraListener(object : CameraListener() {
                override fun onPictureTaken(result: PictureResult) {
                    super.onPictureTaken(result)
                    result.toBitmap {
                        if (it != null) {
                            authViewModel.saveUserAvatar(it)
                        }
                    }
                }
            })

            takePhoto.setOnClickListener {
                cameraView.takePictureSnapshot()
            }

            cancelButton.setOnClickListener {
                viewModel.onCancelButtonClicked()
            }

            nextButton.setOnClickListener {
                viewModel.onNextButtonClicked()
            }
        }
    }

    private fun observeButtonClick(){
        lifecycleScope.launchWhenStarted {
            viewModel.onButtonClickEvent.collect{ navEvent ->
                when(navEvent){
                    is NavigationEvent.NavigateNextScreen -> {
                        authViewModel.saveUserAvatar(binding.tookPhotoResult.cropBitmap())
                        findNavController().popBackStack()
                    }
                    is NavigationEvent.NavigatePreviousScreen -> {
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