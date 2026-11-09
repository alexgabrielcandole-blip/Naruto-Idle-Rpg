package com.example

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.ui.screens.squad.SquadScreen
import com.example.ui.theme.NarutoIdleTheme
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class SquadScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun verifySquadScreenRendersAllRequiredComponents() {
        composeTestRule.setContent {
            NarutoIdleTheme(darkTheme = true) {
                SquadScreen()
            }
        }

        // 1. Header Verification
        composeTestRule.onNodeWithTag("header_title_squad").assertExists()
        composeTestRule.onAllNodesWithText("Squad").assertCountEquals(2) // Header title and Bottom Dock
        composeTestRule.onNodeWithText("Hall 10 · 4 of 5 deployed").assertExists()
        composeTestRule.onNodeWithTag("btn_training_hall").assertExists()
        composeTestRule.onNodeWithText("Training Hall").assertExists()

        // 2. Formation Stage Verification
        composeTestRule.onNodeWithTag("indicator_enemy_approach").assertExists()
        composeTestRule.onNodeWithText("Enemy approach").assertExists()
        composeTestRule.onNodeWithTag("badge_front_row").assertExists()
        composeTestRule.onNodeWithTag("badge_rear_row").assertExists()

        // Five formation slots
        composeTestRule.onNodeWithTag("slot_front_left_gaara").assertExists()
        composeTestRule.onNodeWithTag("slot_front_right_sasuke").assertExists()
        composeTestRule.onNodeWithTag("slot_rear_left_neji").assertExists()
        composeTestRule.onNodeWithTag("slot_rear_center_naruto").assertExists()
        composeTestRule.onNodeWithTag("slot_rear_right_open").assertExists()

        // 3. Inspector Panel Verification
        composeTestRule.onNodeWithTag("inspector_panel").assertExists()
        composeTestRule.onNodeWithText("REAR CENTER").assertExists()
        composeTestRule.onNodeWithText("Naruto Uzumaki").assertExists()
        composeTestRule.onNodeWithText("Damage Dealer · Hybrid / Flex").assertExists()
        composeTestRule.onNodeWithText("Reliable frontline damage.").assertExists()
        composeTestRule.onNodeWithTag("btn_move_shinobi").assertExists()
        composeTestRule.onNodeWithText("Move").assertExists()

        // 4. Mandatory Wording Checks: Exact "View Shinobi" & NO "dossier"
        composeTestRule.onNodeWithTag("btn_view_shinobi").assertExists()
        composeTestRule.onNodeWithText("View Shinobi").assertExists()

        // Dossier must NEVER be present anywhere
        composeTestRule.onAllNodesWithText("dossier", substring = true, ignoreCase = true)
            .assertCountEquals(0)
        composeTestRule.onAllNodesWithContentDescription("dossier", substring = true, ignoreCase = true)
            .assertCountEquals(0)

        // 5. Owned Shinobi Drawer Verification
        composeTestRule.onNodeWithTag("owned_shinobi_drawer").assertExists()
        composeTestRule.onNodeWithText("Owned shinobi").assertExists()
        composeTestRule.onNodeWithText("4 owned").assertExists()

        // Filter pills
        composeTestRule.onNodeWithText("All").assertExists()
        composeTestRule.onNodeWithText("Tank").assertExists()
        composeTestRule.onNodeWithText("Damage Dealer").assertExists()
        composeTestRule.onNodeWithText("Support").assertExists()

        // 4 Starter Cards
        composeTestRule.onNodeWithTag("card_owned_gaara").assertExists()
        composeTestRule.onNodeWithTag("card_owned_sasuke").assertExists()
        composeTestRule.onNodeWithTag("card_owned_neji").assertExists()
        composeTestRule.onNodeWithTag("card_owned_naruto").assertExists()

        // 6. Persistent Bottom Dock
        composeTestRule.onNodeWithTag("tab_home").assertExists()
        composeTestRule.onNodeWithTag("tab_squad").assertExists()
        composeTestRule.onNodeWithTag("tab_campaign").assertExists()
        composeTestRule.onNodeWithTag("tab_recruit").assertExists()
    }
}
