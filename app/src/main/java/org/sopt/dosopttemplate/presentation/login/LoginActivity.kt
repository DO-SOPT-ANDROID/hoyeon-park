package org.sopt.dosopttemplate.presentation.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.sopt.dosopttemplate.data.auth.RequestLoginDto
import org.sopt.dosopttemplate.data.auth.ResponseLoginDto
import org.sopt.dosopttemplate.data.ServicePool.authService
import org.sopt.dosopttemplate.databinding.ActivityLoginBinding
import org.sopt.dosopttemplate.presentation.home.HomeActivity
import org.sopt.dosopttemplate.presentation.signup.SignUpActivity
import retrofit2.Call
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    private var id: String? = null
    private var pw: String? = null
    private var name: String? = null
    private var mbti: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setResult()

        initSignBtnClickListener()
        //initLoginBtnClickListener()
        login()
    }

    private fun setResult() {
        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                id = result.data?.getStringExtra("username") ?: ""
                pw = result.data?.getStringExtra("password") ?: ""
                name = result.data?.getStringExtra("nickname") ?: ""
                makeSnackbar("회원가입에 성공하였습니다!")
            }
        }
    }

    fun initSignBtnClickListener() {
        binding.signButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            resultLauncher.launch(intent)
        }
    }

    fun makeSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun login() {
        binding.loginButton.setOnClickListener{
            val id = binding.idEdit.text.toString()
            val password = binding.pwEdit.text.toString()
            authService.login(RequestLoginDto(id, password))
                .enqueue(object : retrofit2.Callback<ResponseLoginDto> {
                    override fun onResponse(
                        call: Call<ResponseLoginDto>,
                        response: Response<ResponseLoginDto>,
                    ) {
                        if (response.isSuccessful) {
                            val data: ResponseLoginDto = response.body()!!
                            val userId = data.id
                            Toast.makeText(
                                this@LoginActivity,
                                "로그인이 성공하였습니다! 유저의 ID는 $userId 입니다.",
                                Toast.LENGTH_SHORT,
                            ).show()

                            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                            startActivity(intent)
                        }
                        else{
                            Toast.makeText(
                                this@LoginActivity,
                                "로그인에 실패하였습니다",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseLoginDto>, t: Throwable) {
                        val errorMessage = "서버 통신 실패: ${t.message}"
                        Toast.makeText(this@LoginActivity, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }
}

