package com.gmail.danylooliinyk.android.sorbet.ui.stubs

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.gmail.danylooliinyk.android.sorbet.R

/**
 * StubFragmentOne
 */
class StubFragmentOne : Fragment(R.layout.fragment_stub_one) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(view) {
            findViewById<View>(R.id.llRoot).setOnClickListener {
                Toast.makeText(
                    this.context,
                    this@StubFragmentOne::class.java.simpleName,
                    Toast.LENGTH_SHORT
                ).show()

                // TODO disable multiple taps
                // TODO disable open current page in Bottom View
            }
        }
    }
}
