// RegisterActivity.kt
package com.example.petpatrol

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.petpatrol.api.RetrofitClient
import com.example.petpatrol.api.UserData
import com.example.petpatrol.api.UserService
import com.google.android.material.textfield.TextInputEditText
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val firstNameEditText = findViewById<TextInputEditText>(R.id.nameEditText)
        val lastNameEditText = findViewById<TextInputEditText>(R.id.lastNameEditText)
        val emailEditText = findViewById<TextInputEditText>(R.id.emailEditText)
        val phoneNumberEditText = findViewById<TextInputEditText>(R.id.phoneEditText) // Asegúrate de que este ID sea correcto
        val passwordEditText = findViewById<TextInputEditText>(R.id.passwordEditText)
        val registerButton = findViewById<Button>(R.id.registerButton) // Asegúrate de que este ID sea el correcto

        registerButton.setOnClickListener {
            val userData = UserData(
                email = emailEditText.text.toString(),
                firstName = firstNameEditText.text.toString(),
                lastName = lastNameEditText.text.toString(),
                phoneNumber = phoneNumberEditText.text.toString(),
                password = passwordEditText.text.toString()
            )

            val userService = RetrofitClient.createService(UserService::class.java)
            userService.registerUser(userData).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@RegisterActivity, "Registro exitoso", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@RegisterActivity, "Error en el registro", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(this@RegisterActivity, "Error en la conexión", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}
