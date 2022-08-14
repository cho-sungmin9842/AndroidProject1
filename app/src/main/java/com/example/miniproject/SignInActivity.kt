package com.example.miniproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.miniproject.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignInBinding
    lateinit var appbarc: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val nhf=supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        var topdest= setOf(R.id.homeFragment,R.id.quizFragment,R.id.searchFragment,R.id.shopFragment)
        appbarc= AppBarConfiguration(topdest)
        setupActionBarWithNavController(nhf.navController,appbarc)
        binding.bottomNavigationView.setupWithNavController(nhf.navController)
    }
    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.fragmentContainerView).navigateUp(appbarc) ||super.onSupportNavigateUp()
    }
}