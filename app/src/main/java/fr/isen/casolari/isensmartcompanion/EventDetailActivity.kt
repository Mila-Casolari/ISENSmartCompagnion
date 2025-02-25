package fr.isen.casolari.isensmartcompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

class EventDetailActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContent {
            EventDetailScreen()
        }
    }
}

@Composable
fun EventDetailScreen(){
    Text(text="Détails de l'évent ici !")
}