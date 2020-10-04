package com.example.screenshottesting

import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import org.junit.Before
import org.junit.Test

class TestSampleListItemView : ScreenshotTest() {

    private lateinit var configurations: List<DeviceConfiguration>

    @Before
    override fun setUp() {
        super.setUp()

        configurations = getDeviceConfigurationsFor(WRAP_CONTENT)
    }

    @Test
    fun testSampleListItemView() {
        configurations.forEach { configuration ->
            updateResources(configuration.locale, configuration.isDarkTheme)

            val view = SampleListItemView(context)

            record(view, configuration)
        }
    }
}
