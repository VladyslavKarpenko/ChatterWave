package com.test.chatterwave.beta.features.main_activity.edit_profile_fragment

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.chi.interngram.echo.R
import com.chi.interngram.echo.databinding.EditProfileAvatarDialogBinding
import com.test.chatterwave.beta.features.main_activity.MainActivityViewModel
import com.test.chatterwave.beta.utils.requestCameraPermission
import com.test.chatterwave.beta.utils.requestPermission
import com.test.chatterwave.beta.utils.requestStoragePermission
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileAvatarDialog : BottomSheetDialogFragment() {

    private var _binding: EditProfileAvatarDialogBinding? = null
    private val binding get() = _binding!!

    private var _cameraPermission: ActivityResultLauncher<String> = requestCameraPermission()

    private var _storagePermission: ActivityResultLauncher<String> = requestStoragePermission()

    private val mainViewModel: MainActivityViewModel by activityViewModels()

    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialogTheme
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = EditProfileAvatarDialogBinding.bind(
            inflater.inflate(
                R.layout.edit_profile_avatar_dialog,
                container,
                false
            )
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            closeMenu.setOnClickListener {
                dismiss()
            }

            takePhotoMenu.setOnClickListener {
                checkCameraPermission()
            }

            chooseFromLibraryMenu.setOnClickListener {
                checkLocalStoragePermission()
            }
            deleteAvatar.setOnClickListener {
                findNavController().navigate(R.id.action_editProfileAvatarDialog_to_editAvatarDeleteDialog)
            }
        }
    }

    private fun checkCameraPermission() {
        requestPermission(_cameraPermission, Manifest.permission.CAMERA)
    }

    private fun checkLocalStoragePermission() {
        requestPermission(_storagePermission, Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}