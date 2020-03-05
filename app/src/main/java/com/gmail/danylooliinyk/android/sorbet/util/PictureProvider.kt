package com.gmail.danylooliinyk.android.sorbet.util

/**
 * PictureProvider
 */
interface PictureProvider {

    fun getRandomPicturePath(): String

    fun getPicturePath(forLetters: List<Char>): String
}
