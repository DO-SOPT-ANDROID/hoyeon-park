package org.sopt.dosopttemplate.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.databinding.FragmentDoAndroidBinding

class DoAndroidFragment : Fragment() {
    private var _binding: FragmentDoAndroidBinding? = null
    private val binding: FragmentDoAndroidBinding
        get() = requireNotNull(_binding) { "바인딩 객체가 생성되지 않았습니다" }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentDoAndroidBinding.inflate(inflater, container, false)

        binding.submitButton.setOnClickListener {
            showSubmitAlertDialog()
        }

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

    private fun showSubmitAlertDialog() {
        val dialogBuilder = AlertDialog.Builder(requireActivity())
        dialogBuilder.setTitle("[제출하기]")
        dialogBuilder.setMessage("제출하시겠습니까?")
        dialogBuilder.setPositiveButton("확인") { dialog, which ->
            navigateToPostView()
        }
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }

    private fun navigateToPostView() {
        val intent = Intent(requireActivity(), HomeActivity::class.java)
        startActivity(intent)
        requireActivity().finish() // 현재 화면 종료
    }
}
