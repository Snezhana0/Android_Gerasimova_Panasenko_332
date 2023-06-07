package ru.rsue.gerasimova.teachers_android.chair

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
import ru.rsue.gerasimova.teachers_android.api.Connection.chairsApi
import kotlin.system.exitProcess
import ru.rsue.gerasimova.teachers_android.faculty.Faculties

class ChairAddActivity : AppCompatActivity() {
    lateinit var idFaculty_input : Spinner
    lateinit var nameChair_input: EditText
    lateinit var shortNameChair_input: EditText

    lateinit var add_button : Button

    lateinit var faculty_select: Faculties

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_chair)

        idFaculty_input = findViewById(R.id.idFaculty_input)
        nameChair_input = findViewById(R.id.nameChair_input)
        shortNameChair_input = findViewById(R.id.shortNameChair_input)

        add_button = findViewById(R.id.add_button)

        // Асинхронная передача значения на сервер
        add_button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                chairsApi.postChair(getChair()).enqueue(object : Callback<Chairs> {
                    override fun onResponse(call: Call<Chairs>, response: Response<Chairs>) {
                        println("Передано")
                        GlobalScope.launch {
                            Connection.updateChairs()
                            exitProcess(0)
                        }
                    }

                    override fun onFailure(call: Call<Chairs>, t: Throwable) {
                        println("Ошибка")
                        t.printStackTrace()
                    }
                })
            }
        })
        idFaculty_input.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, Connection.facultyBeauty())
                .apply { setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }

        idFaculty_input.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    faculty_select = Connection.facultyBeauty()[p2]
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    //
                }
            }
    }

    fun getChair(): Chairs {
        return Chairs(0,
            faculty_select.id,
            nameChair_input.text.toString(),
            shortNameChair_input.text.toString()
        )
    }
}