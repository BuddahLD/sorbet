package com.gmail.danylooliinyk.android.sorbet.api.fuel

import com.github.kittinunf.fuel.coroutines.awaitString
import com.github.kittinunf.fuel.httpGet
import kotlin.random.Random

/**
 * FuelApiDefault
 */
class FuelApiDefault : FuelApi {

    override suspend fun getRandomGroupName(): String {
        val numberOfWords = Random.nextInt(from = 2, until = 4)
        val parameters = listOf(
            NUMBER_KEY to numberOfWords
        )

        val randomWords = WORDS_BASE_URL.httpGet(parameters).awaitString()

        return randomWords.replace("[", "")
            .replace("\"", "")
            .replace(",", " ")
            .replace("]", "")
    }

    companion object {
        private const val WORDS_BASE_URL = "https://random-word-api.herokuapp.com/word"
        private const val NUMBER_KEY = "number"
    }
}
