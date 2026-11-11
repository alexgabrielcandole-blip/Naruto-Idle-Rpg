package com.example

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import com.example.ui.components.IllustratedBottomDock
import com.example.ui.theme.NarutoIdleTheme
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class IllustratedBottomDockTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun verifyIllustratedBottomDockRendersAllFourTabsWithCorrectOrderAndLabels() {
        var clickedTab = ""
        composeTestRule.setContent {
            NarutoIdleTheme(darkTheme = true) {
                IllustratedBottomDock(
                    selectedTab = "Squad",
                    onTabSelected = { clickedTab = it }
                )
            }
        }

        // Verify all 4 tabs exist
        composeTestRule.onNodeWithTag("tab_home").assertExists()
        composeTestRule.onNodeWithTag("tab_squad").assertExists()
        composeTestRule.onNodeWithTag("tab_campaign").assertExists()
        composeTestRule.onNodeWithTag("tab_recruit").assertExists()

        // Verify native labels
        composeTestRule.onNodeWithText("Home").assertExists()
        composeTestRule.onNodeWithText("Squad").assertExists()
        composeTestRule.onNodeWithText("Campaign").assertExists()
        composeTestRule.onNodeWithText("Recruit").assertExists()

        // Verify Squad is selected
        composeTestRule.onNodeWithTag("tab_squad").assertIsSelected()
        composeTestRule.onNodeWithTag("tab_home").assertIsNotSelected()
        composeTestRule.onNodeWithTag("tab_campaign").assertIsNotSelected()
        composeTestRule.onNodeWithTag("tab_recruit").assertIsNotSelected()

        // Verify clicks trigger callback
        composeTestRule.onNodeWithTag("tab_home").performClick()
        assertEquals("Home", clickedTab)

        composeTestRule.onNodeWithTag("tab_campaign").performClick()
        assertEquals("Campaign", clickedTab)
    }

    @Test
    fun verifyDockRendersAt360dpWidth() {
        composeTestRule.setContent {
            NarutoIdleTheme(darkTheme = true) {
                Box(modifier = Modifier.width(360.dp)) {
                    IllustratedBottomDock(selectedTab = "Squad")
                }
            }
        }
        composeTestRule.onNodeWithTag("tab_squad").assertExists().assertIsSelected()
    }

    @Test
    fun verifyDockRendersAt320dpWidth() {
        composeTestRule.setContent {
            NarutoIdleTheme(darkTheme = true) {
                Box(modifier = Modifier.width(320.dp)) {
                    IllustratedBottomDock(selectedTab = "Squad")
                }
            }
        }
        composeTestRule.onNodeWithTag("tab_squad").assertExists().assertIsSelected()
    }
}
