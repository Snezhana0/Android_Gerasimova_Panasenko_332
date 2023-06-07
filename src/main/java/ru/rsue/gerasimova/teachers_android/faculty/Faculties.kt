package ru.rsue.gerasimova.teachers_android.faculty

data class Faculties(
    var id: Int = -1,
    var nameFaculty: String = "",
    var shortNameFaculty: String = ""
) {
    override fun toString(): String {
        return "$nameFaculty"
    }

}

