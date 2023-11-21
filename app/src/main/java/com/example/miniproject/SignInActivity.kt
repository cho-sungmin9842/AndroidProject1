package com.example.miniproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.miniproject.databinding.ActivitySignInBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private val homeFragment = HomeFragment()
    private val quizFragment = QuizFragment()
    private val searchFragment = SearchFragment()
    private val shopFragment = ShopFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.frameLayout) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
//        binding.bottomNavigationView.setOnItemSelectedListener {
//            when (it.itemId) {
//                R.id.homeFragment -> {
//                    replaceFragment(homeFragment)
//                    return@setOnItemSelectedListener true
//                }
//                R.id.quizFragment -> {
//                    replaceFragment(quizFragment)
//                    return@setOnItemSelectedListener true
//                }
//                R.id.searchFragment -> {
//                    replaceFragment(searchFragment)
//                    return@setOnItemSelectedListener true
//                }
//                R.id.shopFragment -> {
//                    replaceFragment(shopFragment)
//                    return@setOnItemSelectedListener true
//                }
//                else -> {
//                    return@setOnItemSelectedListener false
//                }
//            }
//        }
//        replaceFragment(homeFragment)


    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, fragment)
            commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings -> {
                Firebase.auth.signOut()
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}