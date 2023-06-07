package ru.rsue.gerasimova.teachers_android.post

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
class PostListFragment : Fragment() {
    private var recyclerView: RecyclerView? = null
    private var adapter: PostAdapter? = null
    lateinit var add_button: FloatingActionButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_list, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView!!.layoutManager = LinearLayoutManager(activity)

        updateUI()

        add_button = view.findViewById(R.id.floatingActionButton_add)
        add_button.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view: View): Unit {
                val intent = Intent(context, PostAddActivity::class.java);
                startActivity(intent);
            }
        })

        return view
    }

    private fun updateUI() {
        val post = Connection.postBeauty()
        adapter = PostAdapter(post)
        recyclerView!!.adapter = adapter

        if (adapter == null) {
            adapter = PostAdapter(post)
            recyclerView!!.adapter = adapter
        }
        else
            adapter!!.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private class PostHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!), View.OnClickListener {
        var post_id: TextView = itemView!!.findViewById(R.id.post_id)
        var post_namePost: TextView = itemView!!.findViewById(R.id.post_namePost)

        private lateinit var post: Posts

        fun bindPost(post: Posts) {
            this.post = post
            post_id.text = post.id.toString()
            post_namePost.text = post.namePost
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val context = v!!.context
            val intent = PostPagerActivity.newIntent(context, post.id)
            context.startActivity(intent)
        }
    }

    private class PostAdapter(post: List<Posts>?) : RecyclerView.Adapter<PostHolder?>() {
        private var post: List<Posts>? = null

        init {
            this.post = post
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_post, parent, false)

            return PostHolder(view)
        }

        override fun onBindViewHolder(holder: PostHolder, position: Int) {
            val post = post!![position]
            holder.bindPost(post)
        }

        override fun getItemCount(): Int {
            return post!!.size
        }
    }
}