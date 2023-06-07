package ru.rsue.gerasimova.teachers_android.post

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import ru.rsue.gerasimova.teachers_android.R
import ru.rsue.gerasimova.teachers_android.api.Connection


// Листание данных между активностями
class PostPagerActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var post: List<Posts>

    companion object {
        private const val EXTRA_POST_ID = "ru.rsue.gerasimova.teachers_android.post_id"

        fun newIntent(packageContext: Context?, idPost: Int?) = Intent(
            packageContext,
            PostPagerActivity::class.java
        ).apply {
            putExtra(EXTRA_POST_ID, idPost)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pager)

        val idPost = intent.getSerializableExtra(EXTRA_POST_ID) as Int?

        viewPager = findViewById(R.id.activity_pager_view_pager)
        viewPager.adapter = ViewPagerAdapter(this)

        post = Connection.postBeauty()
        for (i in post.indices)
            if (post[i].id == idPost) {
                viewPager.currentItem = i
                break
            }
    }

    private class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
        private val post: List<Posts> = Connection.postBeauty()

        override fun getItemCount() = post.size
        override fun createFragment(position: Int) = PostFragment.newInstance(post[position].id)
    }
}