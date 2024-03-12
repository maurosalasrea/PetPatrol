package com.example.petpatrol

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast

import com.example.petpatrol.api.Distrito
import com.example.petpatrol.api.EdadMascotas
import com.example.petpatrol.api.RetrofitClient
import com.example.petpatrol.api.SexoMascota
import com.example.petpatrol.api.SizeMascota
import com.example.petpatrol.api.TipoMascotas
import com.example.petpatrol.api.TipoPost
import com.example.petpatrol.api.UserService
import com.google.android.material.bottomnavigation.BottomNavigationView
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

@Suppress("DEPRECATION")
class AddActivity : AppCompatActivity() {
    private var imageUri: Uri? = null
    private var userId: Int = 0

    private lateinit var nameEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var imageView: ImageView
    private lateinit var spinnerTipoPost: Spinner
    private lateinit var spinnerTipoMascota: Spinner
    private lateinit var spinnerEdadMascota: Spinner
    private lateinit var spinnerSizeMascota: Spinner
    private lateinit var spinnerSexoMascota: Spinner
    private lateinit var spinnerDistrito: Spinner
    private val bottomNavigation by lazy { findViewById<BottomNavigationView>(R.id.bottom_navigation) }

    private var distritos: List<Distrito> = emptyList()
    private var selectedDistritoId: Int = -1

    private var edadesMascotas: List<EdadMascotas> = emptyList()
    private var selectedEdadMascotaId: Int = -1

    private var sexosMascotas: List<SexoMascota> = emptyList()
    private var selectedSexoMascotaId: Int = -1

    private var tamanosMascotas: List<SizeMascota> = emptyList()
    private var selectedSizeMascotaId: Int = -1

    private var tiposMascotas: List<TipoMascotas> = emptyList()
    private var selectedTipoMascotaId: Int = -1

    private var tiposPost: List<TipoPost> = emptyList()
    private var selectedTipoPostId: Int = -1

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
        private const val STORAGE_PERMISSION_CODE = 1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        userId = intent.getIntExtra("USER_ID", 0)
        Log.d("AddActivity", "User ID recibido: $userId")

