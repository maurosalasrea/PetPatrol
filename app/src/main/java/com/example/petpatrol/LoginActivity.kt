package com.example.petpatrol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var forgotPasswordTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        forgotPasswordTextView = findViewById(R.id.forgotPasswordTextView)

        loginButton.setOnClickListener {
            performLogin()
        }

        // Configura el OnClickListener para forgotPasswordTextView
        forgotPasswordTextView.setOnClickListener {
            // Crea un Intent para iniciar ForgotPasswordActivity
            val forgotPasswordIntent = Intent(this, ForgotPassword::class.java)
            startActivity(forgotPasswordIntent)
        }
    }

    private fun performLogin() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (credentialsAreValid(email, password)) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            // Mostrar un error o hacer alguna otra acción
        }
    }

    private fun credentialsAreValid(email: String, password: String): Boolean {
        // Implementa tu lógica de validación de credenciales
        return true
    }



}
