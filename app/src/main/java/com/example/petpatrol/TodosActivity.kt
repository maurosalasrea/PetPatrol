package com.example.petpatrol

import MyListAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    }
}
