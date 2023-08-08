package com.test.chatterwave.beta.features.main_activity.edit_profile_fragment.edit_with_camera_fragment

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
import com.chi.interngram.echo.databinding.FragmentEditAvatarCameraBinding
import com.test.chatterwave.beta.di.hilt.assisted.InjectingSavedStateViewModelFactory
import com.test.chatterwave.beta.features.main_activity.MainActivityViewModel
import com.test.chatterwave.beta.features.main_activity.ui.MainActivity
import com.test.chatterwave.beta.utils.NavigationEvent
import com.test.chatterwave.beta.utils.callbacks.OnBackPressedListener
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.PictureResult
import dagger.Lazy
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EditAvatarCameraFragment : Fragment() {

    private var _binding: FragmentEditAvatarCameraBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainActivityViewModel by activityViewModels()

    @Inject
    lateinit var viewModelFactory: Lazy<InjectingSavedStateViewModelFactory>

    private val viewModel: EditAvatarCameraViewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory.get().create(this)
        )[EditAvatarCameraViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeButtonClick()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditAvatarCameraBinding.inflate(layoutInflater)
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
                            mainViewModel.saveUserNewAvatar(it)
                        }
                    }
                }
            })
            nextButton.setOnClickListener {
                mainViewModel.saveUserAvatar(tookPhotoResult.cropBitmap())
                findNavController().popBackStack(R.id.editProfileFragment, false)
            }
        }
    }

    private fun observeButtonClick(){
        lifecycleScope.launchWhenStarted {
            viewModel.onButtonClickEvent.collect{ navEvent ->
                when(navEvent){
                    is NavigationEvent.NavigatePreviousScreen -> {
                        mainViewModel.deleteEditAvatarNew()
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
            editProfileAvatarCameraVM = viewModel
            lifecycleOwner = this@EditAvatarCameraFragment
        }
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setOnBackPressedListener(object : OnBackPressedListener {
            override fun onBackPressedClickListener() {
                viewModel.onCancelButtonClicked()
            }
        })
    }

    override fun onStop() {
        super.onStop()
        (activity as MainActivity).setOnBackPressedListener(null)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}