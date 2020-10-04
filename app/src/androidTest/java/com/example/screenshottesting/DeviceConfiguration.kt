package com.example.screenshottesting

import android.view.ViewGroup
import java.util.*

data class DeviceConfiguration(val screenParams: ScreenParams, val locale: Locale, val isDarkTheme: Boolean) {

    override fun toString(): String {
        val height = if (screenParams.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            "WRAP_CONTENT"
        } else {
            screenParams.height
        }

        val theme = if (isDarkTheme) {
            "DarkTheme"
        } else {
            "LightTheme"
        }

        return "${screenParams.width}x${height}_${locale.displayName}_$theme"
    }
}