package com.example.ui.screens.squad

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ripple
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
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

/**
 * UI-007 — Shinobi Squad Management Screen:
 * Faithfully follows the approved visual design from "03_Squad_Visual_Reference.png":
 *
 * 1. Top Header: Deep teal header panel with ivory "Squad" title (26sp),
 *    "Hall 10 · 4 of 5 deployed" subtitle, and "Training Hall" pill button with scroll emblem.
 *
 * 2. Courtyard Stage (Painted Konoha Assembly Grounds):
 *    - Center-rear Japanese torii gate with green curved tile eaves, red Konoha banners, and Hokage rock face.
 *    - "Enemy approach" indicator plaque with downward pointing golden arrow.
 *    - Left-edge "FRONT" and "REAR" echelon badges.
 *    - Five-Slot Formation:
 *      * Front Left: Gaara (Tank — Guardian / Protector) on brass-rimmed wooden pad with "Gaara" plaque.
 *      * Front Right: Sasuke (Damage Dealer — Finisher / Duelist) with kunai on pad with "Sasuke" plaque.
 *      * Rear Left: Neji (Support — Scout / Rear Access) in Gentle Fist stance with "Neji" plaque.
 *      * Rear Center: Naruto (Damage Dealer — Hybrid / Flex) SELECTED on radiant gold glowing pad with "Naruto" plaque.
 *      * Rear Right: Open slot with dashed brass pad, "+" icon, and "Open" plaque.
 *
 * 3. Selected Shinobi Inspector Panel:
 *    - Close-up portrait of Naruto Uzumaki.
 *    - "REAR CENTER", "Naruto Uzumaki", "Damage Dealer · Hybrid / Flex", "Reliable frontline damage.".
 *    - Exact action button label "View Shinobi" (lacquered cinnabar orange #A94B17 with gold rim).
 *    - Teal-framed "Move" button.
 *
 * 4. Owned Shinobi Drawer:
 *    - "Owned shinobi" (4 owned) with category filter pills (All [active], Tank, Damage Dealer, Support).
 *    - 4 starter roster cards: Gaara, Sasuke, Neji, Naruto (Naruto highlighted with radiant gold frame).
 *
 * 5. Persistent Bottom Dock:
 *    - 4 destinations: Home, Squad (Selected with glowing gold icon and underline), Campaign, Recruit.
 */
