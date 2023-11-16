package org.sopt.dosopttemplate.presentation.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.sopt.dosopttemplate.data.RequestLoginDto
import org.sopt.dosopttemplate.data.ResponseLoginDto
import org.sopt.dosopttemplate.data.ServicePool.authService
import org.sopt.dosopttemplate.databinding.ActivityLoginBinding
import org.sopt.dosopttemplate.presentation.home.HomeActivity
import org.sopt.dosopttemplate.presentation.signup.SignUpActivity
import retrofit2.Call
import retrofit2.Response
import java.util.Locale

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
                id = result.data?.getStringExtra("idValue") ?: ""
                pw = result.data?.getStringExtra("pwValue") ?: ""
                name = result.data?.getStringExtra("nameValue") ?: ""
                mbti = result.data?.getStringExtra("mbtiValue")?.uppercase(Locale.ROOT) ?: ""
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

    /*fun initLoginBtnClickListener() {
        binding.loginButton.setOnClickListener {
            if (binding.idEdit.text.toString() == id && binding.pwEdit.text.toString() == pw
            ) {
                makeToast("로그인에 성공했습니다!")
                Log.d("LoginActivity", "로그인 성공 - ID: $id, PW: $pw")
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("idValue", id)
                intent.putExtra("pwValue", pw)
                intent.putExtra("nameValue", name)
                intent.putExtra("mbtiValue", mbti)
                startActivity(intent)
            } else {
                makeToast("로그인에 실패했습니다.")
                Log.d("LoginActivity",
                    "로그인 실패 - 사용자 입력 ID: ${binding.idEdit.text}, " +
                            "사용자 입력 PW: ${binding.pwEdit.text}, " +
                            "저장된 ID: $id, " +
                            "저장된 PW: $pw")
            }
        }
    }*/

    fun makeSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    fun makeToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun login() {
        val id = binding.idEdit.text.toString()
        val password = binding.pwEdit.text.toString()

        binding.loginButton.setOnClickListener{
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
                                "로그인이 성공하였고 유저의 ID는 $userId 입니둥",
                                Toast.LENGTH_SHORT,
                            ).show()

                            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                            startActivity(intent)
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

