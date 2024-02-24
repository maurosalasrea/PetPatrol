package com.example.petpatrol

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton


class AdoptarActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private val bottomNavigation by lazy { findViewById<BottomNavigationView>(R.id.bottom_navigation) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adoptar)

        setupFilterButton()
        loadFragment(savedInstanceState)
        setupButtons()
        setupBottomNavigation()
    }

    private fun setupFilterButton() {
        findViewById<ImageButton>(R.id.btnFiltro).setOnClickListener {
            showDialog()
        }
    }
    private fun setupButtons() {
        findViewById<MaterialButton>(R.id.btnAyudar).setOnClickListener {
            navigateToActivity(AyudarActivity::class.java)
        }

        findViewById<MaterialButton>(R.id.btnConquistar).setOnClickListener {
            navigateToActivity(CruceActivity::class.java)
        }
    }
    private fun navigateToActivity(activityClass: Class<*>) {
        if (this::class.java != activityClass) {
            startActivity(Intent(this, activityClass))
        }
    }
    private fun showDialog() {
        val dialog = Dialog(this).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.dialog_filter)
            window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
            findViewById<Button>(R.id.button_apply_filter).setOnClickListener {
                dismiss()
            }
        }
        dialog.show()
    }

    private fun loadFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AdoptarFragment())
                .commit()
        }
    }

    private fun setupBottomNavigation() {
        bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.profile -> startNewActivity(ProfileActivity::class.java)
                R.id.adoptar -> Unit
                R.id.add -> startNewActivity(AddActivity::class.java)
                R.id.alerta -> startNewActivity(AyudarActivity::class.java)
                R.id.cruzar -> startNewActivity(CruceActivity::class.java)
                else -> false
            }
            true
        }
        bottomNavigation.selectedItemId = R.id.adoptar
    }

    private fun startNewActivity(activity: Class<*>) {
        val intent = Intent(this, activity)
        startActivity(intent)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END)
        } else {
            super.onBackPressed()
        }
    }
}
