package ru.rsue.gerasimova.teachers_android.teacher

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
class TeacherPagerActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var teacher: List<Teachers>

    companion object {
        private const val EXTRA_TEACHER_ID = "ru.rsue.gerasimova.teachers_android.teacher_id"

        fun newIntent(packageContext: Context?, teacherId: Int?) = Intent(
            packageContext,
            TeacherPagerActivity::class.java
        ).apply {
            putExtra(EXTRA_TEACHER_ID, teacherId)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pager)

        val teacherId = intent.getSerializableExtra(EXTRA_TEACHER_ID) as Int?

        viewPager = findViewById(R.id.activity_pager_view_pager)
        viewPager.adapter = ViewPagerAdapter(this)

        teacher = Connection.teachers
        for (i in teacher.indices)
            if (teacher[i].id == teacherId) {
                viewPager.currentItem = i
                break
            }
    }

    private class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
        private val teachers: List<Teachers> = Connection.teachers

        override fun getItemCount() = teachers.size
        override fun createFragment(position: Int) =
            TeacherFragment.newInstance(teachers[position].id)
    }
}