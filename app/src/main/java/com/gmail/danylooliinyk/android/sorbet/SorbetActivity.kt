package com.gmail.danylooliinyk.android.sorbet

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.addListener
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.gmail.danylooliinyk.android.sorbet.ui.signOut.SignOutFragmentDirections
import com.gmail.danylooliinyk.android.sorbet.util.Const
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * SorbetActivity
 */
class SorbetActivity : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView

    private var navListener: NavController.OnDestinationChangedListener? = null
    private var backPressed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sorbet)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fNavHost) as NavHostFragment
        this.bottomNavigation = findViewById(R.id.bottomNavigation)
        NavigationUI.setupWithNavController(bottomNavigation, navHostFragment.navController)
        setNavControllerListener(findNavController(R.id.fNavHost))
    }

    override fun onDestroy() {
        super.onDestroy()

        navListener?.run {
            findNavController(R.id.fNavHost).removeOnDestinationChangedListener(this)
        }
    }

    override fun onBackPressed() {
        val navController = findNavController(R.id.fNavHost)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fNavHost)
        val backStackEntryCount = navHostFragment?.childFragmentManager?.backStackEntryCount

        if ((navController.currentDestination?.id == R.id.chatRoomListFragment ||
                navController.currentDestination?.id == R.id.signInFragment)
            && backStackEntryCount == 0) {

            if (backPressed) {
                super.onBackPressed()
                return
            }

            backPressed = true
            Toast.makeText(this, getString(R.string.press_back_once_more), Toast.LENGTH_SHORT)
                .show()

            Handler().postDelayed(
                { backPressed = false },
                2000L
            )
        } else {
            super.onBackPressed()
        }
    }

    /**
     * Bottom Navigation view visibility listener function. It shows or hides Bottom Navigation.
     */
    private fun setNavControllerListener(navController: NavController) {
        this.navListener = NavController.OnDestinationChangedListener { nav, destination, _ ->
            when (destination.id) {
                R.id.chatRoomFragment,
                R.id.signInFragment,
                R.id.chatRoomEditFragment -> showBottomNavigation(false, bottomNavigation)
                else -> showBottomNavigation(true, bottomNavigation)
            }
        }.also { listener ->
            navController.addOnDestinationChangedListener(listener)
        }
    }

    private fun showBottomNavigation(show: Boolean, bottomNavigation: BottomNavigationView) {
        val (visibility, distance) = if (show) {
            View.VISIBLE to 0f
        } else {
            View.GONE to 250f
        }

        ObjectAnimator.ofFloat(bottomNavigation, Const.TRANSLATION_Y, distance).apply {
            duration = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
            addListener(onStart = { bottomNavigation.visibility = visibility })
            start()
        }
    }
}
