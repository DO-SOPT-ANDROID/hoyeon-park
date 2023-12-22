package org.sopt.dosopttemplate.presentation.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
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
        observeViewModel()

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

    private fun observeViewModel() {
        viewModel.loginResult.observe(
            (this),
            Observer { result ->
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
                if (result.contains("성공")) {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                }
            },
        )
    }

    private fun makeSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}
