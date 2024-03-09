package com.example.petpatrol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.petpatrol.api.RetrofitClient
import com.example.petpatrol.api.UserData
import com.example.petpatrol.api.UserService
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileActivity : AppCompatActivity() {
    private lateinit var emailEditText: TextInputEditText
    private lateinit var nameEditText: TextInputEditText
    private lateinit var lastNameEditText: TextInputEditText
    private lateinit var phoneEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        emailEditText = findViewById(R.id.emailEditText)
        nameEditText = findViewById(R.id.nameEditText)
        lastNameEditText = findViewById(R.id.lastNameEditText)
        phoneEditText = findViewById(R.id.phoneEditText)
        passwordEditText = findViewById(R.id.passwordEditText)

        val userEmail = "Obtener el correo electrónico del usuario actual"

        // Cargar los datos del usuario al iniciar
        loadUserData(userEmail)

        val saveChangesButton: Button = findViewById(R.id.saveChangesButton)

        saveChangesButton.setOnClickListener {
            val userData = com.example.petpatrol.api.UserData(
                email = emailEditText.text.toString(),
                firstName = nameEditText.text.toString(),
                lastName = lastNameEditText.text.toString(),
                phoneNumber = phoneEditText.text.toString(),
                password = passwordEditText.text.toString()
            )
            updateUser(userData)
        }

        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        findViewById<BottomNavigationView>(R.id.bottom_navigation).apply {
            setOnNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.profile -> {}
                    R.id.adoptar -> navigateToActivity(AdoptarActivity::class.java)
                    R.id.add -> navigateToActivity(AddActivity::class.java)
                    R.id.alerta -> navigateToActivity(AyudarActivity::class.java)
                    R.id.cruzar -> navigateToActivity(CruceActivity::class.java)
                }
                true
            }
            selectedItemId = R.id.profile
        }
    }


    private fun loadUserData(email: String) {
        val service = RetrofitClient.createService(UserService::class.java)
        service.getUserByEmail(email).enqueue(object : Callback<UserData> {
            override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                if (response.isSuccessful) {
                    val userData = response.body()
                    // Actualiza los campos de texto con los datos del usuario
                    Log.d("ProfileActivity", "Datos del usuario cargados correctamente")
                } else {
                    Toast.makeText(
                        this@ProfileActivity,
                        "Error al cargar datos del usuario",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<UserData>, t: Throwable) {
                Toast.makeText(
                    this@ProfileActivity,
                    "Fallo al cargar datos: ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun updateUser(userData: UserData) {
        val service = RetrofitClient.createService(UserService::class.java)
        service.updateUser(userData).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@ProfileActivity, "Datos actualizados correctamente", Toast.LENGTH_LONG).show()
                    Log.d("ProfileActivity", "Datos del usuario actualizados correctamente")
                } else {
                    Toast.makeText(this@ProfileActivity, "Error al actualizar datos", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@ProfileActivity, "Fallo al actualizar datos: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }



    private fun navigateToActivity(activityClass: Class<*>) {
        if (this::class.java != activityClass) {
            startActivity(Intent(this, activityClass))
        }
    }

}