@Composable
fun SquadScreen(
    modifier: Modifier = Modifier,
    onNavigateToHome: () -> Unit = {}
) {
    // Current active filter tab (All, Tank, Damage Dealer, Support)
    var selectedFilter by remember { mutableStateOf("All") }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF06111A))
    ) {
        val screenHeight = maxHeight
        val screenWidth = maxWidth

        // =========================================================================
        // 1. COURTYARD BACKGROUND LAYER (Upper ~52% of screen)
        // =========================================================================
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight * 0.54f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.bg_konoha_courtyard),
                contentDescription = "Konoha Courtyard Training Grounds",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Soft transition gradient into lower dark teal panels
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Transparent, Color(0xFF091C24).copy(alpha = 0.85f))
                        )
                    )
            )
        }

        // =========================================================================
        // 2. MAIN CONTENT COLUMN (Header, Courtyard Formation, Inspector, Drawer, Dock)
        // =========================================================================
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // ---------------------------------------------------------------------
            // TOP HEADER: SQUAD TITLE & TRAINING HALL PILL
            // ---------------------------------------------------------------------
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color(0xFF0A2028).copy(alpha = 0.95f),
                                Color(0xFF0D252F).copy(alpha = 0.88f),
                                Color(0x000D252F)
                            )
                        )
                    )
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Squad Title & Hall Level Subtitle
                    Column {
                        Text(
                            text = "Squad",
                            modifier = Modifier.testTag("header_title_squad"),
                            style = androidx.compose.ui.text.TextStyle(
                                fontSize = 28.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFFFFF2D7),
                                letterSpacing = 0.5.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(1.dp))
                        Text(
                            text = "Hall 10 · 4 of 5 deployed",
                            style = androidx.compose.ui.text.TextStyle(
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFFCAD5D3)
                            )
                        )
                    }

                    // Training Hall Pill Button
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color(0xFF102B35))
                            .border(BorderStroke(1.2.dp, Color(0xFFAE8C55)), RoundedCornerShape(20.dp))
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = ripple(color = Color(0xFFFFD54F))
                            ) { /* Inert presentation */ }
                            .padding(horizontal = 14.dp, vertical = 7.dp)
                            .testTag("btn_training_hall")
                            .semantics {
                                contentDescription = "Training Hall"
                                role = Role.Button
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Miniature Jutsu Scroll Emblem
                        Box(
                            modifier = Modifier
                                .size(18.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(Color(0xFFE0C498))
                                .border(1.dp, Color(0xFFAE8C55), RoundedCornerShape(3.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFA94B17))
                            )
                        }

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

            // ---------------------------------------------------------------------
            // COURTYARD FORMATION STAGE (Height calculated to match reference)
            // ---------------------------------------------------------------------
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                // Enemy Approach Indicator (Centered at top of courtyard)
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0xFF0F2C36).copy(alpha = 0.92f))
                            .border(BorderStroke(1.dp, Color(0xFFAE8C55)), RoundedCornerShape(6.dp))
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                            .testTag("indicator_enemy_approach")
                    ) {
                        Text(
                            text = "Enemy approach",
                            style = androidx.compose.ui.text.TextStyle(
                                fontSize = 11.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFFF2D7)
                            )
                        )
                    }

                    // Downward Gold Triangle Arrow
                    Canvas(
                        modifier = Modifier
                            .size(width = 12.dp, height = 7.dp)
                            .padding(top = 1.dp)
                    ) {
                        val path = Path().apply {
                            moveTo(0f, 0f)
                            lineTo(size.width, 0f)
                            lineTo(size.width / 2f, size.height)
                            close()
                        }
                        drawPath(path = path, color = Color(0xFFFFD54F))
                    }
                }

                // Row Labels on Left Edge ("FRONT" & "REAR")
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(80.dp)
                ) {
                    RowBadge(text = "FRONT", testTag = "badge_front_row")
                    RowBadge(text = "REAR", testTag = "badge_rear_row")
                }

                // =================================================================
                // 5 FORMATION SLOTS (2 FRONT, 3 REAR)
                // =================================================================

                // --- FRONT ROW (2 Slots: Gaara, Sasuke) ---
                Row(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 56.dp)
                        .fillMaxWidth(0.68f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    // Front Left: Gaara (Tank — Guardian / Protector)
                    FormationSlot(
                        shinobiName = "Gaara",
                        drawableRes = R.drawable.figure_gaara,
                        isSelected = false,
                        isOpen = false,
                        characterWidth = 72.dp,
                        characterHeight = 156.dp,
                        testTag = "slot_front_left_gaara"
                    )

                    // Front Right: Sasuke (Damage Dealer — Finisher / Duelist)
                    FormationSlot(
                        shinobiName = "Sasuke",
                        drawableRes = R.drawable.figure_sasuke,
                        isSelected = false,
                        isOpen = false,
                        characterWidth = 84.dp,
                        characterHeight = 156.dp,
                        testTag = "slot_front_right_sasuke"
                    )
                }

                // --- REAR ROW (3 Slots: Neji, Naruto [Selected], Open) ---
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 6.dp)
                        .fillMaxWidth(0.94f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    // Rear Left: Neji (Support — Scout / Rear Access)
                    FormationSlot(
                        shinobiName = "Neji",
                        drawableRes = R.drawable.figure_neji,
                        isSelected = false,
                        isOpen = false,
                        characterWidth = 96.dp,
                        characterHeight = 162.dp,
                        testTag = "slot_rear_left_neji"
                    )

                    // Rear Center: Naruto (Damage Dealer — Hybrid / Flex) [SELECTED]
                    FormationSlot(
                        shinobiName = "Naruto",
                        drawableRes = R.drawable.figure_naruto,
                        isSelected = true,
                        isOpen = false,
                        characterWidth = 78.dp,
                        characterHeight = 168.dp,
                        testTag = "slot_rear_center_naruto"
                    )

                    // Rear Right: Open Slot
                    FormationSlot(
                        shinobiName = "Open",
                        drawableRes = 0,
                        isSelected = false,
                        isOpen = true,
                        characterWidth = 78.dp,
                        characterHeight = 130.dp,
                        testTag = "slot_rear_right_open"
                    )
                }
            }

            // ---------------------------------------------------------------------
            // 3. SELECTED SHINOBI INSPECTOR PANEL (Naruto Uzumaki)
            // ---------------------------------------------------------------------
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 4.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF0F2B35))
                    .border(BorderStroke(1.5.dp, Color(0xFFAE8C55)), RoundedCornerShape(12.dp))
                    .padding(horizontal = 10.dp, vertical = 8.dp)
                    .testTag("inspector_panel")
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Left: Close-up Portrait Frame
                    Box(
                        modifier = Modifier
                            .size(66.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(BorderStroke(1.5.dp, Color(0xFFAE8C55)), RoundedCornerShape(8.dp))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.roster_portrait_naruto),
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
                                color = Color(0xFFCAD5D3),
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

                    // Right: "View Shinobi" and "Move" Buttons
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
                                        listOf(Color(0xFFBA541E), Color(0xFFA94B17))
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
                                .background(Color(0xFF102B35))
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

            // ---------------------------------------------------------------------
            // 4. OWNED SHINOBI DRAWER
            // ---------------------------------------------------------------------
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                    .background(Color(0xFF0C222B))
                    .border(
                        BorderStroke(1.2.dp, Color(0xFFAE8C55)),
                        RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
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

            // ---------------------------------------------------------------------
            // 5. PERSISTENT FOUR-DESTINATION BOTTOM NAVIGATION DOCK
            // ---------------------------------------------------------------------
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF071720))
                    .border(BorderStroke(1.dp, Color(0xFF102F3A)))
                    .navigationBarsPadding()
                    .height(68.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Tab 1: Home (Navigates back to Home screen)
                    NavDockTab(
                        title = "Home",
                        iconRes = R.drawable.ic_nav_home_raw,
                        isSelected = false,
                        onClick = onNavigateToHome,
                        testTag = "tab_home"
                    )

                    // Tab 2: Squad (CURRENT SELECTED TAB - Highlighted in Radiant Gold)
                    NavDockTab(
                        title = "Squad",
                        iconRes = R.drawable.ic_nav_squad_raw,
                        isSelected = true,
                        onClick = { /* Already on Squad */ },
                        testTag = "tab_squad"
                    )

                    // Tab 3: Campaign
                    NavDockTab(
                        title = "Campaign",
                        iconRes = R.drawable.ic_nav_campaign_raw,
                        isSelected = false,
                        onClick = { /* Inert */ },
                        testTag = "tab_campaign"
                    )

                    // Tab 4: Recruit
                    NavDockTab(
                        title = "Recruit",
                        iconRes = R.drawable.ic_nav_recruit_raw,
                        isSelected = false,
                        onClick = { /* Inert */ },
                        testTag = "tab_recruit"
                    )
                }
            }
        }
    }
}

