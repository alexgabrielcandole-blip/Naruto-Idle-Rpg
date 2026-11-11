package com.example.ui.screens.squad

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.FormationStage
import com.example.ui.components.IllustratedBottomDock

/**
 * UI-007 — Shinobi Squad Management Screen:
 * Complete visual continuity with Home (VillageScreen):
 *
 * 1. Continuous Illustrated Background:
 *    - Full-bleed illustrated Konoha terrace (bg_squad_home_matched) from top to bottom.
 *    - Paving extends seamlessly behind formation, between panels, and behind the lower drawer.
 *
 * 2. Dual-Tier Header:
 *    - Tier 1: Player profile avatar (gold-rimmed Naruto), "Konoha Genin", "Hidden Leaf Village",
 *      and floating currency capsules (Ryo: 1,280 with 3D coin, Seals: 12 with scroll).
 *    - Tier 2: Gold Leaf emblem, "Squad" title (26sp), "Hall 10 · 4 of 5 deployed",
 *      and "Training Hall" pill button with archery target icon.
 *    - Sits under a shallow teal gradient scrim over the sky/Hokage rock panorama.
 *
 * 3. Five-Slot Formation Stage:
 *    - Takes all remaining height on standard/tall screens; scrolls gracefully on compact heights.
 *    - Enemy approach indicator and row badges ("FRONT" and "REAR").
 *    - 4 field characters + 1 open slot, firmly grounded on terrace stone paving.
 *    - Early-era Naruto art (Gaara, standing Sasuke, Neji, Naruto selected with radiant gold ring).
 *
 * 4. Selected Shinobi Inspector Panel (Naruto Uzumaki):
 *    - Deep teal lacquer gradient matching Home's mission strip with warm brass border (#AE8C55).
 *    - Square illustrated portrait frame with warm ambient background.
 *    - "REAR CENTER", "Naruto Uzumaki", "Damage Dealer · Hybrid / Flex", "Reliable frontline damage.".
 *    - Glossy cinnabar orange "View Shinobi" button with gold rim (#DE8B3A).
 *    - Deep teal "Move" button.
 *
 * 5. Owned Shinobi Drawer:
 *    - "Owned shinobi" (4 owned) with category filter pills (All [active], Tank, Damage Dealer, Support).
 *    - 4 starter roster cards (Gaara, Sasuke, Neji, Naruto) with warm illustrated backgrounds.
 *    - Naruto highlighted with glowing gold border and gold nameplate text.
 *    - Finishes ~8-12 dp above the footer dock.
 *
 * 6. Persistent Bottom Dock:
 *    - 4 destinations: Home, Squad (Selected with gold icon, gold label, and underline), Campaign, Recruit.
 */
