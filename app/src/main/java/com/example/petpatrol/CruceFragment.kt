package com.example.petpatrol

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petpatrol.api.Post
import com.example.petpatrol.api.RetrofitClient
import com.example.petpatrol.api.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CruceFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private var adapter: CruceAdapter = CruceAdapter(mutableListOf())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.cruce_fragment, container, false)
        recyclerView = view.findViewById(R.id.rvCruce)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter // Asigna el adaptador al RecyclerView

        cargarPostsTipoCruce()

        return view
    }

    private fun cargarPostsTipoCruce() {
        RetrofitClient.createService(UserService::class.java).getPostsByTipo3(3).enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful) {
                    // Actualiza los datos del adaptador con la respuesta
                    adapter.updateData(response.body() ?: emptyList())
                    Log.d("CruceFragment", "Posts cargados correctamente")
                } else {
                    Log.e("CruceFragment", "Error al obtener los posts: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Log.e("CruceFragment", "Fallo al obtener los posts", t)
            }
        })
    }

}
