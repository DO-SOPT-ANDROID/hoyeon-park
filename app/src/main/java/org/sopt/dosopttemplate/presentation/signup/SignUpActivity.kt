package org.sopt.dosopttemplate.presentation.signup

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.sopt.dosopttemplate.data.model.SignupState
import org.sopt.dosopttemplate.databinding.ActivitySignUpBinding
import org.sopt.dosopttemplate.presentation.login.LoginActivity

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupTextWatchers()
        observeViewModel()

        binding.btSignUp.setOnClickListener {
            val username = binding.etIdEdit.text.toString()
            val password = binding.etPwEdit.text.toString()
            val nickname = binding.etNicknameEdit.text.toString()
            viewModel.signup(username, password, nickname)
        }
    }

    private fun setupTextWatchers() {
        binding.etIdEdit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.validateId(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        binding.etPwEdit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.validatePassword(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.idErrorMessage.collect { error ->
                binding.etIdEdit.error = error
            }
        }

        lifecycleScope.launch {
            viewModel.passwordErrorMessage.collect { error ->
                binding.etPwEdit.error = error
            }
        }

        lifecycleScope.launch {
            viewModel.isSignUpButtonEnabled.collect { isEnabled ->
                binding.btSignUp.isEnabled = isEnabled
            }
        }

        lifecycleScope.launch {
            viewModel.signUpStatus.collect { status ->
                when (status) {
                    is SignupState.Success -> {
                        Toast.makeText(this@SignUpActivity, "회원가입에 성공하였습니다", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
                        finish()
                    }
                    is SignupState.Error -> {
                        Toast.makeText(this@SignUpActivity, "회원가입에 실패하였습니다", Toast.LENGTH_SHORT).show()
                    }
                    is SignupState.Loading, null -> {
                        // 대기 중은 따로 안띄우기
                    }
                }
            }
        }
    }
}
