package com.test.chatterwave.beta.features.auth_activity.sign_up.avatar_fragment

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import com.chi.interngram.echo.R
import com.chi.interngram.echo.databinding.AddAvatarDialogBinding
import com.test.chatterwave.beta.utils.requestCameraPermission
import com.test.chatterwave.beta.utils.requestPermission
import com.test.chatterwave.beta.utils.requestStoragePermission
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddAvatarDialog : BottomSheetDialogFragment() {

    private var _binding: AddAvatarDialogBinding? = null
    private val binding get() = _binding!!

    private var _cameraPermission: ActivityResultLauncher<String> = requestCameraPermission()

    private var _storagePermission:  ActivityResultLauncher<String> = requestStoragePermission()

    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialogTheme
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddAvatarDialogBinding.bind(
            inflater.inflate(
                R.layout.add_avatar_dialog,
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