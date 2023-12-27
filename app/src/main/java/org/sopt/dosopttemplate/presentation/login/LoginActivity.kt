package org.sopt.dosopttemplate.presentation.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.sopt.dosopttemplate.data.model.LoginState
import org.sopt.dosopttemplate.databinding.ActivityLoginBinding
import org.sopt.dosopttemplate.presentation.home.HomeActivity
import org.sopt.dosopttemplate.presentation.signup.SignUpActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSignBtnClickListener()
        observeLoginResult()

        binding.btLogin.setOnClickListener {
            val id = binding.etIdEdit.text.toString()
            val password = binding.etPwEdit.text.toString()
            viewModel.login(id, password)
        }
    }

    private fun initSignBtnClickListener() {
        binding.btSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observeLoginResult() {
        lifecycleScope.launch {
            viewModel.loginState.collect { loginState ->
                when (loginState) {
                    is LoginState.Success -> {
                        Toast.makeText(this@LoginActivity, "로그인 성공", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                    }

                    is LoginState.Error -> {
                        Toast.makeText(this@LoginActivity, "로그인 실패", Toast.LENGTH_SHORT).show()
                    }

                    is LoginState.Loading -> {
                        Toast.makeText(this@LoginActivity, "로그인 하는 중", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
