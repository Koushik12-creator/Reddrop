package uk.ac.tees.mad.rd.authentication.model


data class UserInfo(
    val name: String = "",
    val email: String = "",
    val bloodGroup: String = "",
    val phoneNumber: String = "",
    val profilePicture: String = "",
    val healthDetails: HealthDetails? = null
)

data class HealthDetails(
    val anyHistory: Boolean = false,
    val diabetes: Boolean = false,
    val heartDisease: Boolean = false
)

