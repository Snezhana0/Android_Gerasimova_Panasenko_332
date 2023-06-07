package ru.rsue.gerasimova.teachers_android.post

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.rsue.gerasimova.teachers_android.R
import ru.rsue.gerasimova.teachers_android.api.Connection
import ru.rsue.gerasimova.teachers_android.api.Connection.postsApi
import kotlin.system.exitProcess


class PostAddActivity : AppCompatActivity() {
    lateinit var namePost_input : EditText

    lateinit var add_button : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)

        namePost_input = findViewById(R.id.namePost_input)
        add_button = findViewById(R.id.add_button)

        // Асинхронная передача значения на сервер
        add_button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                postsApi.postPost(addPost()).enqueue(object : Callback<Posts> {
                    override fun onResponse(call: Call<Posts>, response: Response<Posts>) {
                        println("Передано")
                        GlobalScope.launch {
                            Connection.updatePosts()
                            exitProcess(0)
                        }
                    }
                    override fun onFailure(call: Call<Posts>, t: Throwable) {
                        println("Ошибка")
                        t.printStackTrace()
                    }
                })
            }
        })
    }

    fun addPost(): Posts {
        return Posts(0,
            namePost_input.text.toString(),
        )
    }
}