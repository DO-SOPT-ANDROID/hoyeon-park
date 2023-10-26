package org.sopt.dosopttemplate

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.sopt.dosopttemplate.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
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