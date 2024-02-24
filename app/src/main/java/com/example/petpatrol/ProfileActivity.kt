package com.example.petpatrol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        findViewById<BottomNavigationView>(R.id.bottom_navigation).apply {
            setOnNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.profile -> {}
                    R.id.adoptar -> navigateToActivity(AdoptarActivity::class.java)
                    R.id.add -> navigateToActivity(AddActivity::class.java)
                    R.id.alerta -> navigateToActivity(AyudarActivity::class.java)
                    R.id.cruzar -> navigateToActivity(CruceActivity::class.java)
                }
                true
            }
            selectedItemId = R.id.profile
        }
    }

    private fun navigateToActivity(activityClass: Class<*>) {
        if (this::class.java != activityClass) {
            startActivity(Intent(this, activityClass))
        }
    }

}