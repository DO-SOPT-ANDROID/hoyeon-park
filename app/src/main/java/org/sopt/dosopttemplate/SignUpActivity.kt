package org.sopt.dosopttemplate

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import org.sopt.dosopttemplate.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var startLoginActivityForResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSignBtnClickListener()

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

    fun initSignBtnClickListener() {
        binding.signButton.setOnClickListener {
            if (binding.idEdit.text.isNullOrBlank() || binding.pwEdit.text.isNullOrBlank() || binding.nameEdit.text.isNullOrBlank() || binding.mbtiEdit.text.isNullOrBlank()) {
                makeSnackbar("모든 정보를 입력해 주세요.")
            } else if (binding.idEdit.text.toString().length in 6..10 && binding.pwEdit.text.toString().length >= 8 && binding.pwEdit.text.toString().length <= 12 && binding.nameEdit.text.toString().trim().isNotEmpty() && binding.mbtiEdit.text.toString().length == 4) {
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("idValue", binding.idEdit.text.toString())
                intent.putExtra("pwValue", binding.pwEdit.text.toString())
                intent.putExtra("nameValue", binding.nameEdit.text.toString())
                intent.putExtra("mbtiValue", binding.mbtiEdit.text.toString())

                startLoginActivityForResult.launch(intent)
            } else {
                makeSnackbar("입력 정보를 다시 확인해주세요.")
            }
        }
    }

    fun makeSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}