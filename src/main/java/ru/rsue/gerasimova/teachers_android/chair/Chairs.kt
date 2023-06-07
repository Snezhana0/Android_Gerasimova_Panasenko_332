package ru.rsue.gerasimova.teachers_android.chair

data class Chairs(
    var id: Int = -1,
    var idFaculty: Int = 0,
    var nameChair: String = "",
    var shortNameChair: String = ""
) {
    override fun toString(): String {
        return "$nameChair"
    }
}
