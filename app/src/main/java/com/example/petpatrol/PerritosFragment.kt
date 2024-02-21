package com.example.petpatrol

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PerritosFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Asegúrate de inflar el layout correcto aquí. Debe ser el layout del fragmento, no de la actividad.
        val view = inflater.inflate(R.layout.fragment_perritos, container, false)

        recyclerView = view.findViewById(R.id.rvPerritos)

        // Ejemplo de datos
        val listaPerritos = listOf(
            Perrito(R.drawable.perrito, "Descripción del perrito 1"),
            Perrito(R.drawable.perrito, "Descripción del perrito 2"),
            Perrito(R.drawable.perrito, "Descripción del perrito 1"),
            Perrito(R.drawable.perrito, "Descripción del perrito 2"),
            Perrito(R.drawable.perrito, "Descripción del perrito 2"),
            // Agrega más perritos aquí
        )

        recyclerView.adapter = PerritosAdapter(listaPerritos)
        recyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }
}
