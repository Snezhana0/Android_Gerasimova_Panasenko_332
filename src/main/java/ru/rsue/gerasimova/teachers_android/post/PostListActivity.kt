package ru.rsue.gerasimova.teachers_android.post

import androidx.fragment.app.Fragment
import ru.rsue.gerasimova.teachers_android.SingleFragmentActivity

// Выполняет ф-ции хоста для для TeacherListFragment (вывод списка)
class PostListActivity : SingleFragmentActivity() {
    override fun createFragment(): Fragment = PostListFragment()
}