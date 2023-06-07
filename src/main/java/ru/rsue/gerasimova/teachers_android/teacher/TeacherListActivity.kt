package ru.rsue.gerasimova.teachers_android.teacher

import androidx.fragment.app.Fragment
import ru.rsue.gerasimova.teachers_android.SingleFragmentActivity

// Выполняет ф-ции хоста для для TeacherListFragment (вывод списка)
class TeacherListActivity : SingleFragmentActivity() {
    override fun createFragment(): Fragment = TeacherListFragment()
}