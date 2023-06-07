package ru.rsue.gerasimova.teachers_android.faculty

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
class FacultyPagerActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var faculty: List<Faculties>

    companion object {
        private const val EXTRA_FACULTY_ID = "ru.rsue.gerasimova.teachers_android.faculty_id"

        fun newIntent(packageContext: Context?, idFaculty: Int?) = Intent(
            packageContext,
            FacultyPagerActivity::class.java
        ).apply {
            putExtra(EXTRA_FACULTY_ID, idFaculty)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pager)

        val idFaculty = intent.getSerializableExtra(EXTRA_FACULTY_ID) as Int?

        viewPager = findViewById(R.id.activity_pager_view_pager)
        viewPager.adapter = ViewPagerAdapter(this)

        faculty = Connection.facultyBeauty()
        for (i in faculty.indices)
            if (faculty[i].id == idFaculty) {
                viewPager.currentItem = i
                break
            }
    }


    private class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
        private val faculty: List<Faculties> = Connection.facultyBeauty()

        override fun getItemCount() = faculty.size
        override fun createFragment(position: Int) = FacultyFragment.newInstance(faculty[position].id)
    }
}