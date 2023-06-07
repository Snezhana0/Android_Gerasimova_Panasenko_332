package ru.rsue.gerasimova.teachers_android.teacher

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import ru.rsue.gerasimova.teachers_android.api.Connection.teachersApi
import ru.rsue.gerasimova.teachers_android.chair.Chairs
import ru.rsue.gerasimova.teachers_android.post.Posts
import kotlin.system.exitProcess


// Контроллер, взаимодействующий с объектами модели и представления
class TeacherFragment : Fragment() {
    companion object {
        private const val ARG_TEACHER_ID = "teacher_id"

        fun newInstance(teacherId: Int) = TeacherFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_TEACHER_ID, teacherId)
            }
        }
    }

    private var teacher: Teachers? = null

    private lateinit var id_update: EditText
    private lateinit var phone_update: EditText
    private lateinit var email_update: EditText
    private lateinit var idChair_update: Spinner
    private lateinit var idPost_update: Spinner
    private lateinit var firstName_update: EditText
    private lateinit var secondName_update: EditText
    private lateinit var lastName_update: EditText

    lateinit var chair_select: Chairs
    lateinit var post_select: Posts

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val teacherId = requireArguments().getSerializable(ARG_TEACHER_ID) as Int
        teacher = Connection.teachers.find { teachers -> teachers.id == teacherId }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.activity_update_teacher, container, false)

        id_update = v.findViewById(R.id.id_update_teacher)
        var updateId = teacher?.id.toString()
        id_update.setText(updateId)


        phone_update = v.findViewById(R.id.phone_update)
        var phone = teacher?.phone.toString()
        phone_update.setText(phone)
        phone_update.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                //
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                teacher?.phone = s.toString().toInt()
            } //сделать проверку на пустую строку
            override fun afterTextChanged(c: Editable) {
            }
        })
//        if (!s.isNullOrEmpty()) {
//            val value = Integer.parseInt(s.toString())
//            // код...
//        } else {
//            // Обработка случая, когда ввод пустой
//        }

        email_update = v.findViewById(R.id.email_update)
        email_update.setText(teacher?.email)
        email_update.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                //
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                teacher?.email = s.toString()
            }
            override fun afterTextChanged(c: Editable) {
                //
            }
        })

        firstName_update = v.findViewById(R.id.firstName_update)
        var firstName = teacher?.firstName.toString()
        firstName_update.setText(firstName)
        firstName_update.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                //
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                teacher?.firstName = s.toString()
            }
            override fun afterTextChanged(c: Editable) {
                //
            }
        })

        secondName_update = v.findViewById(R.id.secondName_update)
        var secondName = teacher?.secondName.toString()
        secondName_update.setText(secondName)
        secondName_update.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                //
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                teacher?.secondName = s.toString()
            }
            override fun afterTextChanged(c: Editable) {
                //
            }
        })

        lastName_update = v.findViewById(R.id.lastName_update)
        lastName_update.setText(teacher?.lastName)
        lastName_update.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                //
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                teacher?.lastName = s.toString()
            }
            override fun afterTextChanged(c: Editable) {
                //
            }
        })

        v.findViewById<Button>(R.id.delete_button).setOnClickListener {
            GlobalScope.launch {
                teachersApi.deleteTeacher(id_update.text.toString().toInt()).enqueue(object : Callback<Unit> {
                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                        println("Передано")
                        GlobalScope.launch {
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

        idChair_update = v.findViewById(R.id.idChair_update)
        idPost_update = v.findViewById(R.id.idPost_update)

        idChair_update.adapter =
            ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, Connection.chairs)
                .apply{ setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }

        idChair_update.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    chair_select = Connection.chairs[p2]
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    //
                }
            }

        idPost_update.adapter =
            ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, Connection.posts)
                .apply{ setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }

        idPost_update.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    post_select = Connection.posts[p2]
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    //
                }
            }

        v.findViewById<Button>(R.id.update_button).setOnClickListener {
            GlobalScope.launch {
                val (id, teacher) = getTeacher()
                teachersApi.putTeacher(id, teacher).enqueue(object : Callback<Unit> {
                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                        println("Передано")
                        GlobalScope.launch {
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

        var idPost = teacher?.idPost.toString().toInt()
        idPost_update.setSelection(
            Connection.posts.indexOf(
            Connection.posts.find { posts -> posts.id == idPost }
        ))

        var idChair = teacher?.idChair.toString().toInt()
        idChair_update.setSelection(
            Connection.chairs.indexOf(
            Connection.chairs.find { chairs -> chairs.id == idChair }
        ))

        return v
    }

    fun getTeacher(): Pair<Int, Teachers> {
        return id_update.text.toString().toInt() to
                Teachers(
                    id_update.text.toString().toInt(),
                    chair_select.id,
                    post_select.id,
                    email_update.text.toString(),
                    phone_update.text.toString().toInt(),
                    firstName_update.text.toString(),
                    secondName_update.text.toString(),
                    lastName_update.text.toString(),
                )
    }
}