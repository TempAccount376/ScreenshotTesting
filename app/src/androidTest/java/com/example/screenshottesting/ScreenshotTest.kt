package com.example.screenshottesting

import android.content.Context
import android.content.res.Configuration
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import com.facebook.testing.screenshot.Screenshot
import com.facebook.testing.screenshot.ViewHelpers
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestName
import java.util.*

open class ScreenshotTest {

    @get:Rule
    val permissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

    @get:Rule
    val testName = TestName()

    // Resolution DP 320 x 480, AspectRatio 3:2
    private val SCREEN_1 = ScreenParams(320, 480)

    // Resolution DP 360 x 640, AspectRatio 16:9
    private val SCREEN_2= ScreenParams(360, 640)

    // Resolution DP 480 x 800, AspectRatio 5:3
    private val SCREEN_3 = ScreenParams(480, 800)

    // Resolution DP 600 x 960, AspectRatio 8:5
    private val SCREEN_4 = ScreenParams(600, 960)

    protected lateinit var context: Context

    @Before
    open fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        context.setTheme(R.style.AppTheme)
    }

    protected fun updateResources(locale: Locale, isDarkTheme: Boolean) {
        val configuration = context.resources.configuration
        configuration.setLocale(locale)

        configuration.uiMode = configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK.inv()
        if (isDarkTheme) {
            configuration.uiMode = configuration.uiMode or Configuration.UI_MODE_NIGHT_YES
        } else {
            configuration.uiMode = configuration.uiMode or Configuration.UI_MODE_NIGHT_NO
        }

        context = context.createConfigurationContext(configuration)
    }

    protected fun record(view: View, deviceConfiguration: DeviceConfiguration) {
        if (deviceConfiguration.screenParams.height == WRAP_CONTENT) {
            ViewHelpers.setupView(view).setExactWidthDp(deviceConfiguration.screenParams.width).layout()
        } else {
            ViewHelpers.setupView(view).setExactWidthDp(deviceConfiguration.screenParams.width)
                .setExactHeightDp(deviceConfiguration.screenParams.height).layout()
        }

        Screenshot.snap(view).setName(testName.methodName + "_" + deviceConfiguration.toString()).record()
    }

    protected fun getDeviceConfigurationsFor(height: Int): List<DeviceConfiguration> {
        val devices = ArrayList<DeviceConfiguration>()
        // here we can add more languages
        val languages = arrayOf("en")

        devices.addAll(generateConfigurations(languages, SCREEN_1, false))
        devices.addAll(generateConfigurations(languages, SCREEN_4, true))

        return devices
    }

    private fun generateConfigurations(
        locales: Array<String>,
        screenParams: ScreenParams,
        isDarkTheme: Boolean
    ): List<DeviceConfiguration> {

        return locales.map {
            DeviceConfiguration(screenParams, Locale(it), isDarkTheme)
        }
    }

}