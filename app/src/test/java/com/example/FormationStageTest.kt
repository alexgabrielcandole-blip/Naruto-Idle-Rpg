package com.example

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import com.example.ui.components.FormationStage
import com.example.ui.screens.squad.SquadScreen
import com.example.ui.theme.NarutoIdleTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class FormationStageTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun verifyFormationStageSlotsAndMarkers() {
        var clickedSlot = ""
        composeTestRule.setContent {
            NarutoIdleTheme(darkTheme = true) {
                FormationStage(
                    selectedShinobi = "Naruto",
                    onSlotSelected = { clickedSlot = it }
                )
            }
        }

        // 1. Markers
        composeTestRule.onNodeWithTag("indicator_enemy_approach").assertExists()
        composeTestRule.onNodeWithText("Enemy approach").assertExists()
        composeTestRule.onNodeWithTag("badge_front_row").assertExists()
        composeTestRule.onNodeWithText("FRONT").assertExists()
        composeTestRule.onNodeWithTag("badge_rear_row").assertExists()
        composeTestRule.onNodeWithText("REAR").assertExists()

        // 2. All 5 formation slots and their name plaques
        composeTestRule.onNodeWithTag("slot_front_left_gaara").assertExists()
        composeTestRule.onNodeWithText("Gaara").assertExists()

        composeTestRule.onNodeWithTag("slot_front_right_sasuke").assertExists()
        composeTestRule.onNodeWithText("Sasuke").assertExists()

        composeTestRule.onNodeWithTag("slot_rear_left_neji").assertExists()
        composeTestRule.onNodeWithText("Neji").assertExists()

        composeTestRule.onNodeWithTag("slot_rear_center_naruto").assertExists()
        composeTestRule.onNodeWithText("Naruto").assertExists()

        composeTestRule.onNodeWithTag("slot_rear_right_open").assertExists()
        composeTestRule.onNodeWithText("Open").assertExists()
        composeTestRule.onNodeWithText("+").assertExists()

        // 3. Selection interaction
        composeTestRule.onNodeWithTag("slot_front_left_gaara").performClick()
        assertEquals("Gaara", clickedSlot)
    }

    @Test
    fun verifySquadScreenAt360x800StandardViewport() {
        composeTestRule.setContent {
            NarutoIdleTheme(darkTheme = true) {
                Box(modifier = Modifier.size(width = 360.dp, height = 800.dp)) {
                    SquadScreen()
                }
            }
        }

        // Verify formation and footer remain intact and accessible
        composeTestRule.onNodeWithTag("slot_front_left_gaara").assertExists()
        composeTestRule.onNodeWithTag("slot_front_right_sasuke").assertExists()
        composeTestRule.onNodeWithTag("slot_rear_left_neji").assertExists()
        composeTestRule.onNodeWithTag("slot_rear_center_naruto").assertExists()
        composeTestRule.onNodeWithTag("slot_rear_right_open").assertExists()
        composeTestRule.onNodeWithTag("tab_squad").assertExists().assertIsSelected()
    }

    @Test
    fun verifySquadScreenAt360x640CompactViewport() {
        composeTestRule.setContent {
            NarutoIdleTheme(darkTheme = true) {
                Box(modifier = Modifier.size(width = 360.dp, height = 640.dp)) {
                    SquadScreen()
                }
            }
        }

        // Verify all elements exist and fit into scrollable column without squeezing or crashing
        composeTestRule.onNodeWithTag("slot_front_left_gaara").assertExists()
        composeTestRule.onNodeWithTag("slot_rear_center_naruto").assertExists()
        composeTestRule.onNodeWithTag("tab_squad").assertExists().assertIsSelected()
        composeTestRule.onNodeWithTag("tab_home").assertExists()
    }
}
