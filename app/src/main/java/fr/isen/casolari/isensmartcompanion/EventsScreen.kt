package fr.isen.casolari.isensmartcompanion

import android.provider.CalendarContract.Events
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import android.content.Intent

data class ISEvent(
    val title: String,
    val date: String,
    val location: String
)

@Composable
fun EventsScreen(){
    val context = LocalContext.current

    Button(onClick =  {
        context.startActivity(Intent(context, EventDetailActivity::class.java))
    }){
        Text(("Voir les details de l'évènnement"))
    }
}

@Composable
val fakeEvents = listOf(
    ISEvent("Soirée BDE", "12/03/2025", "Barathym"),
    ISEvent("Gala ISEN", "21/03/2025", "Hyères"),
    ISEvent("Journée de Cohésion", "05/09/2025", "Mourillon"),
    ISEvent("Tournoi Sportif BDS", "10/10/2025", "Complexe Léo Lagrange")
)