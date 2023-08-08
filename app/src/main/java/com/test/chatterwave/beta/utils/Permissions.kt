package com.test.chatterwave.beta.utils

import android.Manifest
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.chi.interngram.echo.R
import com.test.chatterwave.beta.features.auth_activity.AuthActivity

fun Fragment.requestPermission(request: ActivityResultLauncher<String>, permissions: String) = request.launch(permissions)

fun Fragment.requestCameraPermission() =
    registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            when {
                this.requireActivity() is AuthActivity -> findNavController().navigate(R.id.action_addAvatarDialog_to_createAvatarFragment)
                findNavController().currentDestination?.id == R.id.addAvatarDialog2 -> findNavController().navigate(R.id.action_addAvatarDialog2_to_createPostWithCameraFragment)
                findNavController().currentDestination?.id == R.id.addAvatarDialog3 -> findNavController().navigate(R.id.action_addAvatarDialog3_to_editAvatarCameraFragment)
                else -> findNavController().navigate(R.id.editAvatarCameraFragment)
            }
        } else {
            if (!shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                val isCameraPermission = Bundle().apply {
                    putBoolean(IS_CAMERA_PERMISSION, true)
                }
                when {
                    this.requireActivity() is AuthActivity -> findNavController().navigate(R.id.action_addAvatarDialog_to_openSettingsDialog, isCameraPermission)
                    findNavController().currentDestination?.id == R.id.addAvatarDialog2 -> findNavController().navigate(R.id.action_addAvatarDialog2_to_openSettingsDialog2, isCameraPermission)
                    findNavController().currentDestination?.id == R.id.addAvatarDialog3 -> findNavController().navigate(R.id.action_addAvatarDialog3_to_openSettingsDialog3, isCameraPermission)
                    else -> findNavController().navigate(R.id.openSettingsDialog3, isCameraPermission)
                }
            }
        }
    }

fun Fragment.requestStoragePermission() =
    registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            when{
                this.requireActivity() is AuthActivity -> findNavController().navigate(R.id.action_addAvatarDialog_to_createAvatarWithGalleryFragment)
                findNavController().currentDestination?.id == R.id.addAvatarDialog2 -> findNavController().navigate(R.id.action_addAvatarDialog2_to_createPostWithGalleryFragment)
                findNavController().currentDestination?.id == R.id.addAvatarDialog3 -> findNavController().navigate(R.id.action_addAvatarDialog3_to_editAvatarGalleryFragment)
                else -> findNavController().navigate(R.id.editAvatarGalleryFragment)
            }
        } else {
            if (!shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                val isCameraPermission = Bundle().apply {
                    putBoolean(IS_CAMERA_PERMISSION, false)
                }
                when {
                    this.requireActivity() is AuthActivity -> findNavController().navigate(R.id.action_addAvatarDialog_to_openSettingsDialog, isCameraPermission)
                    findNavController().currentDestination?.id == R.id.addAvatarDialog2 -> findNavController().navigate(R.id.action_addAvatarDialog2_to_openSettingsDialog2, isCameraPermission)
                    findNavController().currentDestination?.id == R.id.addAvatarDialog3 -> findNavController().navigate(R.id.action_addAvatarDialog3_to_openSettingsDialog3, isCameraPermission)
                    else -> findNavController().navigate(R.id.openSettingsDialog3, isCameraPermission)
                }
            }
        }
    }