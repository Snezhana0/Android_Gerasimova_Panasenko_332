package ru.rsue.gerasimova.teachers_android.chair

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
class ChairPagerActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var chair: List<Chairs>

    companion object {
        private const val EXTRA_CHAIR_ID = "ru.rsue.gerasimova.teachers_android.chair_id"

        fun newIntent(packageContext: Context?, idChair: Int?) = Intent(
            packageContext,
            ChairPagerActivity::class.java
        ).apply {
            putExtra(EXTRA_CHAIR_ID, idChair)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pager)

        val idChair = intent.getSerializableExtra(EXTRA_CHAIR_ID) as Int?

        viewPager = findViewById(R.id.activity_pager_view_pager)
        viewPager.adapter = ViewPagerAdapter(this)

        chair = Connection.chairBeauty()
        for (i in chair.indices)
            if (chair[i].id == idChair) {
                viewPager.currentItem = i
                break
            }
    }

    private class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
        private val chair: List<Chairs> = Connection.chairBeauty()

        override fun getItemCount() = chair.size
        override fun createFragment(position: Int) = ChairFragment.newInstance(chair[position].id)
    }
}