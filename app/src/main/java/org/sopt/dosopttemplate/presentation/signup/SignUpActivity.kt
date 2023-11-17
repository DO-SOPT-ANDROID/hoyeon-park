package org.sopt.dosopttemplate.presentation.signup

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import org.sopt.dosopttemplate.data.auth.RequestSignUpDto
import org.sopt.dosopttemplate.data.ServicePool
import org.sopt.dosopttemplate.databinding.ActivitySignUpBinding
import org.sopt.dosopttemplate.presentation.login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var startLoginActivityForResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initSignBtnClickListener()
        signup()

        startLoginActivityForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
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
                        }
                        else{
                            Log.e("SignUpActivity", "Unsuccessful response body: ${response.errorBody()?.string()}")
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
}