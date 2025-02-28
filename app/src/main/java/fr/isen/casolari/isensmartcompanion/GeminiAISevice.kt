package fr.isen.casolari.isensmartcompanion

import kotlinx.coroutines.delay

object GeminiAIService {

    suspend fun analyzeText(input: String): String {
        delay(1000)
        return "Réponse générée pour \"$input\""

    }
}