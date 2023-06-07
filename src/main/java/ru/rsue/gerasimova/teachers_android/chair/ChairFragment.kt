package ru.rsue.gerasimova.teachers_android.chair

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
import ru.rsue.gerasimova.teachers_android.api.Connection.chairsApi
import ru.rsue.gerasimova.teachers_android.faculty.Faculties
import kotlin.system.exitProcess

// Контроллер, взаимодействующий с объектами модели и представления
// Отображает инфу, которая содержится в экземпляре Chair
class ChairFragment : Fragment() {
    companion object {
        private const val ARG_CHAIR_ID = "chair_id"

        fun newInstance(chair_id: Int) = ChairFragment().apply {
            arguments = Bundle().apply {
                Log.d("aboba_chair", chair_id.toString())
                putSerializable(ARG_CHAIR_ID, chair_id)
            }
        }
    }

    private var chair: Chairs? = null

    private lateinit var id_update: EditText
    private lateinit var idFaculty_update: Spinner
    private lateinit var nameChair_update: EditText
    private lateinit var shortNameChair_update: EditText

    lateinit var faculty_select: Faculties

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val idChair = requireArguments().getSerializable(ARG_CHAIR_ID) as Int
        chair = Connection.chairs.find { chairs -> chairs.id == idChair }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.activity_update_chair, container, false)

        id_update = v.findViewById(R.id.id_update_chair)
        var updateId = chair?.id.toString()
        id_update.setText(updateId)


        nameChair_update = v.findViewById(R.id.nameChair_update)
        nameChair_update.setText(chair?.nameChair)
        nameChair_update.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                //
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                chair?.nameChair = s.toString()
            }

            override fun afterTextChanged(c: Editable) {
                //
            }
        })

        shortNameChair_update = v.findViewById(R.id.shortNameChair_update)
        shortNameChair_update.setText(chair?.shortNameChair)
        shortNameChair_update.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                //
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                chair?.shortNameChair = s.toString()
            }

            override fun afterTextChanged(c: Editable) {
                //
            }
        })

        idFaculty_update = v.findViewById(R.id.idFaculty_update)

        idFaculty_update.adapter =
            ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, Connection.faculties)
                .apply{ setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }

        idFaculty_update.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    faculty_select = Connection.faculties[p2]
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    //
                }
            }

        v.findViewById<Button>(R.id.delete_button).setOnClickListener {
            GlobalScope.launch {
                chairsApi.deleteChair(id_update.text.toString().toInt())
                    .enqueue(object : Callback<Unit> {
                        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                            println("Передано")
                            GlobalScope.launch {
                                Connection.updateChairs()
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
                val (id, chair) = getChair()
                chairsApi.putChair(id, chair).enqueue(object : Callback<Unit> {
                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                        println("Передано")
                        GlobalScope.launch {
                            Connection.updateChairs()
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

        var idFaculty = chair?.idFaculty.toString().toInt()
        idFaculty_update.setSelection(Connection.faculties.indexOf(
            Connection.faculties.find { faculties -> faculties.id == idFaculty }
        ))

        return v
    }

    fun getChair(): Pair<Int, Chairs> {
        return id_update.text.toString().toInt() to
                Chairs(
                    id_update.text.toString().toInt(),
                    faculty_select.id,
                    nameChair_update.text.toString(),
                    shortNameChair_update.text.toString()
                )
    }
}