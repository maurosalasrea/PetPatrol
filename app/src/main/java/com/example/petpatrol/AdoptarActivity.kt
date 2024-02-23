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


@Suppress("DEPRECATION")
class AdoptarActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout


    @SuppressLint("InflateParams", "MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adoptar)


        findViewById<ImageButton>(R.id.btnFiltro).setOnClickListener {
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.dialog_filter)
            dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent) // Hace el fondo del diálogo transparente para permitir esquinas redondeadas
            dialog.findViewById<Button>(R.id.button_apply_filter).setOnClickListener {
                // Implementa la lógica para aplicar el filtro aquí
                dialog.dismiss()
            }
            dialog.show()
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AdoptarFragment())
                .commit()
        }



        val logoImageView: ImageView = findViewById(R.id.logoImageView)
        logoImageView.setOnClickListener {
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
        }
        val btnAdoptar: MaterialButton = findViewById(R.id.btnAdoptar)
        btnAdoptar.setOnClickListener {
            // Iniciar MainActivity (aunque ya estamos en ella, es solo un ejemplo)
            val intent = Intent(this, AdoptarActivity::class.java)
            startActivity(intent)
        }

        val btnAyudar: MaterialButton = findViewById(R.id.btnAyudar)
        btnAyudar.setOnClickListener {
            // Iniciar AyudarActivity
            val intent = Intent(this, AyudarActivity::class.java)
            startActivity(intent)
        }

        val btnConquistar: MaterialButton = findViewById(R.id.btnConquistar)
        btnConquistar.setOnClickListener {
            // Iniciar CruceActivity
            val intent = Intent(this, CruceActivity::class.java)
            startActivity(intent)
        }

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.profile -> {
                    // Iniciar ProfileActivity
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.adoptar -> {
                    // Iniciar MainActivity
                    val intent = Intent(this, AdoptarActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.add -> {
                    // Iniciar AddActivity
                    val intent = Intent(this, AddActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.alerta -> {
                    // Iniciar AyudarActivity
                    val intent = Intent(this, AyudarActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.cruzar -> {
                    // Iniciar CruceActivity
                    val intent = Intent(this, CruceActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }

        }
        bottomNavigation.selectedItemId = R.id.adoptar
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END)
        } else {
            super.onBackPressed()
        }
    }
}
