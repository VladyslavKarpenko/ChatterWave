package com.test.chatterwave.beta.features.auth_activity.sign_up.birthday_fragment

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.chi.interngram.echo.R
import com.test.chatterwave.beta.features.auth_activity.AuthViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BirthdayDatePickerFragment : DialogFragment() {

    private val authViewModel: AuthViewModel by activityViewModels()

    private val args: BirthdayDatePickerFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val minYearOfRegistration by lazy {
            args.minYearRegistration
        }
        val maxYearOfRegistration by lazy {
            args.maxYearRegistration
        }

        val dateValidator: CalendarConstraints.DateValidator =
            DateValidatorPointBackward.before((minYearOfRegistration))

        val constraintsBuilder =
            CalendarConstraints.Builder()
                .setOpenAt(minYearOfRegistration)
                .setStart(maxYearOfRegistration)
                .setEnd(minYearOfRegistration)
                .setValidator(dateValidator)

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setCalendarConstraints(constraintsBuilder.build())
            .setTitleText(resources.getString(R.string.select_date)).build()

        datePicker.show(activity?.supportFragmentManager!!, TAG)

        datePicker.addOnPositiveButtonClickListener {
            authViewModel.saveBirthdayToViewModel(it)
            dismiss()
        }
        datePicker.addOnNegativeButtonClickListener {
            dismiss()
        }
        datePicker.addOnDismissListener {
            dismiss()
        }
    }

    companion object {
        private const val TAG = "BirthdayDatePickerTag"
    }
}