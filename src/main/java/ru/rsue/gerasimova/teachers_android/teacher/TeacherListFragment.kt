package ru.rsue.gerasimova.teachers_android.teacher

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.rsue.gerasimova.teachers_android.R
import ru.rsue.gerasimova.teachers_android.api.Connection


// Отображение данных в списке
class TeacherListFragment : Fragment() {
    private var recyclerView: RecyclerView? = null
    private var adapter: TeacherAdapter? = null
    lateinit var add_button: FloatingActionButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_list, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView!!.layoutManager = LinearLayoutManager(activity)

        updateUI()

        add_button = view.findViewById(R.id.floatingActionButton_add)
        add_button.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view: View): Unit {
                val intent = Intent(context, TeacherAddActivity::class.java);
                startActivity(intent);
            }
        })

        return view
    }

    private fun updateUI() {
        val teacher = Connection.teachers
        adapter = TeacherAdapter(teacher)
        recyclerView!!.adapter = adapter

        if (adapter == null) {
            adapter = TeacherAdapter(teacher)
            recyclerView!!.adapter = adapter
        }
        else
            adapter!!.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private class TeacherHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!), View.OnClickListener {
        var teacher_id: TextView = itemView!!.findViewById(R.id.teacher_id)
        var firstName: TextView = itemView!!.findViewById(R.id.teacher_name)
        var secondName: TextView = itemView!!.findViewById(R.id.teacher_surname)

        private lateinit var teacher: Teachers

        fun bindTeacher(teacher: Teachers) {
            this.teacher = teacher
            teacher_id.text = teacher.id.toString()
            firstName.text = teacher.firstName
            secondName.text = teacher.secondName
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val context = v!!.context
            val intent = TeacherPagerActivity.newIntent(context, teacher.id)
            context.startActivity(intent)
        }
    }

    private class TeacherAdapter(teacher: List<Teachers>?) : RecyclerView.Adapter<TeacherHolder?>() {
        private var teacher: List<Teachers>? = null

        init {
            this.teacher = teacher
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_teacher, parent, false)

            return TeacherHolder(view)
        }

        override fun onBindViewHolder(holder: TeacherHolder, position: Int) {
            val teacher = teacher!![position]
            holder.bindTeacher(teacher)
        }

        override fun getItemCount(): Int {
            return teacher!!.size
        }
    }
}