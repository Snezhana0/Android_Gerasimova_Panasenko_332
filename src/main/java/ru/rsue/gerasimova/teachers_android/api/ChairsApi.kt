package ru.rsue.gerasimova.teachers_android.api

import retrofit2.Call
import retrofit2.http.*
import ru.rsue.gerasimova.teachers_android.chair.Chairs


interface ChairsApi {
    @GET("api/chairs/")
    fun getChair(): Call<List<Chairs>>

    @POST("api/chairs/")
    fun postChair(@Body chair: Chairs): Call<Chairs>

    @DELETE("api/chairs/{id}/")
    fun deleteChair(@Path("id") idChair: Int): Call<Unit>

    @PUT("api/chairs/{id}/")
    fun putChair(@Path("id") idChair: Int, @Body chairs: Chairs): Call<Unit>
}