package com.test.chatterwave.beta.features.auth_activity.login_fragment

import android.app.Dialog
import android.os.Bundle
import android.view.ContextThemeWrapper
import androidx.fragment.app.DialogFragment
import com.chi.interngram.echo.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class LoginErrorDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = MaterialAlertDialogBuilder(
            ContextThemeWrapper(context, R.style.AlertDialogTryAgainLogin)
        )
            .setTitle(getString(R.string.error_incorrect_mail_or_phone))
            .setMessage(getString(R.string.error_account_doesnt_exist))
            .setPositiveButton(getString(R.string.try_again)) { dialog, _ ->
                dialog.cancel()
            }.create()

        return dialog
    }
}