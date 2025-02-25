package fr.isen.casolari.isensmartcompanion

import android.icu.text.CaseMap.Title
import android.os.Bundle
import android.provider.CalendarContract.Events
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.isen.casolari.isensmartcompanion.ui.theme.ISENSmartCompanionTheme

sealed class Screen(val route: String, val title: String) {
    object Home : Screen("home", "Home")
    object Event : Screen("events", "Events")
    object History : Screen("history", "History")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            ISENSmartCompanionTheme {
                Scaffold(bottomBar = {
                    BottomNavigationBar(navController)
                }, modifier = Modifier.fillMaxSize()) { innerPadding ->
                    //MainScreen()
                    MainApp(innerPadding, navController)
                }

            }
        }
    }
}


@Composable
fun MainApp(innerPadding: PaddingValues, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(Screen.Home.route) {
            MainScreen()
        }
        composable(Screen.Event.route) {
            EventsScreen()
        }
        composable(Screen.History.route) {
            HistoryScreen()
        }

    }
}

@Composable
fun MainScreen() {
    val context = LocalContext.current
    // On stocke la question de l'utilisateur et la réponse (fausse pour l'instant) dans des variables réactives (states)
    var userQuestion by remember { mutableStateOf("") }
    var aiResponse by remember { mutableStateOf("En attente de question...") }

    // On utilise une Column pour empiler verticalement les éléments
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),           // marge autour
        horizontalAlignment = Alignment.CenterHorizontally,  // centre horizontalement
        verticalArrangement = Arrangement.Center             // centre verticalement
    ) {

        // ---------- Logo + Titre ----------
        // Remplace R.drawable.logo_isen par ton image dans les ressources
        Image(
            painter = painterResource(id = R.drawable.isen_logo_rn),
            contentDescription = "Logo ISEN",
            modifier = Modifier
                .size(100.dp)
                .padding(bottom = 8.dp)
        )

        Text(
            text = "ISEN",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color.Red
            )
        )
        Text(
            text = "Smart Companion",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // ---------- Champ de texte pour la question ----------
        TextField(
            value = userQuestion,
            onValueChange = { newText -> userQuestion = newText },
            label = { Text("Posez votre question") },
            modifier = Modifier.fillMaxWidth()
        )

        // ---------- Bouton pour envoyer la question ----------
        Button(
            onClick = {
                Toast.makeText(context, "Question Submitted", Toast.LENGTH_SHORT).show()
                // Ici on simule une réponse "fausse". Plus tard, tu pourras appeler l'API d'IA.
                aiResponse = "Tu as demandé : $userQuestion"
            },
            modifier = Modifier
                .padding(top = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Envoyer",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text("Envoyer")
        }

        // ---------- Texte pour la réponse de l'IA ----------
        Text(
            text = aiResponse,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}