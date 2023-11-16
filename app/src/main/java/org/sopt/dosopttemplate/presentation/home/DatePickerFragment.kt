package org.sopt.dosopttemplate.presentation.home

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import org.sopt.dosopttemplate.R
import java.util.Calendar
import java.util.Locale

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(requireActivity(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val selectedDate = String.format(Locale.getDefault(), "%02d-%02d-%02d", year, month + 1, dayOfMonth)
        val editText = activity?.findViewById<EditText>(R.id.deadlineEdit)
        editText?.setText(selectedDate)
    }
}