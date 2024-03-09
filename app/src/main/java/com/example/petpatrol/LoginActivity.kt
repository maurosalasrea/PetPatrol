package com.example.petpatrol

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
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
        val registerButton = findViewById<Button>(R.id.registerButton)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            performLogin(email, password)
        }
        registerButton.setOnClickListener {
            navigateToRegisterActivity()
        }
    }

    private fun performLogin(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor ingresa tu email y contraseña.", Toast.LENGTH_LONG).show()
            return
        }

        val userService: UserService = RetrofitClient.createService(UserService::class.java)
        userService.postLogin(LoginData(email_address = email, password = password)).enqueue(object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                if (response.isSuccessful && response.body() != null) {
                    val userId = response.body()?.user?.user_id ?: 0

                    Log.d("USER", "$userId")
                    // Guardar userId en SharedPreferences (Opcional)
                    val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
                    val myEdit = sharedPreferences.edit()
                    myEdit.putInt("user_id", userId)
                    myEdit.apply()

                    // Pasar userId a AdoptarActivity
                    val intent = Intent(this@LoginActivity, AdoptarActivity::class.java).apply {
                        putExtra("USER_ID", userId)
                    }
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, "Credenciales incorrectas.", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Error en la conexión: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun navigateToRegisterActivity() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

}
