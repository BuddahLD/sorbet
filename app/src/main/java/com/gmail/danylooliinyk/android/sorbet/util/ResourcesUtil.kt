package com.gmail.danylooliinyk.android.sorbet.util

import android.content.Context
import com.gmail.danylooliinyk.android.sorbet.R

/**
 * ResourcesUtil
 */
class ResourcesUtil(private val context: Context) {

    fun getResourcesArray(arrayId: Int): List<Int> =
        context.resources.getIntArray(arrayId).toList()

    fun getColorsArray(arrayId: Int): List<String> =
        getResourcesArray(arrayId).map {
            String.format("#%06X", 0xFFFFFF and it)
        }
}
