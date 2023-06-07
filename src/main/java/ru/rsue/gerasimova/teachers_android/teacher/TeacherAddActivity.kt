package ru.rsue.gerasimova.teachers_android.teacher

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.rsue.gerasimova.teachers_android.R
import ru.rsue.gerasimova.teachers_android.api.Connection
import ru.rsue.gerasimova.teachers_android.api.Connection.teachersApi
import ru.rsue.gerasimova.teachers_android.chair.Chairs
import ru.rsue.gerasimova.teachers_android.post.Posts
import kotlin.system.exitProcess


class TeacherAddActivity : AppCompatActivity() {
    lateinit var idChair_input: Spinner
    lateinit var idPost_input: Spinner
    lateinit var firstName_input: EditText
    lateinit var secondName_input: EditText
    lateinit var lastName_input: EditText
    lateinit var phone_input: EditText
    lateinit var email_input: EditText

    lateinit var add_button: Button

    lateinit var chair_select: Chairs
    lateinit var post_select: Posts

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_teacher)

        phone_input = findViewById(R.id.phone_input)
        email_input = findViewById(R.id.email_input)
        idChair_input = findViewById(R.id.idChair_input)
        idPost_input = findViewById(R.id.idPost_input)
        firstName_input = findViewById(R.id.firstName_input)
        secondName_input = findViewById(R.id.secondName_input)
        lastName_input = findViewById(R.id.lastName_input)
        add_button = findViewById(R.id.add_button)

        // Асинхронная передача значения на сервер
        add_button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                teachersApi.postTeacher(getTeacher()).enqueue(object : Callback<Teachers> {
                    override fun onResponse(call: Call<Teachers>, response: Response<Teachers>) {
                        println("Передано")
                        GlobalScope.launch {
                            Connection.updateTeachers()
                            exitProcess(0)
                        }
                    }
                    override fun onFailure(call: Call<Teachers>, t: Throwable) {
                        println("Ошибка")
                        t.printStackTrace()
                    }
                })
            }
        })

        idChair_input.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, Connection.chairBeauty())
                .apply{ setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }

        idChair_input.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    chair_select = Connection.chairBeauty()[p2]
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    //
                }
            }

        idPost_input.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, Connection.postBeauty())
                .apply{ setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }

        idPost_input.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    post_select = Connection.postBeauty()[p2]
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    //
                }
            }
    }

    fun getTeacher(): Teachers {
        return Teachers(
            0,
            chair_select.id,
            post_select.id,
            email_input.text.toString(),
            phone_input.text.toString().toInt(),
            firstName_input.text.toString(),
            secondName_input.text.toString(),
            lastName_input.text.toString(),
        )
    }
}



