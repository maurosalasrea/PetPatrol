package com.example.petpatrol

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdoptarAdapter(private val listaPerritos: List<Adoptar>) : RecyclerView.Adapter<AdoptarAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.ivPerrito)
        val textView: TextView = view.findViewById(R.id.tvDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Asegúrate de inflar el layout correcto aquí
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adoptar_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val perrito = listaPerritos[position]
        holder.imageView.setImageResource(perrito.imagen)
        holder.textView.text = perrito.descripcion
    }

    override fun getItemCount() = listaPerritos.size
}
