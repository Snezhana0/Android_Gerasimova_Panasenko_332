package ru.rsue.gerasimova.teachers_android.api

import retrofit2.Call
import retrofit2.http.*
import ru.rsue.gerasimova.teachers_android.post.Posts


interface PostsApi {
    @GET("api/posts/")
    fun getPosts(): Call<List<Posts>>

    @POST("api/posts/")
    fun postPost(@Body post: Posts): Call<Posts>

    @DELETE("api/posts/{id}/")
    fun deletePost(@Path("id") idPost: Int): Call<Unit>

    @PUT("api/posts/{id}/")
    fun putPost(@Path("id") idPost: Int, @Body post: Posts): Call<Unit>
}