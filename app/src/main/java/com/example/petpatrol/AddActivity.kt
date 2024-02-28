package com.example.petpatrol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
import com.google.android.material.bottomnavigation.BottomNavigationView

@Suppress("DEPRECATION")
class AddActivity : AppCompatActivity() {



    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }

    private val bottomNavigation by lazy { findViewById<BottomNavigationView>(R.id.bottom_navigation) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val verAnimalesButton: Button = findViewById(R.id.todosButton)
        verAnimalesButton.setOnClickListener {
            // Inicia la actividad VerAnimalesActivity
            val intent = Intent(this, TodosActivity::class.java)
            startActivity(intent)
        }

        val spinner1: Spinner = findViewById(R.id.spinner1)
        val options1 = arrayOf("Animal", "Gato", "Perro", "Otros")
        val spinner2: Spinner = findViewById(R.id.spinner2)
        val options2 = arrayOf("Edad", "Cachorro", "Adulto", "Mayor")
        val spinner3: Spinner = findViewById(R.id.spinner3)
        val options3 = arrayOf("Tamaño", "Pequeño", "Mediano", "Grande")
        val spinner4: Spinner = findViewById(R.id.spinner4)
        val options4 = arrayOf("Sexo", "Macho", "Hembra")
        val spinner5: Spinner = findViewById(R.id.spinner5)
        val options5 = arrayOf("Distrito", "Iquitos", "Punchana", "Belen", "San Juan")
        val adapter1 = ArrayAdapter(this, android.R.layout.simple_spinner_item, options1)
        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, options2)
        val adapter3 = ArrayAdapter(this, android.R.layout.simple_spinner_item, options3)
        val adapter4 = ArrayAdapter(this, android.R.layout.simple_spinner_item, options4)
        val adapter5 = ArrayAdapter(this, android.R.layout.simple_spinner_item, options5)
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner1.adapter = adapter1
        spinner2.adapter = adapter2
        spinner3.adapter = adapter3
        spinner4.adapter = adapter4
        spinner5.adapter = adapter5

        setupBottomNavigation()

        val buttonInsertImage: ImageButton = findViewById(R.id.buttonInsertImage)
        buttonInsertImage.setOnClickListener {
            openImageChooser()
        }

    }

    private fun openImageChooser() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            val imageUri = data.data
            // Aquí puedes usar imageUri con un ImageView o manejarlo como necesites
        }
    }

    private fun setupBottomNavigation() {
        bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.profile -> startNewActivity(ProfileActivity::class.java)
                R.id.adoptar -> startNewActivity(AdoptarActivity::class.java)
                R.id.add -> {}
                R.id.alerta -> startNewActivity(AyudarActivity::class.java)
                R.id.cruzar -> startNewActivity(CruceActivity::class.java)
            }
            true
        }
        bottomNavigation.selectedItemId = R.id.add
    }

    private fun startNewActivity(activity: Class<*>) {
        val intent = Intent(this, activity)
        startActivity(intent)
    }
}
