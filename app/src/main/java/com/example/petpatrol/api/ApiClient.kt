package com.example.petpatrol.api

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

object RetrofitClient {
//    private const val BASE_URL = "http://10.0.2.2:3000"
//    private const val BASE_URL = "http://localhost:3000"
    private const val BASE_URL = "http://192.168.0.105:3000"

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

    @GET("/sizeMascotas")
    fun getSizeMascotas(): Call<List<SizeMascota>>

    @GET("/sexoMascotas")
    fun getSexoMascotas(): Call<List<SexoMascota>>
    @Multipart
    @POST("/crearMascotaYPost")
    fun crearMascotaYPost(
        @Part("name_mascota") nameMascota: RequestBody,
        @Part("contenido_mascota") contenidoMascota: RequestBody,
        @Part("id_distrito") idDistrito: RequestBody,
        @Part("id_edad") idEdad: RequestBody,
        @Part("id_sexo") idSexo: RequestBody,
        @Part("id_size") idSize: RequestBody,
        @Part("id_tipo") idTipo: RequestBody,
        @Part("user_id") userId: RequestBody,
        @Part("tipo_post") tipoPost: RequestBody,
        @Part imagen: MultipartBody.Part
    ): Call<ResponseBody>


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

data class SizeMascota(
    @SerializedName("id_size") val idSize: Int,
    @SerializedName("size_mascota") val descripcion: String
)

data class SexoMascota(
    @SerializedName("id_sexo") val idSexo: Int,
    @SerializedName("sexo_mascota") val descripcion: String // Corregido aquí
)

data class MascotaPostData(
    val name_mascota: String,
    val contenido_mascota: String,
    val id_distrito: Int,
    val id_edad: Int,
    val id_sexo: Int,
    val id_size: Int,
    val id_tipo: Int,
    val user_id: Int,
    val tipo_post: Int,
)