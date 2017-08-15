package io.github.mgarciaguerrero.safefragments.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import io.github.mgarciaguerrero.safefragments.R
import io.github.mgarciaguerrero.safefragments.extensions.addFragmentSafelfy
import io.github.mgarciaguerrero.safefragments.extensions.replaceFragmentSafely
import io.github.mgarciaguerrero.safefragments.ui.fragment.Fragment1
import io.github.mgarciaguerrero.safefragments.ui.fragment.Fragment2

class MainActivity : AppCompatActivity() {

    private lateinit var button: Button

    companion object Factory {
        private const val TAG_FRAGMENT_ONE = "fragment_one"
        private const val TAG_FRAGMENT_TWO = "fragment_two"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            replaceFragment()
        }
        // Checking if the activity is recreated
        if (savedInstanceState == null) {
            addFragment()
        }
    }

    private fun addFragment() {
        addFragmentSafelfy(
                fragment = Fragment1(),
                tag = TAG_FRAGMENT_ONE,
                containerViewId = R.id.content
        )
    }

    private fun replaceFragment() {
        replaceFragmentSafely(
                fragment = Fragment2(),
                tag = TAG_FRAGMENT_TWO,
                containerViewId = R.id.content,
                allowStateLoss = true
        )
    }
}
