package com.test.chatterwave.beta.features.auth_activity.sign_up.avatar_fragment

import android.app.Dialog
import android.os.Bundle
import android.view.ContextThemeWrapper
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.chi.interngram.echo.R
import com.test.chatterwave.beta.features.auth_activity.AuthViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteAvatarDialog : DialogFragment(){

    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(
            ContextThemeWrapper(context, R.style.AlertDialogTryAgainLogin)
        )
            .setTitle(getString(R.string.delete_profile_photo_dialog_title))
            .setPositiveButton(getString(R.string.delete)) { _, _ ->
                authViewModel.deleteUserAvatar()
            }
            .setNegativeButton(getString(R.string.cancel)){ _, _ ->
            }
            .create()
    }
}