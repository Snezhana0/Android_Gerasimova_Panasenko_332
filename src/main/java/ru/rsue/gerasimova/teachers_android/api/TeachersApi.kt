package ru.rsue.gerasimova.teachers_android.api

import retrofit2.Call
import retrofit2.http.*
import ru.rsue.gerasimova.teachers_android.teacher.Teachers


interface TeachersApi {
    @GET("api/teachers/")
    fun getTeacher(): Call<List<Teachers>>

    @POST("api/teachers/")
    fun postTeacher(@Body teacher: Teachers): Call<Teachers>

    @DELETE("api/teachers/{id}/")
    fun deleteTeacher(@Path("id") teacherId: Int): Call<Unit>

    @PUT("api/teachers/{id}/")
    fun putTeacher(@Path("id") teacherId: Int, @Body teacher: Teachers): Call<Unit>
}