package com.example.petpatrol

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageButton
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton

class CruceActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ayudar)

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

    private fun setupButtons() {
        findViewById<MaterialButton>(R.id.btnAdoptar).setOnClickListener {
            navigateToActivity(AdoptarActivity::class.java)
        }

        findViewById<MaterialButton>(R.id.btnAyudar).setOnClickListener {
            navigateToActivity(AyudarActivity::class.java)
        }
    }

    private fun setupBottomNavigation() {
        findViewById<BottomNavigationView>(R.id.bottom_navigation).apply {
            setOnNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.profile -> navigateToActivity(ProfileActivity::class.java)
                    R.id.adoptar -> navigateToActivity(AdoptarActivity::class.java)
                    R.id.add -> navigateToActivity(AddActivity::class.java)
                    R.id.alerta -> navigateToActivity(AyudarActivity::class.java)
                    R.id.cruzar -> {}
                }
                true
            }
            selectedItemId = R.id.cruzar
        }
    }

    private fun navigateToActivity(activityClass: Class<*>) {
        if (this::class.java != activityClass) {
            startActivity(Intent(this, activityClass))
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END)
        } else {
            super.onBackPressed()
        }
    }
}
