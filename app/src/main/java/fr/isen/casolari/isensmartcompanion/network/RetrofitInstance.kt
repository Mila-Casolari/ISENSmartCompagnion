package fr.isen.casolari.isensmartcompanion.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    // URL de base de votre service web
    private const val BASE_URL = "https://isen-smart-companion-default-rtdb.europe-west1.firebasedatabase.app/"

    // Instance paresseuse de Retrofit qui crée l'implémentation de l'interface
    val api: EventApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EventApiService::class.java)
    }
}