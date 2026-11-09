package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.sample.SampleRepository
import com.example.data.sample.SampleShopItem
import com.example.data.sample.ItemCategory
import com.example.ui.theme.*

/**
 * Component Gallery (UI-095 Specimen Inspector):
 * Displays all shared components (C01–C20) across visual states for interactive design review.
 */
@Composable
fun ComponentGalleryScreen(
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val shinobiList = SampleRepository.sampleShinobiList
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var selectedShinobiIndex by remember { mutableIntStateOf(0) }
    var inspectedShopItem by remember {
        mutableStateOf<SampleShopItem?>(
            SampleShopItem(
                id = "gallery_specimen",
                name = "Soldier Pill Flask",
                category = ItemCategory.SUPPLIES,
                description = "Emergency field ration that restores 25% max HP and boosts Chakra generation for 2 rounds.",
                price = 320,
                currencyName = "Ryo",
                quantity = "x5",
                iconEmoji = "💊"
            )
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(NightNavyDark)
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "SHARED COMPONENT GALLERY (C01–C20)",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Black,
                color = NarutoOrange,
                fontSize = 16.sp
            )
        )
        Text(
            text = "Pure visual design review of all primitive widgets, states, and touch boundaries.",
            style = MaterialTheme.typography.bodySmall.copy(color = ShinobiSteel)
        )

        HorizontalDivider(color = WoodBorder)

        // C01 & C02: Action Buttons
        SectionHeader("C01 & C02 Action Buttons (Normal, Pressed, Disabled, Locked)")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                PrimaryActionButton(
                    text = "Normal CTA",
                    visualState = ButtonVisualState.NORMAL
                )
            }
            Box(modifier = Modifier.weight(1f)) {
                PrimaryActionButton(
                    text = "Pressed CTA",
                    visualState = ButtonVisualState.PRESSED
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                PrimaryActionButton(
                    text = "Disabled CTA",
                    visualState = ButtonVisualState.DISABLED
                )
            }
            Box(modifier = Modifier.weight(1f)) {
                PrimaryActionButton(
                    text = "Locked CTA",
                    visualState = ButtonVisualState.LOCKED,
                    lockedReason = "Unlocks at Lv.20"
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                SecondaryActionButton(text = "Secondary Active")
            }
            Box(modifier = Modifier.weight(1f)) {
                SecondaryActionButton(
                    text = "Secondary Disabled",
                    isEnabled = false
                )
            }
        }

        HorizontalDivider(color = WoodBorder)

        // C03 & C07: Destination Header & Resource Chips
        SectionHeader("C03 Destination Header & C07 Resource Chips")
        DestinationHeader(
            title = "TRAINING ACADEMY",
            subtitle = "Batch 1 Component Specimen",
            onBackClick = {}
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ResourceChip(label = "1,280", iconEmoji = "🪙")
            ResourceChip(label = "12", iconEmoji = "📜")
            ResourceChip(label = "3,400", iconEmoji = "⚡")
        }

        HorizontalDivider(color = WoodBorder)

        // C04: Building Plaques
        SectionHeader("C04 Building Plaques (Konoha Village Hub)")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                BuildingPlaque(
                    title = "Training Hall",
                    subtitle = "Shared Lv.10",
                    iconEmoji = "🥋",
                    notificationCount = 1
                )
            }
            Box(modifier = Modifier.weight(1f)) {
                BuildingPlaque(
                    title = "Ninja Tool Shop",
                    subtitle = "Tenten's Depot",
                    iconEmoji = "🗡️"
                )
            }
        }

        HorizontalDivider(color = WoodBorder)

        // C05: Portrait Cards
        SectionHeader("C05 Portrait Cards (Roster, Selected, Locked)")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PortraitCard(
                shinobi = shinobiList[0], // Naruto
                isSelected = selectedShinobiIndex == 0,
                onClick = { selectedShinobiIndex = 0 }
            )
            PortraitCard(
                shinobi = shinobiList[1], // Sasuke
                isSelected = selectedShinobiIndex == 1,
                onClick = { selectedShinobiIndex = 1 }
            )
            PortraitCard(
                shinobi = shinobiList[2], // Sakura
                isSelected = selectedShinobiIndex == 2,
                onClick = { selectedShinobiIndex = 2 }
            )
            PortraitCard(
                shinobi = shinobiList[7], // Tenten (locked)
                isLocked = true
            )
        }

        HorizontalDivider(color = WoodBorder)

        // C06: Item Tiles
        SectionHeader("C06 Item Tiles (Catalog, Sold Out, Owned)")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ItemTile(
                name = "Chakra Kunai",
                iconEmoji = "🗡️",
                priceText = "150 Ryo",
                quantity = "x10",
                isSelected = true
            )
            ItemTile(
                name = "Leaf Flak Vest",
                iconEmoji = "🦺",
                priceText = "600 Ryo",
                quantity = "x1",
                isSoldOut = true
            )
            ItemTile(
                name = "Secret Scroll",
                iconEmoji = "📜",
                priceText = "Owned",
                quantity = "x1",
                isOwned = true
            )
        }

        HorizontalDivider(color = WoodBorder)

        // C08: Cloth Tabs
        SectionHeader("C08 Cloth Tabs")
        ClothTabs(
            categories = listOf("All Shinobi", "Leaf Village", "Sand Village", "Support"),
            selectedIndex = selectedTabIndex,
            onSelectIndex = { selectedTabIndex = it }
        )

        HorizontalDivider(color = WoodBorder)

        // C09: Progress Rails
        SectionHeader("C09 Progress Rails (Affordable / Insufficient)")
        ProgressRail(
            current = 3400,
            max = 5000,
            fillColor = ChakraCyan,
            label = "EXP TO LEVEL 11",
            valueText = "3,400 / 5,000"
        )
        Spacer(modifier = Modifier.height(4.dp))
        ProgressRail(
            current = 1280,
            max = 2000,
            fillColor = NarutoGold,
            label = "RYO REQUIRED",
            valueText = "1,280 / 2,000"
        )

        HorizontalDivider(color = WoodBorder)

        // C10 & C11: Character Stage & Formation Pads
        SectionHeader("C10 Character Stage & C11 Formation Pads")
        CharacterStage(
            shinobi = shinobiList[selectedShinobiIndex],
            isSelected = true
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "FRONT LINE",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = VillageVermilion,
                        fontWeight = FontWeight.Bold
                    )
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FormationSlotPad(slotIndex = 0, isFrontLine = true, occupant = shinobiList[0])
                    FormationSlotPad(slotIndex = 1, isFrontLine = true, occupant = shinobiList[1])
                }
                Text(
                    text = "REAR LINE",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = ChakraCyan,
                        fontWeight = FontWeight.Bold
                    )
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FormationSlotPad(slotIndex = 2, isFrontLine = false, occupant = shinobiList[2])
                    FormationSlotPad(slotIndex = 3, isFrontLine = false, occupant = shinobiList[3])
                    FormationSlotPad(slotIndex = 4, isFrontLine = false, occupant = null)
                }
            }
        }

        HorizontalDivider(color = WoodBorder)

        // C12: Status Tokens
        SectionHeader("C12 Status Effect Tokens")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            StatusToken(effect = StatusEffectType.WOUND, stacks = 2)
            StatusToken(effect = StatusEffectType.SILENCE)
            StatusToken(effect = StatusEffectType.STUN)
            StatusToken(effect = StatusEffectType.SHIELD)
        }

        HorizontalDivider(color = WoodBorder)

        // C16: Reward Tray
        SectionHeader("C16 Reward Presentation Tray")
        RewardTray(
            rewards = listOf(
                RewardItem("🪙", "Ryo", "+450"),
                RewardItem("⚡", "Shinobi EXP", "+820"),
                RewardItem("📜", "Recruit Seal", "+1"),
                RewardItem("💊", "Soldier Pill", "+3")
            )
        )

        HorizontalDivider(color = WoodBorder)

        // C18: Glossary Tooltip
        SectionHeader("C18 Glossary Tooltip")
        GlossaryTooltip(
            title = "Shared Squad Level (The Resonance Principle)",
            explanation = "Your lowest deployed shinobi determines the battle potency of your entire team. Individual grind is eliminated in favor of squad-wide advancement."
        )

        HorizontalDivider(color = WoodBorder)

        // C15: Message States
        SectionHeader("C15 Message States")
        MessageStateView(
            status = MessageStatusType.COMPLETED,
            title = "Training Session Concluded",
            subtitle = "All 5 squad members advanced to Level 11 resonance."
        )

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelMedium.copy(
            color = NarutoGold,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
    )
}
