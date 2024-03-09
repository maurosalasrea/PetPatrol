package com.example.petpatrol

import MyListAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TodosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todos)

        // Inicialización del RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Lista de ítems para el adaptador
        val items = listOf(
            MyItem("Perro 1", R.drawable.edit_icon, R.drawable.delete_icon),
            MyItem("Perro 2", R.drawable.edit_icon, R.drawable.delete_icon),
            MyItem("Gato 2", R.drawable.edit_icon, R.drawable.delete_icon),
            MyItem("Gato 2", R.drawable.edit_icon, R.drawable.delete_icon)
            // Agrega más ítems según sea necesario
        )

        // Configuración del adaptador con la lista de ítems
        recyclerView.adapter = MyListAdapter(items)

        val backButton: Button = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            // Inicia AddActivity cuando se hace clic en el botón de regreso
            val intent = Intent(this, AddActivity::class.java)
            // Considera agregar flags si es necesario para limpiar el back stack, por ejemplo:
            // intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }
    }
}
