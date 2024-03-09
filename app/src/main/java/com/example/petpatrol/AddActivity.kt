package com.example.petpatrol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import com.example.petpatrol.api.Distrito
import com.example.petpatrol.api.EdadMascotas
import com.example.petpatrol.api.RetrofitClient
import com.example.petpatrol.api.TipoMascotas
import com.example.petpatrol.api.TipoPost
import com.example.petpatrol.api.UserService
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("DEPRECATION")
class AddActivity : AppCompatActivity() {



    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }

    private val bottomNavigation by lazy { findViewById<BottomNavigationView>(R.id.bottom_navigation) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        Log.d("TestLog", "onCreate executed")

        val verAnimalesButton: Button = findViewById(R.id.todosButton)
        verAnimalesButton.setOnClickListener {
            // Inicia la actividad VerAnimalesActivity
            val intent = Intent(this, TodosActivity::class.java)
            startActivity(intent)
        }

        fetchDistritos()
        fetchEdadMascotas()
        fetchTipoMascotas()
        fetchTipoPost()

        val spinner3: Spinner = findViewById(R.id.spinner3)
        val options3 = arrayOf("Tamaño", "Pequeño", "Mediano", "Grande")
        val spinner4: Spinner = findViewById(R.id.spinner4)
        val options4 = arrayOf("Sexo", "Macho", "Hembra")
        val adapter3 = ArrayAdapter(this, android.R.layout.simple_spinner_item, options3)
        val adapter4 = ArrayAdapter(this, android.R.layout.simple_spinner_item, options4)
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner3.adapter = adapter3
        spinner4.adapter = adapter4

        setupBottomNavigation()

        val buttonInsertImage: ImageButton = findViewById(R.id.buttonInsertImage)
        buttonInsertImage.setOnClickListener {
            openImageChooser()
        }


    }

    private fun fetchTipoPost() {
        val service = RetrofitClient.createService(UserService::class.java)
        service.getTipoPost().enqueue(object : Callback<List<TipoPost>> {
            override fun onResponse(call: Call<List<TipoPost>>, response: Response<List<TipoPost>>) {
                if (response.isSuccessful) {
                    val tiposPost = response.body() ?: emptyList()
                    val descripcionesTipoPost = tiposPost.map { it.descripcion }
                    val spinner6: Spinner = findViewById(R.id.spinner6)
                    val adapter = ArrayAdapter(this@AddActivity, android.R.layout.simple_spinner_item, descripcionesTipoPost)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner6.adapter = adapter

                    // Listener para cuando se selecciona un item del spinner
                    spinner6.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {
                            val selectedTipoPost = adapterView.getItemAtPosition(position).toString()
                            Log.d("TipoPostSelection", "Seleccionado: $selectedTipoPost")
                        }

                        override fun onNothingSelected(adapterView: AdapterView<*>) {
                            Log.d("TipoPostSelection", "No se seleccionó ningún tipo de post")
                        }
                    }
                } else {
                    Toast.makeText(this@AddActivity, "Respuesta no exitosa: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<TipoPost>>, t: Throwable) {
                Toast.makeText(this@AddActivity, "Fallo en la petición: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun fetchTipoMascotas() {
        val service = RetrofitClient.createService(UserService::class.java)
        service.getTipoMascotas().enqueue(object : Callback<List<TipoMascotas>> {
            override fun onResponse(call: Call<List<TipoMascotas>>, response: Response<List<TipoMascotas>>) {
                if (response.isSuccessful) {
                    val tipos = response.body() ?: emptyList()
                    val nombresTipo = tipos.map { it.NombreTipo }
                    val spinner1: Spinner = findViewById(R.id.spinner1)
                    val adapter = ArrayAdapter(this@AddActivity, android.R.layout.simple_spinner_item, nombresTipo)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner1.adapter = adapter

                    // Listener para cuando se selecciona un item del spinner de tipo
                    spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {
                            val selectedTipo = adapterView.getItemAtPosition(position).toString()
                            Log.d("TipoMascotaSelection", "Seleccionado: $selectedTipo")
                        }

                        override fun onNothingSelected(adapterView: AdapterView<*>) {
                            Log.d("TipoMascotaSelection", "No se seleccionó ningún tipo de mascota")
                        }
                    }
                } else {
                    Toast.makeText(this@AddActivity, "Respuesta no exitosa: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<TipoMascotas>>, t: Throwable) {
                Toast.makeText(this@AddActivity, "Fallo en la petición: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun fetchEdadMascotas() {
        val service = RetrofitClient.createService(UserService::class.java)
        service.getEdadMascotas().enqueue(object : Callback<List<EdadMascotas>> {
            override fun onResponse(call: Call<List<EdadMascotas>>, response: Response<List<EdadMascotas>>) {
                if (response.isSuccessful) {
                    val edades = response.body() ?: emptyList()
                    val descripcionesEdad = edades.map { it.edadDescription }
                    val spinner2: Spinner = findViewById(R.id.spinner2)
                    val adapter = ArrayAdapter(this@AddActivity, android.R.layout.simple_spinner_item, descripcionesEdad)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner2.adapter = adapter

                    // Listener para cuando se selecciona un item del spinner de edad
                    spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {
                            val selectedEdad = adapterView.getItemAtPosition(position).toString()
                            Log.d("EdadMascotaSelection", "Seleccionado: $selectedEdad")
                        }

                        override fun onNothingSelected(adapterView: AdapterView<*>) {
                            Log.d("EdadMascotaSelection", "No se seleccionó ninguna edad")
                        }
                    }
                } else {
                    Toast.makeText(this@AddActivity, "Respuesta no exitosa: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<EdadMascotas>>, t: Throwable) {
                Toast.makeText(this@AddActivity, "Fallo en la petición: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun fetchDistritos() {
        val service = RetrofitClient.createService(UserService::class.java)
        service.getDistritos().enqueue(object : Callback<List<Distrito>> {
            override fun onResponse(call: Call<List<Distrito>>, response: Response<List<Distrito>>) {
                if (response.isSuccessful) {
                    val distritos = response.body() ?: emptyList()
                    val nombresDistrito = distritos.map { it.nombre }
                    val spinner5: Spinner = findViewById(R.id.spinner5)
                    val adapter = ArrayAdapter(this@AddActivity, android.R.layout.simple_spinner_item, nombresDistrito)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner5.adapter = adapter

                    // Listener para cuando se selecciona un item del spinner
                    spinner5.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {
                            val selectedDistrito = adapterView.getItemAtPosition(position).toString()
                            Log.d("DistritoSelection", "Seleccionado: $selectedDistrito")
                        }

                        override fun onNothingSelected(adapterView: AdapterView<*>) {
                            Log.d("DistritoSelection", "No se seleccionó ningún distrito")
                        }
                    }
                } else {
                    Toast.makeText(this@AddActivity, "Respuesta no exitosa: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Distrito>>, t: Throwable) {
                Toast.makeText(this@AddActivity, "Fallo en la petición: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
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