        initUI()
        setupListeners()
    }

    private fun initUI() {
        imageView = findViewById(R.id.selectedImageView)
        nameEditText = findViewById(R.id.nameEditText)
        descriptionEditText = findViewById(R.id.descriptionEditText)
        spinnerTipoPost = findViewById(R.id.spinner6)
        spinnerTipoMascota = findViewById(R.id.spinner1)
        spinnerEdadMascota = findViewById(R.id.spinner2)
        spinnerSizeMascota = findViewById(R.id.spinner3)
        spinnerSexoMascota = findViewById(R.id.spinner4)
        spinnerDistrito = findViewById(R.id.spinner5)

        fetchTipoPost()
        fetchTipoMascotas()
        fetchEdadMascotas()
        fetchSizeMascotas()
        fetchSexoMascotas()
        fetchDistritos()

        setupBottomNavigation()
    }

    private fun setupListeners() {
        findViewById<ImageButton>(R.id.buttonInsertImage).setOnClickListener {
            if (checkAndRequestPermissions()) {
                openImageChooser()
            }
        }

        findViewById<Button>(R.id.loginButton).setOnClickListener {
            uploadDataAndImage()
        }
    }

    private fun openImageChooser() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.data
            imageView.setImageURI(imageUri)
        }
    }

    private fun getPath(context: Context, uri: Uri?): String? {
        uri ?: return null
        if ("content".equals(uri.scheme, ignoreCase = true)) {
            val projection = arrayOf(MediaStore.Images.Media.DATA)
            context.contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
                val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                if (cursor.moveToFirst()) {
                    return cursor.getString(columnIndex)
                }
            }
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path
        }
        return null
    }

    private fun uploadDataAndImage() {
        val filePath = getPath(this, imageUri)
        if (filePath == null) {
            Toast.makeText(this, "No se pudo obtener la ruta de la imagen.", Toast.LENGTH_SHORT).show()
            return
        }

        val file = File(filePath)
        val requestBody = RequestBody.create("image/jpeg".toMediaTypeOrNull(), file)
        val imagePart = MultipartBody.Part.createFormData("imagen", file.name, requestBody)

        if (selectedDistritoId == -1 || selectedEdadMascotaId == -1 || selectedSexoMascotaId == -1 || selectedSizeMascotaId == -1 || selectedTipoMascotaId == -1 || selectedTipoPostId == -1) {
            Toast.makeText(this, "Por favor, asegúrate de seleccionar todas las opciones requeridas.", Toast.LENGTH_SHORT).show()
            return
        }

        // Convertir el resto de tus datos a RequestBody
        val name = RequestBody.create("text/plain".toMediaTypeOrNull(), nameEditText.text.toString())
        val description = RequestBody.create("text/plain".toMediaTypeOrNull(), descriptionEditText.text.toString())
        val distrito = RequestBody.create("text/plain".toMediaTypeOrNull(), selectedDistritoId.toString())
        val edad = RequestBody.create("text/plain".toMediaTypeOrNull(), selectedEdadMascotaId.toString())
        val sexo = RequestBody.create("text/plain".toMediaTypeOrNull(), selectedSexoMascotaId.toString())
        val size = RequestBody.create("text/plain".toMediaTypeOrNull(), selectedSizeMascotaId.toString())
        val tipo = RequestBody.create("text/plain".toMediaTypeOrNull(), selectedTipoMascotaId.toString())
        val tipoPost = RequestBody.create("text/plain".toMediaTypeOrNull(), selectedTipoPostId.toString())
        // Asegúrate de tener el userId disponible como variable o de otra fuente
        val userIdRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), userId.toString())

        Log.d("AddActivity", "ID de distrito seleccionado: $distrito")
        // Realizar la llamada a tu API
        val service = RetrofitClient.createService(UserService::class.java)
        val call = service.crearMascotaYPost(
            name,
            description,
            distrito,
            edad,
            sexo,
            size,
            tipo,
            userIdRequestBody,
            tipoPost,
            imagePart
        )

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@AddActivity, "Mascota y post creados exitosamente.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@AddActivity, "Error en la creación: ${response.message()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@AddActivity, "Fallo en la creación: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun checkAndRequestPermissions(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.READ_MEDIA_IMAGES), STORAGE_PERMISSION_CODE)
                return false
            }
        } else {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE)
                return false
            }
        }
        return true
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            STORAGE_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permiso concedido, puedes continuar
                    openImageChooser()
                } else {
                    // Permiso denegado
                    Toast.makeText(this, "Permiso denegado.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun fetchSizeMascotas() {
        val service = RetrofitClient.createService(UserService::class.java)
        service.getSizeMascotas().enqueue(object : Callback<List<SizeMascota>> {
            override fun onResponse(call: Call<List<SizeMascota>>, response: Response<List<SizeMascota>>) {
                if (response.isSuccessful) {
                    tamanosMascotas = response.body() ?: emptyList()
                    val descripcionesSize = tamanosMascotas.map { it.descripcion }
                    val spinnerSize: Spinner = findViewById(R.id.spinner3)
                    val adapter = ArrayAdapter(this@AddActivity, android.R.layout.simple_spinner_item, descripcionesSize)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerSize.adapter = adapter

                    spinnerSize.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {
                            selectedSizeMascotaId = tamanosMascotas[position].idSize
                            Log.d("SizeMascotaSelection", "ID de tamaño seleccionado: $selectedSizeMascotaId")
                        }

                        override fun onNothingSelected(adapterView: AdapterView<*>) {}
                    }
                } else {
                    Toast.makeText(this@AddActivity, "Respuesta no exitosa: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<SizeMascota>>, t: Throwable) {
                Toast.makeText(this@AddActivity, "Fallo en la petición: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun fetchSexoMascotas() {
        val service = RetrofitClient.createService(UserService::class.java)
        service.getSexoMascotas().enqueue(object : Callback<List<SexoMascota>> {
            override fun onResponse(call: Call<List<SexoMascota>>, response: Response<List<SexoMascota>>) {
                if (response.isSuccessful) {
                    sexosMascotas = response.body() ?: emptyList()
                    val descripcionesSexo = sexosMascotas.map { it.descripcion }
                    val spinnerSexo: Spinner = findViewById(R.id.spinner4)
                    val adapter = ArrayAdapter(this@AddActivity, android.R.layout.simple_spinner_item, descripcionesSexo)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerSexo.adapter = adapter

                    spinnerSexo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {
                            selectedSexoMascotaId = sexosMascotas[position].idSexo
                            Log.d("SexoMascotaSelection", "ID de sexo seleccionado: $selectedSexoMascotaId")
                        }

                        override fun onNothingSelected(adapterView: AdapterView<*>) {}
                    }
                } else {
                    Toast.makeText(this@AddActivity, "Respuesta no exitosa: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<SexoMascota>>, t: Throwable) {
                Toast.makeText(this@AddActivity, "Fallo en la petición: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun fetchTipoPost() {
        val service = RetrofitClient.createService(UserService::class.java)
        service.getTipoPost().enqueue(object : Callback<List<TipoPost>> {
            override fun onResponse(call: Call<List<TipoPost>>, response: Response<List<TipoPost>>) {
                if (response.isSuccessful) {
                    tiposPost = response.body() ?: emptyList()
                    val descripcionesTipoPost = tiposPost.map { it.descripcion }
                    val spinnerTipoPost: Spinner = findViewById(R.id.spinner6)
                    val adapter = ArrayAdapter(this@AddActivity, android.R.layout.simple_spinner_item, descripcionesTipoPost)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerTipoPost.adapter = adapter

                    spinnerTipoPost.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {
                            selectedTipoPostId = tiposPost[position].id_tipoPost
                            Log.d("TipoPostSelection", "ID de tipo de post seleccionado: $selectedTipoPostId")
                        }

                        override fun onNothingSelected(adapterView: AdapterView<*>) {}
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
                    tiposMascotas = response.body() ?: emptyList()
                    val nombresTipo = tiposMascotas.map { it.NombreTipo }
                    val spinnerTipoMascota: Spinner = findViewById(R.id.spinner1)
                    val adapter = ArrayAdapter(this@AddActivity, android.R.layout.simple_spinner_item, nombresTipo)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerTipoMascota.adapter = adapter

                    spinnerTipoMascota.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {
                            selectedTipoMascotaId = tiposMascotas[position].id_Tipo
                            Log.d("TipoMascotaSelection", "ID de tipo seleccionado: $selectedTipoMascotaId")
                        }

                        override fun onNothingSelected(adapterView: AdapterView<*>) {}
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
                    edadesMascotas = response.body() ?: emptyList()
                    val descripcionesEdad = edadesMascotas.map { it.edadDescription }
                    val spinnerEdad: Spinner = findViewById(R.id.spinner2)
                    val adapter = ArrayAdapter(this@AddActivity, android.R.layout.simple_spinner_item, descripcionesEdad)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerEdad.adapter = adapter

                    spinnerEdad.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {
                            selectedEdadMascotaId = edadesMascotas[position].id_Edad
                            Log.d("EdadMascotaSelection", "ID de edad seleccionada: $selectedEdadMascotaId")
                        }

                        override fun onNothingSelected(adapterView: AdapterView<*>) {}
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
                    distritos = response.body() ?: emptyList()
                    val nombresDistrito = distritos.map { it.nombre }
                    val spinner5: Spinner = findViewById(R.id.spinner5)
                    val adapter = ArrayAdapter(this@AddActivity, android.R.layout.simple_spinner_item, nombresDistrito)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner5.adapter = adapter

                    spinner5.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {
                            // Guarda el ID del distrito seleccionado.
                            selectedDistritoId = distritos[position].id
                            Log.d("DistritoSelection", "ID seleccionado: $selectedDistritoId")
                        }

                        override fun onNothingSelected(adapterView: AdapterView<*>) {}
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

    private fun setupBottomNavigation() {
        val userId: Int = 0 // Asegúrate de obtener este valor adecuadamente, por ejemplo, desde un Intent.

        bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.profile -> {
                    val intentProfile = Intent(this, ProfileActivity::class.java).apply {
                        putExtra("USER_ID", userId)
                    }
                    startActivity(intentProfile)
                }
                R.id.adoptar -> {
                    val intentAdoptar = Intent(this, AdoptarActivity::class.java).apply {
                        putExtra("USER_ID", userId)
                    }
                    startActivity(intentAdoptar)
                }
                R.id.add -> {} // La actividad actual, no necesita acción.
                R.id.alerta -> {
                    val intentAyudar = Intent(this, AyudarActivity::class.java).apply {
                        putExtra("USER_ID", userId) // Aquí pasas el userId a AyudarActivity.
                    }
                    startActivity(intentAyudar)
                }
                R.id.cruzar -> {
                    val intentCruzar = Intent(this, CruceActivity::class.java).apply {
                        putExtra("USER_ID", userId) // Aquí pasas el userId a CruceActivity.
                    }
                    startActivity(intentCruzar)
                }
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