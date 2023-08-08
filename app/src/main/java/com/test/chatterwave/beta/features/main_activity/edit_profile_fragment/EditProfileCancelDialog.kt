package com.test.chatterwave.beta.features.main_activity.edit_profile_fragment

import android.app.Dialog
import android.os.Bundle
import android.view.ContextThemeWrapper
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.chi.interngram.echo.R
import com.test.chatterwave.beta.features.main_activity.MainActivityViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileCancelDialog : DialogFragment(){

    private val mainViewModel: MainActivityViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(
            ContextThemeWrapper(context, R.style.AlertDialogTryAgainLogin)
        )
            .setTitle(getString(R.string.leave_withput_saving_edit_profile))
            .setPositiveButton(getString(R.string.leave_page)) { _, _ ->
                mainViewModel.deleteEditAvatarNew()
                mainViewModel.deleteEditAvatar()
                findNavController().popBackStack(R.id.editProfileFragment,true)
            }
            .setNegativeButton(getString(R.string.cancel)){ _, _ ->
            }
            .create()
    }
}