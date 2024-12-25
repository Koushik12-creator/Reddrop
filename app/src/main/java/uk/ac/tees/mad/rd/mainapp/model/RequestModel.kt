package uk.ac.tees.mad.rd.mainapp.model


data class RequestModel(
    val name: String? = "",
    val requiredBlood: String? = "",
    val contact: String? = "",
    val reportUrl: String? = ""
)
