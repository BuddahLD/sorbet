package com.gmail.danylooliinyk.android.sorbet.util

import android.content.Context
import com.gmail.danylooliinyk.android.sorbet.R
import java.lang.StringBuilder

/**
 * PictureProvider
 */
class PictureProviderUiAvatars(
    private val resourcesUtil: ResourcesUtil
): PictureProvider {

    override fun getRandomPicturePath(): String {
        val randomLetterOne = StringUtils.getRandomString()[0]
        val randomLetterTwo = StringUtils.getRandomString()[0]

        return getPicturePath(listOf(randomLetterOne, randomLetterTwo))
    }

    /**
     * Takes only first two letters to generate an avatar. [forLetters] size other than 2
     * is forbidden.
     */
    override fun getPicturePath(forLetters: List<Char>): String {
        require(forLetters.size == 2) { "\'getPicturePath\' method accepts only 2 letters." }

        val colors = resourcesUtil.getColorsArray(R.array.group_avatars_colors)
        val randomColor = colors.random().replace("#", "")

        return StringBuilder(BASE_URL).apply {
            append("?")

            append("$NAME_KEY=${forLetters[0]}+${forLetters[1]}")

            append("&")

            append("$ROUNDED_KEY=$ROUNDED_VALUE")

            append("&")

            append("$BACKGROUND_KEY=$randomColor")
        }.toString()
    }

    companion object {
        private const val BASE_URL = "https://eu.ui-avatars.com/api/"

        private const val NAME_KEY = "name"
        private const val ROUNDED_KEY = "rounded"
        private const val ROUNDED_VALUE = "true"
        private const val BACKGROUND_KEY = "background"
    }
}
