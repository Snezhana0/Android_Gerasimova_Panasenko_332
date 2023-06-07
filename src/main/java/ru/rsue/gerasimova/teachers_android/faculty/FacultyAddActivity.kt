package ru.rsue.gerasimova.teachers_android.faculty

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.rsue.gerasimova.teachers_android.R
import ru.rsue.gerasimova.teachers_android.api.Connection
import ru.rsue.gerasimova.teachers_android.api.Connection.facultiesApi
import kotlin.system.exitProcess

class FacultyAddActivity : AppCompatActivity() {
    lateinit var nameFaculty_input: EditText
    lateinit var shortNameFaculty_input: EditText

    lateinit var add_button : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_faculty)

        nameFaculty_input = findViewById(R.id.nameFaculty_input)
        shortNameFaculty_input = findViewById(R.id.shortNameFaculty_input)

        add_button = findViewById(R.id.add_button)

        // Асинхронная передача значения на сервер
        add_button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                facultiesApi.postFaculty(getFaculty()).enqueue(object : Callback<Faculties> {
                    override fun onResponse(call: Call<Faculties>, response: Response<Faculties>) {
                        println("Передано")
                        GlobalScope.launch {
                            Connection.updateFaculties()
                            exitProcess(0)
                        }
                    }

                    override fun onFailure(call: Call<Faculties>, t: Throwable) {
                        println("Ошибка")
                        t.printStackTrace()
                    }
                })
            }
        })
    }

    fun getFaculty(): Faculties {
        return Faculties(0,
            nameFaculty_input.text.toString(),
            shortNameFaculty_input.text.toString()
        )
    }
}