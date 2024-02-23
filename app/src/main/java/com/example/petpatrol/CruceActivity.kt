package com.example.petpatrol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.button.MaterialButton

class CruceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cruce)

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

    }
}