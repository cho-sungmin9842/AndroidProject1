package com.example.miniproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.miniproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.signin.setOnClickListener {
            MyBottomSheetDialog("signin").show(supportFragmentManager,"")
        }
        binding.signup.setOnClickListener {
            MyBottomSheetDialog("signup").show(supportFragmentManager,"")
        }
    }
    //추가할 코드
}