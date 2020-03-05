package com.gmail.danylooliinyk.android.sorbet

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.addListener
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * SorbetActivity
 */
class SorbetActivity : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView
    private var backPressed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sorbet)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fNavHost) as NavHostFragment
        this.bottomNavigation = findViewById(R.id.bottomNavigation)
        NavigationUI.setupWithNavController(bottomNavigation, navHostFragment.navController)
        bnVisibilityListener(findNavController(R.id.fNavHost))
    }

    override fun onBackPressed() {
        val navController = findNavController(R.id.fNavHost)

        if (navController.graph.startDestination == navController.currentDestination?.id) {
            if (backPressed) {
                super.onBackPressed()
                return
            }

            backPressed = true
            Toast.makeText(this, "Press back once more to exit", Toast.LENGTH_SHORT).show()

            Handler().postDelayed(
                {
                    backPressed = false
                },
                2000L
            )
        } else {
            super.onBackPressed()
        }
    }

    /**
     * Bottom Navigation view visibility listener function. It shows or hides Bottom Navigation.
     */
    private fun bnVisibilityListener(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.chatRoomFragment -> {
                    ObjectAnimator.ofFloat(bottomNavigation, "translationY", 250f).apply {
                        duration =
                            resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
                        addListener(onStart = {
                            bottomNavigation.visibility = View.GONE
                        })
                        start()
                    }
                }
                else -> {
                    ObjectAnimator.ofFloat(bottomNavigation, "translationY", 0f).apply {
                        duration =
                            resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
                        addListener(onStart = {
                            bottomNavigation.visibility = View.VISIBLE
                        })
                        start()
                    }
                }
            }
        }
    }
}