// =============================================================================
// SUB-COMPOSABLES & REUSABLE UI ELEMENTS
// =============================================================================

/**
 * Formation Slot displaying an oval ground pad, character standee, and name plaque.
 */
@Composable
private fun FormationSlot(
    shinobiName: String,
    drawableRes: Int,
    isSelected: Boolean,
    isOpen: Boolean,
    characterWidth: androidx.compose.ui.unit.Dp,
    characterHeight: androidx.compose.ui.unit.Dp,
    testTag: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = Color(0xFFFFD54F))
            ) { /* Inert presentation */ }
            .testTag(testTag)
            .semantics {
                contentDescription = "$shinobiName Formation Slot"
                role = Role.Button
            }
    ) {
        // Stack of Character Standee over Ground Pedestal Pad
        Box(
            modifier = Modifier
                .width(characterWidth.coerceAtLeast(80.dp))
                .height(characterHeight),
            contentAlignment = Alignment.BottomCenter
        ) {
            // Ground Pedestal Pad
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .align(Alignment.BottomCenter)
            ) {
                val padWidth = size.width * 0.9f
                val padHeight = size.height * 0.85f
                val left = (size.width - padWidth) / 2f
                val top = (size.height - padHeight) / 2f

                // Drop shadow beneath pad
                drawOval(
                    color = Color(0x77000000),
                    topLeft = Offset(left, top + 4f),
                    size = Size(padWidth, padHeight)
                )

                if (isOpen) {
                    // Dashed neutral oval for open slot
                    drawOval(
                        color = Color(0x33000000),
                        topLeft = Offset(left, top),
                        size = Size(padWidth, padHeight)
                    )
                    drawOval(
                        color = Color(0xFFAE8C55),
                        topLeft = Offset(left, top),
                        size = Size(padWidth, padHeight),
                        style = Stroke(
                            width = 2.dp.toPx(),
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 10f), 0f)
                        )
                    )
                } else {
                    // Solid beveled wooden / brass pad
                    drawOval(
                        color = Color(0xFF4A3420),
                        topLeft = Offset(left, top),
                        size = Size(padWidth, padHeight)
                    )
                    // Inner lighter face
                    drawOval(
                        color = Color(0xFF6B4D2F),
                        topLeft = Offset(left + 2f, top + 2f),
                        size = Size(padWidth - 4f, padHeight - 4f)
                    )
                    // Brass outer rim
                    drawOval(
                        color = if (isSelected) Color(0xFFFFD54F) else Color(0xFFAE8C55),
                        topLeft = Offset(left, top),
                        size = Size(padWidth, padHeight),
                        style = Stroke(width = if (isSelected) 3.5.dp.toPx() else 1.8.dp.toPx())
                    )

                    // Radiant outer glow for selected character (Naruto)
                    if (isSelected) {
                        drawOval(
                            color = Color(0x55FFD54F),
                            topLeft = Offset(left - 6f, top - 4f),
                            size = Size(padWidth + 12f, padHeight + 8f),
                            style = Stroke(width = 2.5.dp.toPx())
                        )
                    }
                }
            }

            // Character Figure Standee (or '+' Icon for Open Slot)
            if (!isOpen && drawableRes != 0) {
                Image(
                    painter = painterResource(id = drawableRes),
                    contentDescription = shinobiName,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 12.dp)
                )
            } else if (isOpen) {
                // Large "+" sign centered in open pad
                Text(
                    text = "+",
                    modifier = Modifier.padding(bottom = 6.dp),
                    style = androidx.compose.ui.text.TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFAE8C55)
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(2.dp))

        // Name Plaque underneath pad
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(
                    if (isSelected) Color(0xFF133642) else Color(0xFF0F2630)
                )
                .border(
                    BorderStroke(1.2.dp, if (isSelected) Color(0xFFFFD54F) else Color(0xFFAE8C55)),
                    RoundedCornerShape(4.dp)
                )
                .padding(horizontal = 14.dp, vertical = 2.dp)
        ) {
            Text(
                text = shinobiName,
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected) Color(0xFFFFF2D7) else Color(0xFFE2DDD3)
                )
            )
        }
    }
}

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
        // Square-ish Portrait Bust
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

