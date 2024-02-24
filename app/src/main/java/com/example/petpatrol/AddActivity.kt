package com.example.petpatrol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.android.material.bottomnavigation.BottomNavigationView

@Suppress("DEPRECATION")
class AddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val spinner1: Spinner = findViewById(R.id.spinner1)
        // Suponiendo que tienes un array de strings con las opciones.
        val options = arrayOf("1", "2", "3")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner1.adapter = adapter


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
    }
}