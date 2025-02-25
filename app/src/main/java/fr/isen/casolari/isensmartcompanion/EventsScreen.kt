package fr.isen.casolari.isensmartcompanion

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import fr.isen.casolari.isensmartcompanion.ui.theme.ISENSmartCompanionTheme

data class ISEvent(
    val title: String,
    val date: String,
    val location: String
)


@Composable
fun EventsScreen(){
    val context = LocalContext.current

    val events = listOf(
        IsenEvent(
            id = 1,
            title = "Soirée BDE",
            description = "Une soirée animée par le BDE avec DJ et cocktails",
            date = "12/03/2025",
            location = "ISEN Toulon",
            category = "BDE"
        ),
        IsenEvent(
            id = 2,
            title = "Gala ISEN",
            description = "Gala annuel de l'ISEN avec dîner et remise de prix",
            date = "25/06/2025",
            location = "Palais Neptune",
            category = "Gala"
        ),
        IsenEvent(
            id = 3,
            title = "Journée de Cohésion",
            description = "Activités de cohésion et team building pour les étudiants",
            date = "05/09/2025",
            location = "Campus ISEN",
            category = "Cohesion"
        ),
        IsenEvent(
            id = 4,
            title = "Tournoi Sportif BDS",
            description = "Compétition sportive organisée par le BDS",
            date = "10/10/2025",
            location = "Complexe Léo Lagrange",
            category = "Sport"
        )
    )

    // Affichage de la liste via LazyColumn
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(events) { event ->
            EventItem(event = event) {
                // Au clic, on lance l'activité EventDetailActivity
                val intent = Intent(context, EventDetailActivity::class.java)
                intent.putExtra("event", event)
                context.startActivity(intent)
            }
        }
    }
}

@Composable
fun EventItem(event: IsenEvent, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ){
        Column (modifier = Modifier.padding(16.dp)){
            Text(
                text = event.title,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Date: ${event.date}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Lieu: ${event.location}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Catégorie: ${event.category}",
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreen(event: IsenEvent?) {
    val activity = LocalContext.current as? Activity

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Détails de l'événement") },
                navigationIcon = {
                    IconButton(onClick = { activity?.finish() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Retour"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        if (event != null) {
            Column(modifier = Modifier.padding(70.dp)) {
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = event.description,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Date: ${event.date}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Lieu: ${event.location}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Catégorie: ${event.category}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        } else {
            Text(text = "Aucun événement trouvé")
        }
    }
}


