package com.cardforge.app

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cardforge.app.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val composeTestRule =
        createAndroidComposeRule<MainActivity>()

    @Test
    fun appLaunch_showsTitle() {

        composeTestRule
            .onNodeWithText("CardForge")
            .assertExists()

    }
}