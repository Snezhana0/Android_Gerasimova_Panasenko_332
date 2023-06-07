package ru.rsue.gerasimova.teachers_android.post

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.rsue.gerasimova.teachers_android.R
import ru.rsue.gerasimova.teachers_android.api.Connection
import ru.rsue.gerasimova.teachers_android.api.Connection.postsApi
import kotlin.system.exitProcess


// Контроллер, взаимодействующий с объектами модели и представления
class PostFragment : Fragment() {
    companion object {
        private const val ARG_POST_ID = "post_id"

        fun newInstance(idPost: Int) = PostFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_POST_ID, idPost)
            }
        }
    }

    private var post: Posts? = null

    private lateinit var id_update: EditText
    private lateinit var namePost_update: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super. onCreate(savedInstanceState)
        val idPost = requireArguments().getSerializable(ARG_POST_ID) as Int
        post = Connection.posts.find { posts -> posts.id == idPost }
    }

    override fun onCreateView(inflater: LayoutInflater, container:
    ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.activity_update_post, container, false)

        id_update = v.findViewById(R.id.id_update_post)
        var updateId = post?.id.toString()
        id_update.setText(updateId)

        namePost_update = v.findViewById(R.id.namePost_update)
        namePost_update.setText(post?.namePost)
        namePost_update.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                //
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                post?.namePost = s.toString()
            }
            override fun afterTextChanged(c: Editable) {
                //
            }
        })

        v.findViewById<Button>(R.id.delete_button).setOnClickListener {
            GlobalScope.launch {
                postsApi.deletePost(id_update.text.toString().toInt()).enqueue(object : Callback<Unit> {
                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                        println("Передано")
                        GlobalScope.launch {
                            Connection.updatePosts()
                            exitProcess(0)
                        }
                    }
                    override fun onFailure(call: Call<Unit>, t: Throwable) {
                        println("Ошибка")
                        t.printStackTrace()
                    }
                })
            }
        }

        v.findViewById<Button>(R.id.update_button).setOnClickListener {
            GlobalScope.launch {
                val (id, post) = getPost()
                postsApi.putPost(id, post).enqueue(object : Callback<Unit> {
                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                        println("Передано")
                        GlobalScope.launch {
                            Connection.updatePosts()
                            Connection.updateTeachers()
                            exitProcess(0)
                        }
                    }
                    override fun onFailure(call: Call<Unit>, t: Throwable) {
                        println("Ошибка")
                        t.printStackTrace()
                    }
                })
            }
        }

        return v
    }

    fun getPost(): Pair<Int, Posts> {
        return id_update.text.toString().toInt() to
                Posts(
                    id_update.text.toString().toInt(),
                    namePost_update.text.toString(),
                )
    }
}