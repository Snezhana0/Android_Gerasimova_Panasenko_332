package ru.rsue.gerasimova.teachers_android.faculty

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
class FacultyListFragment : Fragment() {
    private var recyclerView: RecyclerView? = null
    private var adapter: FacultyAdapter? = null
    lateinit var add_button: FloatingActionButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_list, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView!!.layoutManager = LinearLayoutManager(activity)

        updateUI()

        add_button = view.findViewById(R.id.floatingActionButton_add)
        add_button.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view: View): Unit {
                val intent = Intent(context, FacultyAddActivity::class.java);
                startActivity(intent);
            }
        })

        return view
    }

    private fun updateUI() {
        val faculty = Connection.facultyBeauty()
        adapter = FacultyAdapter(faculty)
        recyclerView!!.adapter = adapter

        if (adapter == null) {
            adapter = FacultyAdapter(faculty)
            recyclerView!!.adapter = adapter
        }
        else
            adapter!!.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private class FacultyHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!), View.OnClickListener {
        var faculty_id: TextView = itemView!!.findViewById(R.id.faculty_id)
        var faculty_nameFaculty: TextView = itemView!!.findViewById(R.id.faculty_nameFaculty)

        private lateinit var faculty: Faculties

        fun bindFaculty(faculty: Faculties) {
            this.faculty = faculty
            faculty_id.text = faculty.id.toString()
            faculty_nameFaculty.text.toString()
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val context = v!!.context
            val intent = FacultyPagerActivity.newIntent(context, faculty.id)
            context.startActivity(intent)
        }
    }

    private class FacultyAdapter(faculty: List<Faculties>?) : RecyclerView.Adapter<FacultyHolder?>() {
        private var faculty: List<Faculties>? = null

        init {
            this.faculty = faculty
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacultyHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_faculty, parent, false)

            return FacultyHolder(view)
        }

        override fun onBindViewHolder(holder: FacultyHolder, position: Int) {
            val faculty = faculty!![position]
            holder.bindFaculty(faculty)
        }

        override fun getItemCount(): Int {
            return faculty!!.size
        }
    }
}