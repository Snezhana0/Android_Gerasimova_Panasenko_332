package ru.rsue.gerasimova.teachers_android.teacher

data class Teachers(
    var id: Int = -1,
    var idChair: Int = 0,
    var idPost: Int = 0,
    var email: String  = "",
    var phone: Int = 0,
    var firstName: String = "",
    var secondName: String = "",
    var lastName: String = "",
)
