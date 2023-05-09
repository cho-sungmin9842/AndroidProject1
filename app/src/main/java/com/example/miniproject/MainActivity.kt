package com.example.miniproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.miniproject.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (Firebase.auth.currentUser != null) {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }
        binding.signin.setOnClickListener {
            MyBottomSheetDialog("signin").show(supportFragmentManager, "")
        }
        binding.signup.setOnClickListener {
            MyBottomSheetDialog("signup").show(supportFragmentManager, "")
        }
    }
}