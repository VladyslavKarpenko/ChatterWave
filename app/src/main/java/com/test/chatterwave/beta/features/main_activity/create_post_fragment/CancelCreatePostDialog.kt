package com.test.chatterwave.beta.features.main_activity.create_post_fragment

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
class CancelCreatePostDialog : DialogFragment(){

    private val mainViewModel: MainActivityViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(
            ContextThemeWrapper(context, R.style.AlertDialogTryAgainLogin)
        )
            .setTitle(getString(R.string.leave_withput_saving_post))
            .setPositiveButton(getString(R.string.yes_leave)) { _, _ ->
                mainViewModel.deleteCreatePostPhoto()
                findNavController().popBackStack(R.id.create_post_navigation,true)
            }
            .setNegativeButton(getString(R.string.cancel)){ _, _ ->
            }
            .create()
    }
}