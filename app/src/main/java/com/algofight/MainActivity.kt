package com.algofight

import android.app.Activity
import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val title = TextView(this).apply {
            text = getString(R.string.app_name)
            textSize = 28f
        }
        val subtitle = TextView(this).apply {
            text = getString(R.string.app_promise)
            textSize = 16f
        }
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(48, 48, 48, 48)
            addView(title)
            addView(subtitle)
        }

        setContentView(layout)
    }
}
