@file:Suppress("DEPRECATION", "UNUSED_EXPRESSION")

package com.example.petpatrol

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton

class AdoptarActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private val bottomNavigation by lazy { findViewById<BottomNavigationView>(R.id.bottom_navigation) }
    private var userId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adoptar)
        userId = intent.getIntExtra("USER_ID", 0)
        Log.d("AdoptarActivity", "User ID obtenido: $userId")

        drawerLayout = findViewById(R.id.drawer_layout) // Asegúrate de tener un ID correspondiente en tu layout
        setupFilterButton()
        loadFragment(savedInstanceState)
        setupButtons()
        setupBottomNavigation()


    }

    private fun startNewActivity(activity: Class<*>) {
        val intent = Intent(this, activity)
        intent.putExtra("USER_ID", userId) // Pasar userId a la siguiente actividad si es necesario
        startActivity(intent)
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
                R.id.profile -> {
                    val intentProfile = Intent(this, ProfileActivity::class.java).apply {
                        putExtra("USER_ID", userId)
                    }
                    startActivity(intentProfile)
                }
                R.id.adoptar -> Unit // No haces nada porque ya estás en AdoptarActivity.
                R.id.add -> {
                    val intentAdd = Intent(this, AddActivity::class.java).apply {
                        putExtra("USER_ID", userId) // Aquí pasas el userId a AddActivity.
                    }
                    startActivity(intentAdd)
                }
                R.id.alerta -> {
                    val intentAyudar = Intent(this, AyudarActivity::class.java).apply {
                        putExtra("USER_ID", userId) // Aquí pasas el userId a AyudarActivity.
                    }
                    startActivity(intentAyudar)
                }
                R.id.cruzar -> {
                    val intentCruzar = Intent(this, CruceActivity::class.java).apply {
                        putExtra("USER_ID", userId) // Aquí pasas el userId a CruceActivity.
                    }
                    startActivity(intentCruzar)
                }
            }
            true
        }
        bottomNavigation.selectedItemId = R.id.adoptar
    }


    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END)
        } else {
            // Mostrar un diálogo de confirmación antes de cerrar sesión
            showLogoutConfirmationDialog()
        }
    }


    private fun showLogoutConfirmationDialog() {
        val dialog = Dialog(this).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.dialog_logout_confirmation)
            window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            window?.setBackgroundDrawableResource(android.R.color.transparent)

            findViewById<Button>(R.id.button_confirm_logout).setOnClickListener {
                logout()
                dismiss()
            }
            findViewById<Button>(R.id.button_cancel_logout).setOnClickListener {
                dismiss()
            }
        }
        dialog.show()
    }

    private fun logout() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
