package com.example.robolectric

import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowApplication
import android.content.Intent
import android.widget.Button
import android.widget.TextView
import org.junit.Test
import org.robolectric.Robolectric
import org.junit.Assert.*


@RunWith(RobolectricTestRunner::class)
class MainActivityTest {
    @Test
    fun clickingGo_shouldWriteToTextView() {
        val activity = Robolectric.setupActivity( MainActivity::class.java!!)
        activity.findViewById<Button>(R.id.go).performClick()
        assertEquals("Clicked", activity.findViewById<TextView>( R.id.tv).text)
    }
}
