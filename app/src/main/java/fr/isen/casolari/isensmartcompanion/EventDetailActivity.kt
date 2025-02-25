package fr.isen.casolari.isensmartcompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

class EventDetailActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        val event = intent.getSerializableExtra("event") as? IsenEvent
        setContent {
            EventDetailScreen(event = event)
        }
    }
}

@Composable
fun EventDetailScreen(){
    Text(text="Détails de l'évent ici !")
}