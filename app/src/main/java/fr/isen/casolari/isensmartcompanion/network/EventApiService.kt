package fr.isen.casolari.isensmartcompanion.network

import fr.isen.casolari.isensmartcompanion.IsenEvent
import retrofit2.http.GET

interface EventApiService {
    // Effectue une requête GET vers "events.json"
    @GET("events.json")
    suspend fun getEvents(): List<IsenEvent>
}