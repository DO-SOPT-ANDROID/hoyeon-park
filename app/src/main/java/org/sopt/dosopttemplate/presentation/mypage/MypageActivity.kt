package org.sopt.dosopttemplate.presentation.mypage

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.sopt.dosopttemplate.databinding.ActivityMypageBinding

class MypageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMypageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMypageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra("idValue") && intent.hasExtra("pwValue") && intent.hasExtra("nameValue") && intent.hasExtra(
                "mbtiValue"
            )
        ) {
            binding.idValue.text = intent.getStringExtra("idValue")
            binding.nameValue.text = intent.getStringExtra("nameValue")
            binding.mbtiValue.text = intent.getStringExtra("mbtiValue")
        } else {
            makeToast("전달되지 않은 값이 있습니다.")
        }
    }

    fun makeToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}