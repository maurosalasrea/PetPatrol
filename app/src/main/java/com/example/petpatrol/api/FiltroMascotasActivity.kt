import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import com.example.petpatrol.R
import com.example.petpatrol.api.FiltroMascotas
import com.example.petpatrol.api.RetrofitClient
import com.example.petpatrol.api.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException

class FiltroMascotasActivity : AppCompatActivity() {

    private lateinit var userService: UserService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_filter)

        userService = RetrofitClient.createService(UserService::class.java)

        val applyFilterButton: Button = findViewById(R.id.button_apply_filter)
        applyFilterButton.setOnClickListener {
            filtrarMascotas()
        }
    }

    private fun filtrarMascotas() {
        val idDistrito = obtenerValorSeleccionado(R.id.checkbox_option51, R.id.checkbox_option52, R.id.checkbox_option53, R.id.checkbox_option54)
        val idEdad = obtenerValorSeleccionado(R.id.checkbox_option01, R.id.checkbox_option02, R.id.checkbox_option03)
        val idSize = obtenerValorSeleccionado(R.id.checkbox_option11, R.id.checkbox_option12, R.id.checkbox_option13)
        val idSexo = obtenerValorSeleccionado(R.id.checkbox_option21, R.id.checkbox_option22)
        val idTipo = obtenerValorSeleccionado(R.id.checkbox_option1, R.id.checkbox_option2, R.id.checkbox_option3)

        val filtroMascotas = FiltroMascotas(idDistrito, idEdad, idSexo, idSize, idTipo)

        // Log de los datos seleccionados del filtro
        Log.d("FiltroMascotasActivity", "Distrito: $idDistrito, Edad: $idEdad, Tamaño: $idSize, Sexo: $idSexo, Tipo: $idTipo")

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = userService.filtrarMascotas(filtroMascotas).execute()
                if (response.isSuccessful) {
                    val mascotas = response.body()
                    // Manejar la respuesta del servidor
                    mascotas?.let {
                        // Procesar los datos de las mascotas obtenidas
                    }
                } else {
                    // Manejar el error de la solicitud
                    val errorMessage = response.errorBody()?.string() ?: "Error desconocido"
                    Log.e("FiltroMascotasActivity", "Error en la solicitud: $errorMessage")
                }
            } catch (e: HttpException) {
                // Manejar errores de HTTP
                Log.e("FiltroMascotasActivity", "Error HTTP: ${e.message()}")
            } catch (e: Throwable) {
                // Manejar otros errores
                Log.e("FiltroMascotasActivity", "Error inesperado: ${e.message}")
            }
        }
    }

    private fun obtenerValorSeleccionado(vararg checkBoxIds: Int): Int? {
        checkBoxIds.forEach { checkBoxId ->
            val checkBox: CheckBox = findViewById(checkBoxId)
            if (checkBox.isChecked) {
                // El valor asociado al CheckBox es el mismo que su ID menos el prefijo "checkbox_option"
                return checkBoxId - R.id.checkbox_option1 + 1
            }
        }
        return null
    }
}
