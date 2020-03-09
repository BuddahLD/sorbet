package com.gmail.danylooliinyk.android.sorbet.api.fuel

import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

/**
 * FuelApiTest
 */
class FuelApiTest {

    lateinit var fuelApi: FuelApi

    @Before
    fun setUp() {
        fuelApi = FuelApiDefault()
    }

    @Test
    fun getRandomGroupName() = runBlocking {
        val groupName = fuelApi.getRandomGroupName()

        assertTrue(groupName.isNotEmpty())
        val size = groupName.split(" ").size
        assertTrue(size in 2..3)
    }
}
