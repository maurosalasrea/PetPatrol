package com.example.petpatrol

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AdoptarFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Asegúrate de inflar el layout correcto aquí. Debe ser el layout del fragmento, no de la actividad.
        val view = inflater.inflate(R.layout.adoptar_fragment, container, false)

        recyclerView = view.findViewById(R.id.rvPerritos)

        // Ejemplo de datos
        val listaPerritos = listOf(
            Adoptar(R.drawable.perrito, "Descripción del perrito 1"),
            Adoptar(R.drawable.perrito, "Descripción del perrito 2"),
            Adoptar(R.drawable.perrito, "Descripción del perrito 1"),
            Adoptar(R.drawable.perrito, "Descripción del perrito 2"),
            Adoptar(R.drawable.perrito, "Descripción del perrito 2"),
            // Agrega más perritos aquí
        )

        recyclerView.adapter = AdoptarAdapter(listaPerritos)
        recyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }
}
