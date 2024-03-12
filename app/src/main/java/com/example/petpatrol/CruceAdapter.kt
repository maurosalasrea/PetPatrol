package com.example.petpatrol

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.petpatrol.api.Post

class CruceAdapter(private var posts: MutableList<Post> = mutableListOf()) : RecyclerView.Adapter<CruceAdapter.ViewHolder>() {

    fun updateData(newPosts: List<Post>) {
        posts.clear()
        posts.addAll(newPosts)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvName)
        val ivImage: ImageView = view.findViewById(R.id.ivPerrito)
        val tvDescription: TextView = view.findViewById(R.id.tvDescription)
        val tvAge: TextView = view.findViewById(R.id.tvAge)
        val tvSize: TextView = view.findViewById(R.id.tvSize)
        val tvSex: TextView = view.findViewById(R.id.tvSexo)
        val tvDistrict: TextView = view.findViewById(R.id.tvDistrito)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cruce_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts[position]

        holder.tvName.text = "Nombre: ${post.name_mascota}"
        holder.tvDescription.text = "Descripción: ${post.contenido_mascota}"
        holder.tvAge.text = "Edad: ${getAgeDescription(post.id_edad)}"
        holder.tvSize.text = "Tamaño: ${getSizeDescription(post.id_size)}"
        holder.tvSex.text = "Sexo: ${getSexDescription(post.id_sexo)}"
        holder.tvDistrict.text = "Distrito: ${getDistrictDescription(post.id_distrito)}"

        if (post.imageUrl.isNotEmpty()) {
            Glide.with(holder.ivImage.context).load(post.imageUrl).into(holder.ivImage)
        } else {
            holder.ivImage.setImageResource(R.drawable.ic_add_image_white) // Coloca una imagen predeterminada si no hay URL de imagen
        }
    }

    override fun getItemCount() = posts.size

    private fun getAgeDescription(idAge: Int): String = when (idAge) {
        1 -> "Cachorro"
        2 -> "Joven"
        3 -> "Adulto"
        else -> "Desconocido"
    }

    private fun getSizeDescription(idSize: Int): String = when (idSize) {
        1 -> "Pequeño"
        2 -> "Mediano"
        3 -> "Grande"
        else -> "Desconocido"
    }

    private fun getSexDescription(idSex: Int): String = when (idSex) {
        1 -> "Macho"
        2 -> "Hembra"
        else -> "Desconocido"
    }

    private fun getDistrictDescription(idDistrict: Int): String = when (idDistrict) {
        1 -> "Iquitos"
        2 -> "Belen"
        3 -> "Punchana"
        4 -> "San Juan"
        else -> "Desconocido"
    }
}
