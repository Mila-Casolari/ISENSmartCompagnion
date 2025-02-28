package fr.isen.casolari.isensmartcompanion

import android.os.Bundle
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.isen.casolari.isensmartcompanion.ui.theme.ISENSmartCompanionTheme
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalSoftwareKeyboardController


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
                    //MainApp(innerPadding, navController)
                    GeminiMainScreen(innerPadding)
                }
            }
        }
    }
}

@Composable
fun GeminiMainScreen(innerPadding: PaddingValues) {
    var userQuestion by remember { mutableStateOf("") }
    // Liste des échanges (chaque élément contient question et réponse)
    val responses = remember { mutableStateListOf<String>() }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        // Affiche le champ de texte
        TextField(
            value = userQuestion,
            onValueChange = { userQuestion = it },
            label = { Text("Posez votre question") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Bouton Envoyer
        Button(
            onClick = {
                if (userQuestion.isNotBlank()) {
                    coroutineScope.launch {
                        // Appel à GeminiAI pour analyser le texte
                        val aiResponse = GeminiAIService.analyzeText(userQuestion)
                        // Ajoute la question et la réponse à la liste des échanges
                        responses.add("Q: $userQuestion")
                        responses.add("A: $aiResponse")
                        // Réinitialise le champ de saisie
                        userQuestion = ""
                    }
                } else {
                    Toast.makeText(context, "Veuillez saisir une question.", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Envoyer")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Affiche la liste des échanges
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(responses) { response ->
                Text(
                    text = response,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
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
            //MainScreen()
            GeminiMainScreen(innerPadding)
        }
        composable(Screen.Event.route) {
            //EventsScreen()
            DynamicEventsScreen()
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