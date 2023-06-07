package ru.rsue.gerasimova.teachers_android.api

import retrofit2.Call
import retrofit2.http.*
import ru.rsue.gerasimova.teachers_android.faculty.Faculties


interface FacultiesApi {
    @GET("api/faculties/")
    fun getFaculty(): Call<List<Faculties>>

    @POST("api/faculties/")
    fun postFaculty(@Body faculty: Faculties): Call<Faculties>

    @DELETE("api/faculties/{id}/")
    fun deleteFaculty(@Path("id") idFaculty: Int): Call<Unit>

    @PUT("api/faculties/{id}/")
    fun putFaculty(@Path("id") idFaculty: Int, @Body faculties: Faculties): Call<Unit>
}