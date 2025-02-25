package fr.isen.casolari.isensmartcompanion

import retrofit2.http.GET

interface EventApiService {
    // Effectue une requête GET vers "events.json"
    @GET("events.json")
    suspend fun getEvents(): Map<String, IsenEvent>
}