package com.example.petpatrol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.petpatrol.api.RetrofitClient
import com.example.petpatrol.api.SharedPreferencesHelper
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

        // Cambiado para usar SharedPreferencesHelper para obtener el user_id
        val userId = intent.getIntExtra("USER_ID", 0) // Asegúrate de manejar el caso en que el ID sea 0 o default
        Log.d("ProfileActivity", "User ID usado para cargar datos: $userId")

        if (userId != 0) {
            loadUserData(userId)
        } else {
            Toast.makeText(this, "No se encontró ID de usuario.", Toast.LENGTH_LONG).show()
        }
        val saveChangesButton: Button = findViewById(R.id.saveChangesButton)
        saveChangesButton.setOnClickListener {
            val userData = UserData(
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

    private fun loadUserData(userId: Int) {
        val service = RetrofitClient.createService(UserService::class.java)
        service.getUserById(userId).enqueue(object : Callback<UserData> {
            override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        emailEditText.setText(it.email)
                        nameEditText.setText(it.firstName)
                        lastNameEditText.setText(it.lastName)
                        phoneEditText.setText(it.phoneNumber)
                        // Considerar seguridad al mostrar la contraseña
                    }
                } else {
                    Toast.makeText(this@ProfileActivity, "Error al cargar los datos del usuario.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserData>, t: Throwable) {
                Toast.makeText(this@ProfileActivity, "Fallo al cargar los datos: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun updateUser(userData: UserData) {
        // Verificación simple de datos no vacíos
        if (userData.email.isEmpty() || userData.firstName.isEmpty() || userData.lastName.isEmpty() || userData.phoneNumber.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_LONG).show()
            return
        }

//        val userId = SharedPreferencesHelper(this).userId
//        Log.d("ProfileActivity", "Intentando actualizar datos para el User ID: $userId")

        val userId = intent.getIntExtra("USER_ID", 0) // Asegúrate de manejar el caso en que el ID sea 0 o default
        Log.d("ProfileActivity", "User ID usado para cargar datos: $userId")

        if (userId == 0) {
            Toast.makeText(this, "Error: ID de usuario no disponible.", Toast.LENGTH_LONG).show()
            return
        }

        val service = RetrofitClient.createService(UserService::class.java)
        service.updateUser(userId, userData).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@ProfileActivity, "Datos actualizados correctamente", Toast.LENGTH_LONG).show()
                    // Opcional: Recargar los datos del usuario para confirmar la actualización
                    loadUserData(userId)
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

}