package fr.isen.casolari.isensmartcompanion

import java.io.Serializable

data class IsenEvent(
    val id: Int,
    val title: String,
    val description: String,
    val date: String,
    val location: String,
    val category: String
) : Serializable