@Composable
fun SquadScreen(
    modifier: Modifier = Modifier,
    onNavigateToHome: () -> Unit = {}
) {
    // Current active filter tab (All, Tank, Damage Dealer, Support)
    var selectedFilter by remember { mutableStateOf("All") }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF06111A))
    ) {
        // =========================================================================
        // 1. CONTINUOUS BACKGROUND: FULL-BLEED KONOHA TERRACE ARTWORK
        // =========================================================================
        Image(
            painter = painterResource(id = R.drawable.bg_squad_home_matched),
            contentDescription = "Konoha Terrace Panorama",
            contentScale = ContentScale.Crop,
            alignment = Alignment.TopCenter,
            modifier = Modifier.fillMaxSize()
        )

        // =========================================================================
        // 2. MAIN CONTENT COLUMN (Header, Scrollable Stage & Panels, Fixed Dock)
        // =========================================================================
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // ---------------------------------------------------------------------
            // DUAL-TIER TOP HEADER (Home-matched Account Presentation & Squad Subheader)
            // ---------------------------------------------------------------------
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color(0xFF071B22).copy(alpha = 0.94f),
                                Color(0xFF0A252E).copy(alpha = 0.86f),
                                Color(0x000A252E)
                            )
                        )
                    )
                    .statusBarsPadding()
                    .padding(bottom = 6.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 2.dp)
                ) {
                    // Row 1: Player Profile Avatar & Currency Capsules (Carried from Home)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Profile Avatar & Player Identity
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier
                                .clip(RoundedCornerShape(24.dp))
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = ripple(color = Color(0xFFFFD54F))
                                ) { /* Inert presentation */ }
                                .testTag("header_profile")
                                .semantics {
                                    contentDescription = "Konoha Genin Player Profile"
                                    role = Role.Button
                                }
                        ) {
                            // Gold-framed circular avatar
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF0F2C37))
                                    .border(BorderStroke(2.dp, Color(0xFFFFD54F)), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.avatar_naruto_polished),
                                    contentDescription = "Naruto Avatar",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }

                            Column {
                                Text(
                                    text = "Konoha Genin",
                                    style = androidx.compose.ui.text.TextStyle(
                                        fontSize = 14.5.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color(0xFFFFF2D7),
                                        letterSpacing = 0.2.sp
                                    )
                                )
                                Text(
                                    text = "Hidden Leaf Village",
                                    style = androidx.compose.ui.text.TextStyle(
                                        fontSize = 11.5.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color(0xFFCAD5D3)
                                    )
                                )
                            }
                        }

                        // Currency Capsules
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            // Ryo Capsule
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(18.dp))
                                    .background(Color(0xFF0A222B).copy(alpha = 0.92f))
                                    .border(BorderStroke(1.2.dp, Color(0xFFAE8C55)), RoundedCornerShape(18.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                                    .testTag("capsule_ryo")
                                    .semantics {
                                        contentDescription = "Ryo Currency: 1,280"
                                        role = Role.Button
                                    },
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.img_konoha_ryo_coin),
                                    contentDescription = "Ryo Coin",
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clip(CircleShape)
                                )
                                Text(
                                    text = "1,280",
                                    style = androidx.compose.ui.text.TextStyle(
                                        fontSize = 12.5.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color(0xFFFFF2D7)
                                    )
                                )
                            }

                            // Seals Capsule
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(18.dp))
                                    .background(Color(0xFF0A222B).copy(alpha = 0.92f))
                                    .border(BorderStroke(1.2.dp, Color(0xFFAE8C55)), RoundedCornerShape(18.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                                    .testTag("capsule_seals")
                                    .semantics {
                                        contentDescription = "Recruitment Seals: 12"
                                        role = Role.Button
                                    },
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_dock_recruit),
                                    contentDescription = "Recruitment Seals Scroll",
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = "12",
                                    style = androidx.compose.ui.text.TextStyle(
                                        fontSize = 12.5.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color(0xFFFFF2D7)
                                    )
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    // Row 2: Squad Title & Training Hall Pill
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Squad Title & Hall Level Subtitle with Leaf Emblem
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_dock_home),
                                contentDescription = "Hidden Leaf Emblem",
                                modifier = Modifier.size(28.dp)
                            )
                            Column {
                                Text(
                                    text = "Squad",
                                    modifier = Modifier.testTag("header_title_squad"),
                                    style = androidx.compose.ui.text.TextStyle(
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color(0xFFFFF2D7),
                                        letterSpacing = 0.5.sp
                                    )
                                )
                                Spacer(modifier = Modifier.height(1.dp))
                                Text(
                                    text = "Hall 10 · 4 of 5 deployed",
                                    style = androidx.compose.ui.text.TextStyle(
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = Color(0xFFCAD5D3)
                                    )
                                )
                            }
                        }

                        // Training Hall Pill Button
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color(0xFF0C242C).copy(alpha = 0.95f))
                                .border(BorderStroke(1.2.dp, Color(0xFFAE8C55)), RoundedCornerShape(20.dp))
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = ripple(color = Color(0xFFFFD54F))
                                ) { /* Inert presentation */ }
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                                .testTag("btn_training_hall")
                                .semantics {
                                    contentDescription = "Training Hall"
                                    role = Role.Button
                                },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_training_target),
                                contentDescription = "Training Target",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                            )

                            Text(
                                text = "Training Hall",
                                style = androidx.compose.ui.text.TextStyle(
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFFFF2D7)
                                )
                            )
                        }
                    }
                }
            }

            // ---------------------------------------------------------------------
            // SCROLLABLE BODY (Formation Stage, Inspector Panel, Owned Shinobi Drawer)
            // ---------------------------------------------------------------------
            BoxWithConstraints(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                val availableHeight = maxHeight
                val scrollState = rememberScrollState()

                // Layout calculations:
                // Inspector height ~96dp, Owned drawer ~132dp, margins/spacers ~20dp -> lowerPanels ~248dp.
                // On tall/standard viewports, formation fills all remaining space so roster finishes 8-12dp above footer.
                // On compact screens (e.g. 640dp), formation keeps minimum 325dp and column scrolls smoothly.
                val lowerPanelsHeight = 248.dp
                val dynamicFormationHeight = if (availableHeight > lowerPanelsHeight + 320.dp) {
                    availableHeight - lowerPanelsHeight
                } else {
                    330.dp
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // 1. Five-Slot Formation Stage: Characters grounded on the stone terrace
                    FormationStage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(dynamicFormationHeight),
                        selectedShinobi = "Naruto",
                        onSlotSelected = { /* Inert presentation */ }
                    )

                    // -----------------------------------------------------------------
                    // 2. SELECTED SHINOBI INSPECTOR PANEL (Naruto Uzumaki)
                    // Styled matching Home's mission strip lacquer & warm brass borders
                    // -----------------------------------------------------------------
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 2.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        Color(0xFF0F323D),
                                        Color(0xFF0B252E),
                                        Color(0xFF081C23)
                                    )
                                )
                            )
                            .border(BorderStroke(1.5.dp, Color(0xFFAE8C55)), RoundedCornerShape(12.dp))
                            .padding(horizontal = 10.dp, vertical = 8.dp)
                            .testTag("inspector_panel")
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Left: Close-up Portrait Frame with warm illustrated background
                            Box(
                                modifier = Modifier
                                    .size(66.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .border(BorderStroke(1.5.dp, Color(0xFFAE8C55)), RoundedCornerShape(8.dp))
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.inspector_portrait_naruto),
                                    contentDescription = "Naruto Uzumaki Portrait",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }

                            Spacer(modifier = Modifier.width(10.dp))

                            // Middle: Formation Position, Name, Role, Tactical Description
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "REAR CENTER",
                                    style = androidx.compose.ui.text.TextStyle(
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF8EAFB5),
                                        letterSpacing = 0.8.sp
                                    )
                                )
                                Text(
                                    text = "Naruto Uzumaki",
                                    style = androidx.compose.ui.text.TextStyle(
                                        fontSize = 16.5.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color(0xFFFFF2D7)
                                    )
                                )
                                Text(
                                    text = "Damage Dealer · Hybrid / Flex",
                                    style = androidx.compose.ui.text.TextStyle(
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color(0xFF8EAFB5)
                                    )
                                )
                                Text(
                                    text = "Reliable frontline damage.",
                                    style = androidx.compose.ui.text.TextStyle(
                                        fontSize = 11.5.sp,
                                        fontStyle = FontStyle.Italic,
                                        color = Color(0xFFCAD5D3)
                                    )
                                )
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            // Right: "View Shinobi" (Primary CTA) and "Move" Buttons
                            Column(
                                horizontalAlignment = Alignment.End,
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                // Primary CTA: "View Shinobi" (MANDATORY EXACT LABEL)
                                Box(
                                    modifier = Modifier
                                        .width(122.dp)
                                        .height(38.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(
                                            Brush.verticalGradient(
                                                listOf(
                                                    Color(0xFFC75B22),
                                                    Color(0xFFA94B17),
                                                    Color(0xFF8A3B10)
                                                )
                                            )
                                        )
                                        .border(BorderStroke(1.5.dp, Color(0xFFDE8B3A)), RoundedCornerShape(8.dp))
                                        .clickable(
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = ripple(color = Color(0xFFFFD54F))
                                        ) { /* Inert presentation */ }
                                        .testTag("btn_view_shinobi")
                                        .semantics {
                                            contentDescription = "View Shinobi"
                                            role = Role.Button
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "View Shinobi",
                                        style = androidx.compose.ui.text.TextStyle(
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = Color.White
                                        )
                                    )
                                }

                                // Secondary Action: "Move"
                                Box(
                                    modifier = Modifier
                                        .width(122.dp)
                                        .height(32.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color(0xFF0E2A34))
                                        .border(BorderStroke(1.dp, Color(0xFF265766)), RoundedCornerShape(8.dp))
                                        .clickable(
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = ripple(color = Color(0xFF90CAF9))
                                        ) { /* Inert presentation */ }
                                        .testTag("btn_move_shinobi")
                                        .semantics {
                                            contentDescription = "Move"
                                            role = Role.Button
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Move",
                                        style = androidx.compose.ui.text.TextStyle(
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFFCAD5D3)
                                        )
                                    )
                                }
                            }
                        }
                    }

                    // -----------------------------------------------------------------
                    // 3. OWNED SHINOBI DRAWER
                    // -----------------------------------------------------------------
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        Color(0xFF0C2731),
                                        Color(0xFF092028),
                                        Color(0xFF06181E)
                                    )
                                )
                            )
                            .border(
                                BorderStroke(1.2.dp, Color(0xFFAE8C55)),
                                RoundedCornerShape(12.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 6.dp)
                            .testTag("owned_shinobi_drawer")
                    ) {
                        Column {
                            // Drawer Header: Title & Filter Pills
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                // Title & Count
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = "Owned shinobi",
                                        style = androidx.compose.ui.text.TextStyle(
                                            fontSize = 14.5.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFFFFF2D7)
                                        )
                                    )
                                    Text(
                                        text = "4 owned",
                                        style = androidx.compose.ui.text.TextStyle(
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Normal,
                                            color = Color(0xFFCAD5D3)
                                        )
                                    )
                                }

                                // Category Filter Pills: All | Tank | Damage Dealer | Support
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    FilterPill(
                                        label = "All",
                                        isSelected = selectedFilter == "All",
                                        onClick = { selectedFilter = "All" }
                                    )
                                    FilterPill(
                                        label = "Tank",
                                        isSelected = selectedFilter == "Tank",
                                        onClick = { selectedFilter = "Tank" }
                                    )
                                    FilterPill(
                                        label = "Damage Dealer",
                                        isSelected = selectedFilter == "Damage Dealer",
                                        onClick = { selectedFilter = "Damage Dealer" }
                                    )
                                    FilterPill(
                                        label = "Support",
                                        isSelected = selectedFilter == "Support",
                                        onClick = { selectedFilter = "Support" }
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            // Roster Row: 4 Starters (Gaara, Sasuke, Neji, Naruto)
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                RosterCard(
                                    name = "Gaara",
                                    imageRes = R.drawable.roster_portrait_gaara,
                                    isSelected = false,
                                    modifier = Modifier.weight(1f).padding(horizontal = 3.dp),
                                    testTag = "card_owned_gaara"
                                )
                                RosterCard(
                                    name = "Sasuke",
                                    imageRes = R.drawable.roster_portrait_sasuke,
                                    isSelected = false,
                                    modifier = Modifier.weight(1f).padding(horizontal = 3.dp),
                                    testTag = "card_owned_sasuke"
                                )
                                RosterCard(
                                    name = "Neji",
                                    imageRes = R.drawable.roster_portrait_neji,
                                    isSelected = false,
                                    modifier = Modifier.weight(1f).padding(horizontal = 3.dp),
                                    testTag = "card_owned_neji"
                                )
                                RosterCard(
                                    name = "Naruto",
                                    imageRes = R.drawable.roster_portrait_naruto,
                                    isSelected = true,
                                    modifier = Modifier.weight(1f).padding(horizontal = 3.dp),
                                    testTag = "card_owned_naruto"
                                )
                            }
                        }
                    }

                    // 8-12dp spacing above the footer dock at normal scale
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            // ---------------------------------------------------------------------
            // 4. PERSISTENT FOUR-DESTINATION BOTTOM NAVIGATION DOCK
            // ---------------------------------------------------------------------
            IllustratedBottomDock(
                selectedTab = "Squad",
                onTabSelected = { tabId ->
                    if (tabId.equals("Home", ignoreCase = true)) {
                        onNavigateToHome()
                    }
                }
            )
        }
    }
}

