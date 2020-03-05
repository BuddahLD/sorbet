package com.gmail.danylooliinyk.android.sorbet.util

/**
 * StringUtils
 */
object StringUtils {

    /**
     * Generates string with random letters A..Z of given [length]. By default [length] is 1.
     */
    fun getRandomString(length: Int = 1) : String {
        val allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXTZ"
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }
}
