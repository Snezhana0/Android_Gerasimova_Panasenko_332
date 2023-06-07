package ru.rsue.gerasimova.teachers_android.faculty

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.rsue.gerasimova.teachers_android.R
import ru.rsue.gerasimova.teachers_android.api.Connection
import ru.rsue.gerasimova.teachers_android.api.Connection.facultiesApi
import kotlin.system.exitProcess

// Контроллер, взаимодействующий с объектами модели и представления
// Отображает инфу, которая содержится в экземпляре Faculty
class FacultyFragment : Fragment() {
    companion object {
        private const val ARG_FACULTY_ID = "faculty_id"

        fun newInstance(facultyId: Int) = FacultyFragment().apply {
            arguments = Bundle().apply {
                Log.d("aboba", facultyId.toString())
                Log.d("aboba :(", "facultyId.toString()")
                putInt(ARG_FACULTY_ID, facultyId)
            }
        }
    }

    private var faculty: Faculties? = null

    private lateinit var id_update: EditText
    private lateinit var nameFaculty_update: EditText
    private lateinit var shortNameFaculty_update: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val idFaculty = requireArguments().getSerializable(ARG_FACULTY_ID) as Int
        faculty = Connection.faculties.find { faculties -> faculties.id == idFaculty
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.activity_update_faculty, container, false)

        id_update = v.findViewById(R.id.id_update_faculty)
        var updateId = faculty?.id.toString()
        id_update.setText(updateId)


        nameFaculty_update = v.findViewById(R.id.nameFaculty_update)
        nameFaculty_update.setText(faculty?.nameFaculty)
        nameFaculty_update.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                //
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                faculty?.nameFaculty = s.toString()
            }

            override fun afterTextChanged(c: Editable) {
                //
            }
        })

        shortNameFaculty_update = v.findViewById(R.id.shortNameFaculty_update)
        shortNameFaculty_update.setText(faculty?.shortNameFaculty)
        shortNameFaculty_update.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                //
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                faculty?.shortNameFaculty = s.toString()
            }

            override fun afterTextChanged(c: Editable) {
                //
            }
        })


        v.findViewById<Button>(R.id.delete_button).setOnClickListener {
            GlobalScope.launch {
                facultiesApi.deleteFaculty(id_update.text.toString().toInt())
                    .enqueue(object : Callback<Unit> {
                        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                            println("Передано")
                            GlobalScope.launch {
                                Connection.updateFaculties()
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
                val (id, faculty) = getFaculty()
                facultiesApi.putFaculty(id, faculty).enqueue(object : Callback<Unit> {
                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                        println("Передано")
                        GlobalScope.launch {
                            Connection.updateFaculties()
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

    fun getFaculty(): Pair<Int, Faculties> {
        return id_update.text.toString().toInt() to
                Faculties(
                    id_update.text.toString().toInt(),
                    nameFaculty_update.text.toString(),
                    shortNameFaculty_update.text.toString()
                )
    }
}