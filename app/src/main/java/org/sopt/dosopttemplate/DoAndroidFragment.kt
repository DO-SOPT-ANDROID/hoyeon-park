package org.sopt.dosopttemplate

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import org.sopt.dosopttemplate.databinding.FragmentDoAndroidBinding


class DoAndroidFragment: Fragment() {
    private var _binding: FragmentDoAndroidBinding? = null
    private val binding: FragmentDoAndroidBinding
        get() = requireNotNull(_binding) { "바인딩 객체가 생성되지 않았습니다" }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDoAndroidBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dateEditText = view.findViewById<EditText>(R.id.deadlineEdit)
        val datePickerButton = view.findViewById<Button>(R.id.datePickerButton)

        dateEditText.setOnClickListener {
            showDatePickerDialog(view)
        }

        datePickerButton.setOnClickListener {
            showDatePickerDialog(view)
        }
    }

    fun showDatePickerDialog(view: View) {
        val newFragment = DatePickerFragment()
        newFragment.show(childFragmentManager, "datePicker")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
