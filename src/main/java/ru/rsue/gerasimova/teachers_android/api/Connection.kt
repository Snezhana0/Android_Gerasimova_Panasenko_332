package ru.rsue.gerasimova.teachers_android.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.rsue.gerasimova.teachers_android.chair.Chairs
import ru.rsue.gerasimova.teachers_android.faculty.Faculties
import ru.rsue.gerasimova.teachers_android.teacher.Teachers
import ru.rsue.gerasimova.teachers_android.post.Posts


object Connection {
    var teachers: List<Teachers> = emptyList()
    var chairs: List<Chairs> = emptyList()
    var posts: List<Posts> = emptyList()
    var faculties: List<Faculties> = emptyList()

    val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val teachersApi = retrofit.create(TeachersApi::class.java)
    val chairsApi = retrofit.create(ChairsApi::class.java)
    val postsApi = retrofit.create(PostsApi::class.java)
    val facultiesApi = retrofit.create(FacultiesApi::class.java)

    fun updateTeachers(): List<Teachers> {
        teachers = teachersApi.getTeacher().execute().body()?: emptyList<Teachers>()
        return teachers
    }
    fun updateChairs(): List<Chairs> {
        chairs = chairsApi.getChair().execute().body()?: emptyList<Chairs>()
        return chairs
    }
    fun updatePosts(): List<Posts> {
        posts = postsApi.getPosts().execute().body()?: emptyList<Posts>()
        return posts
    }
    fun updateFaculties(): List<Faculties> {
        faculties = facultiesApi.getFaculty().execute().body()?: emptyList<Faculties>()
        return faculties
    }

    fun chairBeauty() =
        chairs.filter { chairs -> chairs.id >= 0 }
    fun postBeauty() =
        posts.filter { posts -> posts.id >= 0 }
    fun facultyBeauty() =
        faculties.filter { faculties -> faculties.id >= 0 }


    fun update() {
        updateChairs()
        updateTeachers()
        updatePosts()
        updateFaculties()
    }
}