/**
 * Row Badge on Courtyard Edge ("FRONT" / "REAR").
 */
@Composable
private fun RowBadge(text: String, testTag: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(Color(0xFF0E2832).copy(alpha = 0.9f))
            .border(BorderStroke(1.dp, Color(0xFFAE8C55)), RoundedCornerShape(4.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp)
            .testTag(testTag)
    ) {
        Text(
            text = text,
            style = androidx.compose.ui.text.TextStyle(
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFF2D7),
                letterSpacing = 0.5.sp
            )
        )
    }
}

/**
 * Bottom Navigation Dock Destination Tab.
 */
@Composable
private fun NavDockTab(
    title: String,
    iconRes: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    testTag: String
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(76.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = Color(0xFFFFD54F))
            ) { onClick() }
            .testTag(testTag)
            .semantics {
                contentDescription = if (isSelected) "$title Tab (Selected)" else "$title Tab"
                role = Role.Tab
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Tab Icon
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(26.dp),
            colorFilter = if (isSelected) androidx.compose.ui.graphics.ColorFilter.tint(Color(0xFFFFD54F)) else null
        )

        Spacer(modifier = Modifier.height(2.dp))

        // Tab Title
        Text(
            text = title,
            style = androidx.compose.ui.text.TextStyle(
                fontSize = 11.5.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) Color(0xFFFFD54F) else Color(0xFFCAD5D3)
            )
        )

        Spacer(modifier = Modifier.height(2.dp))

        // Selected Underline Bar
        if (isSelected) {
            Box(
                modifier = Modifier
                    .width(32.dp)
                    .height(2.5.dp)
                    .clip(RoundedCornerShape(1.dp))
                    .background(Color(0xFFFFD54F))
            )
        } else {
            Spacer(modifier = Modifier.height(2.5.dp))
        }
    }
}
