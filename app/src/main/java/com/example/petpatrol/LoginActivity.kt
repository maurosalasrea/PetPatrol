package com.example.petpatrol

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.petpatrol.api.LoginData
import com.example.petpatrol.api.RetrofitClient
import com.example.petpatrol.api.UserService
import com.example.petpatrol.api.Users
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val emailEditText = findViewById<TextInputEditText>(R.id.emailEditText)
        val passwordEditText = findViewById<TextInputEditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val forgotPasswordTextView = findViewById<TextView>(R.id.forgotPasswordTextView)

        loginButton.setOnClickListener {
            performLogin(emailEditText.text.toString().trim(), passwordEditText.text.toString().trim())
        }

        forgotPasswordTextView.setOnClickListener {
            navigateToForgotPassword()
        }
    }

    private fun performLogin(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor ingresa tu email y contraseña.", Toast.LENGTH_LONG).show()
            return
        }

        val userService: UserService = RetrofitClient.createService(UserService::class.java)
        userService.postLogin(LoginData(0, email, password)).enqueue(object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@LoginActivity, "Inicio de sesión exitoso.", Toast.LENGTH_SHORT).show()
                    navigateToAdoptarActivity()
                } else {
                    Toast.makeText(this@LoginActivity, "Credenciales incorrectas.", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Error en la conexión: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun navigateToForgotPassword() {
        // Implementa la navegación a la actividad de olvido de contraseña
    }

    private fun navigateToAdoptarActivity() {
        val intent = Intent(this, AdoptarActivity::class.java)
        startActivity(intent)
        finish()
    }
}
