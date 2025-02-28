package fr.isen.casolari.isensmartcompanion

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import fr.isen.casolari.isensmartcompanion.network.RetrofitInstance


@Composable
fun EventsScreen(){
    val context = LocalContext.current

    val events = listOf(
        IsenEvent(
            id = "a",
            title = "Soirée BDE",
            description = "Une soirée animée par le BDE avec DJ et cocktails",
            date = "12/03/2025",
            location = "ISEN Toulon",
            category = "BDE"
        ),
        IsenEvent(
            id = "b",
            title = "Gala ISEN",
            description = "Gala annuel de l'ISEN avec dîner et remise de prix",
            date = "25/06/2025",
            location = "Palais Neptune",
            category = "Gala"
        ),
        IsenEvent(
            id = "c",
            title = "Journée de Cohésion",
            description = "Activités de cohésion et team building pour les étudiants",
            date = "05/09/2025",
            location = "Campus ISEN",
            category = "Cohesion"
        ),
        IsenEvent(
            id = "d",
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
    /*val context = LocalContext.current
    val intent = android.content.Intent(context, EventDetailActivity::class.java)
    intent.putExtra("event", event)
    context.startActivity(intent)*/

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

    /*Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Détails de l'événement") },
                navigationIcon = {
                    IconButton(onClick = { activity?.finish() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Retour"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->*/

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
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
        // Spacer avec weight pour occuper tout l'espace restant et pousser le bouton vers le bas
        Spacer(modifier = Modifier.weight(1f))

        // Bouton "Retour" qui, lorsqu'il est cliqué, ferme l'activité
        Button(
            onClick = { activity?.finish() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Retour")
        }
    }
}

@Composable
fun DynamicEventsScreen() {
    // État pour stocker la liste des événements récupérés
    var eventsList by remember { mutableStateOf<List<IsenEvent>>(emptyList()) }
    // État pour signaler le chargement ou une erreur si besoin
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current

    // Lance une coroutine dès que le composable est créé
    LaunchedEffect(Unit) {
        try {
            // Appel à l'API pour récupérer la map d'événements
            val response = RetrofitInstance.api.getEvents()
            // Convertir la map en liste
            eventsList = response
        } catch (e: Exception) {
            e.printStackTrace()
            errorMessage = "Erreur lors du chargement des événements."
        }
    }


    // Si une erreur survient, on l'affiche
    if (errorMessage != null) {
        Text(
            text = errorMessage ?: "",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(16.dp)
        )
    } else {
        // Affichage de la liste avec LazyColumn
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(eventsList) { event ->
                // On réutilise notre composable EventItem pour afficher chaque événement
                EventItem(event = event) {

                    // Par exemple, ici vous pouvez gérer le clic sur un événement
                    val intent = android.content.Intent(context, EventDetailActivity::class.java)
                    intent.putExtra("event", event)
                    context.startActivity(intent)
                    // (comme lancer l'activité de détail)

                }
            }
        }
    }
}


