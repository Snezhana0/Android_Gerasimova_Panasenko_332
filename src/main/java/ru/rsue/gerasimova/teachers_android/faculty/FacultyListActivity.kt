package ru.rsue.gerasimova.teachers_android.faculty

import android.os.Bundle
import androidx.fragment.app.Fragment
import ru.rsue.gerasimova.teachers_android.R
import ru.rsue.gerasimova.teachers_android.SingleFragmentActivity

// Выполняет ф-ции хоста для для FacultyListFragment (вывод списка)
class FacultyListActivity : SingleFragmentActivity() {
    override fun createFragment(): Fragment = FacultyListFragment()

}