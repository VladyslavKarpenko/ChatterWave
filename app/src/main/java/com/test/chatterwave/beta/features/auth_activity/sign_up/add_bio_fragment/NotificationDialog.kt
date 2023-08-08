package com.test.chatterwave.beta.features.auth_activity.sign_up.add_bio_fragment

import android.Manifest
import android.app.Dialog
import android.os.Bundle
import android.view.ContextThemeWrapper
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.chi.interngram.echo.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(
            ContextThemeWrapper(context, R.style.AlertDialogTryAgainLogin)
        )
            .setTitle(getString(R.string.allow_alerts))
            .setPositiveButton(getString(R.string.allow)) { _, _ ->
                requestNotificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
            .setNegativeButton(getString(R.string.deny)) { _, _ ->
                findNavController().navigate(R.id.action_notificationDialog_to_mainActivity)
                requireActivity().finish()
            }.create()
    }

    private val requestNotificationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            findNavController().navigate(R.id.action_notificationDialog_to_mainActivity)
            requireActivity().finish()
        }
}