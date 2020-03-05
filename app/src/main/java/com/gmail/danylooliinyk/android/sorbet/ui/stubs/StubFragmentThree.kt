package com.gmail.danylooliinyk.android.sorbet.ui.stubs

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.gmail.danylooliinyk.android.sorbet.R

/**
 * StubFragmentOne
 */
class StubFragmentThree : Fragment(R.layout.fragment_stub_three) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(view) {
            findViewById<View>(R.id.llRoot).setOnClickListener {
                Toast.makeText(
                    this.context,
                    this@StubFragmentThree::class.java.simpleName,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
