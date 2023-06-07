package ru.rsue.gerasimova.teachers_android.chair

import androidx.fragment.app.Fragment
import ru.rsue.gerasimova.teachers_android.SingleFragmentActivity

// Выполняет ф-ции хоста для для TeacherListFragment (вывод списка)
class ChairListActivity : SingleFragmentActivity() {
    override fun createFragment(): Fragment = ChairListFragment()
}