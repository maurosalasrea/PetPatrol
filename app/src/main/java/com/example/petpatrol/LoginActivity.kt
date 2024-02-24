package com.example.petpatrol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class LoginActivity : AppCompatActivity() {

    private val emailEditText by lazy { findViewById<EditText>(R.id.emailEditText) }
    private val passwordEditText by lazy { findViewById<EditText>(R.id.passwordEditText) }
    private val loginButton by lazy { findViewById<Button>(R.id.loginButton) }
    private val forgotPasswordTextView by lazy { findViewById<TextView>(R.id.forgotPasswordTextView) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setupUI()
    }

    private fun setupUI() {
        loginButton.setOnClickListener { performLogin() }
        forgotPasswordTextView.setOnClickListener { navigateToForgotPassword() }
    }

    private fun performLogin() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (credentialsAreValid(email, password)) {
            navigateToAdoptarActivity()
        } else {
            // Mostrar un error o hacer alguna otra acción
        }
    }

    private fun navigateToForgotPassword() {
        startActivity(Intent(this, ForgotPassword::class.java))
    }

    private fun navigateToAdoptarActivity() {
        Intent(this, AdoptarActivity::class.java).also { intent ->
            startActivity(intent)
            finish()
        }
    }

    private fun credentialsAreValid(email: String, password: String): Boolean {
        // Implementa tu lógica de validación de credenciales aquí
        // Por ahora, siempre devuelve verdadero para simplificar
        return true
    }
}