// =============================================================================
// SUB-COMPOSABLES & REUSABLE UI ELEMENTS
// =============================================================================

/**
 * Filter Pill in Owned Shinobi Drawer.
 */
@Composable
private fun FilterPill(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(
                if (isSelected) Color(0xFFB68B42) else Color.Transparent
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = Color(0xFFFFD54F))
            ) { onClick() }
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Text(
            text = label,
            style = androidx.compose.ui.text.TextStyle(
                fontSize = 10.5.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) Color(0xFF091C24) else Color(0xFFCAD5D3)
            )
        )
    }
}

/**
 * Roster Card in Owned Shinobi Drawer.
 */
@Composable
private fun RosterCard(
    name: String,
    imageRes: Int,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    testTag: String
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(Color(0xFF0F2B35))
            .border(
                BorderStroke(
                    width = if (isSelected) 2.2.dp else 1.2.dp,
                    color = if (isSelected) Color(0xFFFFD54F) else Color(0xFFAE8C55)
                ),
                RoundedCornerShape(6.dp)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = Color(0xFFFFD54F))
            ) { /* Inert presentation */ }
            .testTag(testTag),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Square-ish Portrait Bust with warm illustrated background
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        // Bottom Name Plaque
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0B2129))
                .padding(vertical = 3.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = name,
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected) Color(0xFFFFD54F) else Color(0xFFFFF2D7)
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}
