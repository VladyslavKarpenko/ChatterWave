package com.test.chatterwave.beta.features.main_activity.edit_profile_fragment

import android.app.Dialog
import android.os.Bundle
import android.view.ContextThemeWrapper
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.chi.interngram.echo.R
import com.test.chatterwave.beta.features.main_activity.MainActivityViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EditAvatarDeleteDialog  : DialogFragment(){

    private val mainViewModel: MainActivityViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(
            ContextThemeWrapper(context, R.style.AlertDialogTryAgainLogin)
        )
            .setTitle("Are you sure you want to delete the photo?")
            .setPositiveButton(getString(R.string.delete)) { _, _ ->
                mainViewModel.deleteEditAvatarNew()
                mainViewModel.deleteEditAvatar()
            }
            .setNegativeButton(getString(R.string.cancel)){ _, _ ->
            }
            .create()
    }
}