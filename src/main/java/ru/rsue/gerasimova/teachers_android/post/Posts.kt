package ru.rsue.gerasimova.teachers_android.post

data class Posts(
    var id: Int = -1,
    var namePost: String = "",
) {

    override fun toString(): String {
        return "$namePost"
    }
}
