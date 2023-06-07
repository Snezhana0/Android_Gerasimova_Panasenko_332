package ru.rsue.gerasimova.teachers_android.chair

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
class ChairListFragment : Fragment() {
    private var recyclerView: RecyclerView? = null
    private var adapter: ChairAdapter? = null
    lateinit var add_button: FloatingActionButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_list, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView!!.layoutManager = LinearLayoutManager(activity)

        updateUI()

        add_button = view.findViewById(R.id.floatingActionButton_add)
        add_button.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view: View): Unit {
                val intent = Intent(context, ChairAddActivity::class.java);
                startActivity(intent);
            }
        })

        return view
    }

    private fun updateUI() {
        val chair = Connection.chairBeauty()
        adapter = ChairAdapter(chair)
        recyclerView!!.adapter = adapter

        if (adapter == null) {
            adapter = ChairAdapter(chair)
            recyclerView!!.adapter = adapter
        }
        else
            adapter!!.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private class ChairHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!), View.OnClickListener {
        var chair_id: TextView = itemView!!.findViewById(R.id.chair_id)
//        var chair_idFaculty: TextView = itemView!!.findViewById(R.id.chair_idFaculty)
        var chair_nameChair: TextView = itemView!!.findViewById(R.id.chair_nameChair)

        private lateinit var chair: Chairs

        fun bindChair(chair: Chairs) {
            this.chair = chair
            chair_id.text = chair.id.toString()
//            chair_idFaculty.text.toString().toInt()
            chair_nameChair.text.toString()
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val context = v!!.context
            val intent = ChairPagerActivity.newIntent(context, chair.id)
            context.startActivity(intent)
        }
    }

    private class ChairAdapter(chair: List<Chairs>?) : RecyclerView.Adapter<ChairHolder?>() {
        private var chair: List<Chairs>? = null

        init {
            this.chair = chair
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChairHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_chair, parent, false)

            return ChairHolder(view)
        }

        override fun onBindViewHolder(holder: ChairHolder, position: Int) {
            val chair = chair!![position]
            holder.bindChair(chair)
        }

        override fun getItemCount(): Int {
            return chair!!.size
        }
    }
}