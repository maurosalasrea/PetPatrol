package com.example.petpatrol.api

import com.google.gson.annotations.SerializedName
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:3000"
//    private const val BASE_URL = "http://localhost:3000"
//    private const val BASE_URL = "http://192.168.0.105:3000"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> createService(serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }
}
interface UserService {
    @POST("/users/login")
    fun postLogin(@Body loginData: LoginData): Call<Users>

    @GET("/tipoPost")
    fun getTipoPost(): Call<List<TipoPost>>
    @GET("/distritos")
    fun getDistritos(): Call<List<Distrito>>

    @GET("/edadMascotas")
    fun getEdadMascotas(): Call<List<EdadMascotas>>

    @GET("/tipoMascotas")
    fun getTipoMascotas(): Call<List<TipoMascotas>>

    @GET("/users/{id}")
    fun getUserById(@Path("id") id: Int): Call<UserData>

    @PUT("/users/update/{id}")
    fun updateUser(@Path("id") id: Int, @Body userData: UserData): Call<ResponseBody>

    @POST("/users/register")
    fun registerUser(@Body userData: UserData): Call<ResponseBody>

}
data class LoginData(val email_address: String, val password: String)

data class TipoPost(
    @SerializedName("id_tipo_post") val id_tipoPost: Int,
    @SerializedName("descripcion") val descripcion: String
)
data class Distrito(
    @SerializedName("id_distrito") val id: Int,
    @SerializedName("nombre_distrito") val nombre: String
)
data class EdadMascotas(
    @SerializedName("id_edad") val id_Edad: Int,
    @SerializedName("edad_description") val edadDescription: String
)
data class TipoMascotas(
    @SerializedName("id_tipo") val id_Tipo: Int,
    @SerializedName("nombre_tipo") val NombreTipo: String
)

