package com.test.chatterwave.beta.features.auth_activity.sign_up.avatar_fragment

import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.ContextThemeWrapper
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.chi.interngram.echo.R
import com.test.chatterwave.beta.utils.PACKAGE_NAME
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class OpenSettingsDialog : DialogFragment() {

    private val args: OpenSettingsDialogArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val title: String
        val message: String

        if (args.isCameraPermission){
            title = getString(R.string.take_photo_with_interngram)
            message =  getString(R.string.allow_access_to_camera)
        }else {
             title =  getString(R.string.allow_access_photos)
             message = getString(R.string.allow_access_photos_text)
        }

        return MaterialAlertDialogBuilder(
            ContextThemeWrapper(context, R.style.AlertDialogTryAgainLogin)
        )
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(getString(R.string.allow_in_settings)) { _, _ ->
                askForOpenAppSettings()
            }.create()
    }

    private fun askForOpenAppSettings(){
        val appSettingsIntent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts(PACKAGE_NAME,requireActivity().packageName, null)
        )
        if (requireActivity().packageManager.resolveActivity(appSettingsIntent, PackageManager.MATCH_DEFAULT_ONLY) == null){
            Timber.d(getString(R.string.permission_denied_forever))
        }else{
            startActivity(appSettingsIntent)
        }
    }
}