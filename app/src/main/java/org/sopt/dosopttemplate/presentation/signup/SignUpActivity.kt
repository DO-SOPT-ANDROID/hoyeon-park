package org.sopt.dosopttemplate.presentation.signup

import SignUpViewModel
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import org.sopt.dosopttemplate.data.ServicePool
import org.sopt.dosopttemplate.data.auth.RequestSignUpDto
import org.sopt.dosopttemplate.databinding.ActivitySignUpBinding
import org.sopt.dosopttemplate.presentation.login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var startLoginActivityForResult: ActivityResultLauncher<Intent>
    private lateinit var viewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)

        setupTextWatchers()
        signup()

        startLoginActivityForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val intent = result.data
                    if (intent != null) {
                        val loginSuccessful = intent.getBooleanExtra("loginSuccessful", false)
                        if (loginSuccessful) {
                            finish()
                        }
                    }
                }
            }
    }

    private fun signup() {
        binding.btSignUp.setOnClickListener {
            val username = binding.etIdEdit.text.toString()
            val password = binding.etPwEdit.text.toString()
            val nickname = binding.etNicknameEdit.text.toString()

            ServicePool.authService.signup(RequestSignUpDto(username, password, nickname))
                .enqueue(object : Callback<Unit> {
                    override fun onResponse(
                        call: Call<Unit>,
                        response: Response<Unit>,
                    ) {
                        Log.d("SignUpActivity", "onResponse: ${response.isSuccessful}")
                        if (response.isSuccessful) {
                            Toast.makeText(
                                this@SignUpActivity,
                                "회원가입에 성공하였습니다",
                                Toast.LENGTH_SHORT,
                            ).show()

                            val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                            startActivity(intent)
                        } else {
                            Log.e(
                                "SignUpActivity",
                                "Unsuccessful response body: ${response.errorBody()?.string()}",
                            )
                            Toast.makeText(
                                this@SignUpActivity,
                                "회원가입에 실패하였습니다",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<Unit>, t: Throwable) {
                        Log.e("SignUpActivity", "onFailure: ${t.message}")
                        val errorMessage = "서버 통신 실패: ${t.message}"
                        Toast.makeText(this@SignUpActivity, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                })
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

        // SignupViewModel에서 발생한 아이디와 비밀번호의 유효성 검사 결과 버튼 활성화 여부 업데이트
        viewModel.idErrorMessage.observe(this) { error ->
            binding.etIdEdit.error = error
        }

        viewModel.passwordErrorMessage.observe(this) { error ->
            binding.etPwEdit.error = error
        }

        viewModel.isSignUpButtonEnabled.observe(this) { isEnabled ->
            binding.btSignUp.isEnabled = isEnabled
        }
    }
}
