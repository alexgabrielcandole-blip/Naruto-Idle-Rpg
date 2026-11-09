package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.sample.SampleShopItem
import com.example.ui.theme.*

/**
 * C08 Cloth Tabs:
 * Horizontal ninja cloth category tabs with lacquer orange active underline and badge counts.
 */
@Composable
fun ClothTabs(
    categories: List<String>,
    selectedIndex: Int,
    onSelectIndex: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(NightNavySurface)
            .border(BorderStroke(1.dp, WoodBorder), RoundedCornerShape(4.dp))
            .padding(2.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        categories.forEachIndexed { index, title ->
            val isSelected = index == selectedIndex
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(4.dp))
                    .background(if (isSelected) NarutoOrange else Color.Transparent)
                    .clickable { onSelectIndex(index) }
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = if (isSelected) InkCharcoal else ParchmentBg,
                        fontWeight = if (isSelected) FontWeight.Black else FontWeight.Medium,
                        fontSize = 12.sp
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

/**
 * C13 Item Detail Sheet / Tray:
 * Bottom inspection panel presenting item preview, name, description, tier, and purchase button.
 */
@Composable
fun ItemDetailSheet(
    item: SampleShopItem?,
    userRyo: Long,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    onPurchaseClick: () -> Unit = {}
) {
    if (item == null) return

    val canAfford = userRyo >= item.price

    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadow(12.dp, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .border(
                BorderStroke(1.5.dp, WoodBorder),
                RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            ),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        colors = CardDefaults.cardColors(containerColor = NightNavyDark)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Drag handle
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(40.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(WoodBorder)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Item header row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(NightNavySurface)
                            .border(1.dp, WoodBorder, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        when {
                            item.name.contains("Kunai", ignoreCase = true) -> KunaiSetItemArt(modifier = Modifier.size(42.dp))
                            item.name.contains("Vest", ignoreCase = true) -> ChuninVestItemArt(modifier = Modifier.size(42.dp))
                            item.name.contains("Pill", ignoreCase = true) -> SoldierPillsItemArt(modifier = Modifier.size(42.dp))
                            item.name.contains("Scroll", ignoreCase = true) || item.name.contains("Seal", ignoreCase = true) -> RecruitmentSealScroll(modifier = Modifier.size(40.dp))
                            else -> ToolShopAnvilCrest(modifier = Modifier.size(40.dp))
                        }
                    }

                    Column {
                        Text(
                            text = item.name,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = ParchmentBg
                            )
                        )
                        Text(
                            text = "${item.category.label} • ${item.quantity}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = ChakraCyan,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }

                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = ShinobiSteel
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Description text
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = NightNavySurface,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, WoodBorder)
            ) {
                Text(
                    text = item.description,
                    modifier = Modifier.padding(12.dp),
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = ParchmentBg,
                        lineHeight = 18.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Price & Purchase CTA
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Price",
                        style = MaterialTheme.typography.labelSmall.copy(color = ShinobiSteel)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        KonohaRyoCoin(modifier = Modifier.size(16.dp))
                        Text(
                            text = "${item.price} Ryo",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Black,
                                color = if (canAfford) NarutoGold else VillageVermilion
                            )
                        )
                    }
                }

                val buttonState = when {
                    item.isSoldOut -> ButtonVisualState.DISABLED
                    item.isOwned -> ButtonVisualState.LOCKED
                    !canAfford -> ButtonVisualState.DISABLED
                    else -> ButtonVisualState.NORMAL
                }

                val buttonLabel = when {
                    item.isSoldOut -> "Sold Out"
                    item.isOwned -> "Owned"
                    !canAfford -> "Need Ryo"
                    else -> "Purchase"
                }

                PrimaryActionButton(
                    text = buttonLabel,
                    visualState = buttonState,
                    lockedReason = if (item.isOwned) "Already acquired" else if (!canAfford) "Insufficient Ryo" else null
                )
            }
        }
    }
}

/**
 * C14 Confirmation Dialog:
 * Lacquer-framed parchment dialog for decisive actions (e.g. recruit multi, unlock squad slot).
 */
@Composable
fun ConfirmationDialog(
    title: String,
    message: String,
    confirmText: String = "Confirm",
    cancelText: String = "Cancel",
    onConfirm: () -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = NightNavyDark,
            border = BorderStroke(2.dp, NarutoGold),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .shadow(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Black,
                        color = NarutoGold
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = ParchmentBg,
                        lineHeight = 20.sp
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        SecondaryActionButton(
                            text = cancelText,
                            onClick = onDismiss
                        )
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        PrimaryActionButton(
                            text = confirmText
                        )
                    }
                }
            }
        }
    }
}

/**
 * C15 Message State View:
 * Loading, empty, locked, pending, completed, already-owned specimens.
 */
enum class MessageStatusType {
    LOADING,
    EMPTY,
    LOCKED,
    PENDING,
    COMPLETED
}

@Composable
fun MessageStateView(
    status: MessageStatusType,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (status) {
            MessageStatusType.LOADING -> {
                CircularProgressIndicator(
                    color = NarutoOrange,
                    modifier = Modifier.size(36.dp),
                    strokeWidth = 3.dp
                )
            }
            MessageStatusType.EMPTY -> {
                Text(text = "📜", fontSize = 42.sp)
            }
            MessageStatusType.LOCKED -> {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Locked",
                    tint = VillageVermilion,
                    modifier = Modifier.size(40.dp)
                )
            }
            MessageStatusType.PENDING -> {
                Text(text = "⏳", fontSize = 42.sp)
            }
            MessageStatusType.COMPLETED -> {
                Text(text = "💮", fontSize = 42.sp)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Bold,
                color = ParchmentBg
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodySmall.copy(
                color = ShinobiSteel,
                lineHeight = 16.sp
            ),
            textAlign = TextAlign.Center
        )
    }
}

/**
 * C16 Reward Tray:
 * Horizontal presentation ribbon of acquired items / currencies.
 */
data class RewardItem(
    val iconEmoji: String,
    val name: String,
    val amount: String
)

@Composable
fun RewardTray(
    rewards: List<RewardItem>,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        color = NightNavySurface,
        border = BorderStroke(1.dp, WoodBorder)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = "REWARDS ACQUIRED",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Black,
                    color = NarutoGold
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(rewards) { reward ->
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = NightNavyCard,
                        border = BorderStroke(1.dp, WoodBorder)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            when {
                                reward.name.contains("Ryo", ignoreCase = true) -> KonohaRyoCoin(modifier = Modifier.size(20.dp))
                                reward.name.contains("EXP", ignoreCase = true) -> ChakraExpOrb(modifier = Modifier.size(20.dp))
                                reward.name.contains("Seal", ignoreCase = true) || reward.name.contains("Scroll", ignoreCase = true) -> RecruitmentSealScroll(modifier = Modifier.size(20.dp))
                                reward.name.contains("Pill", ignoreCase = true) -> SoldierPillsItemArt(modifier = Modifier.size(20.dp))
                                else -> Text(text = reward.iconEmoji, fontSize = 20.sp)
                            }
                            Column {
                                Text(
                                    text = reward.name,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = ParchmentBg,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp
                                    )
                                )
                                Text(
                                    text = reward.amount,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = ChakraCyan,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 9.sp
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * C18 Glossary Tooltip:
 * Explanatory popover for game theory mechanics (e.g. Shared Level, Speed Initiative).
 */
@Composable
fun GlossaryTooltip(
    title: String,
    explanation: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        color = NightNavySurface,
        border = BorderStroke(1.dp, ChakraCyan.copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Glossary Info",
                tint = ChakraCyan,
                modifier = Modifier.size(18.dp)
            )
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = ChakraCyan
                    )
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = explanation,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = ParchmentBg,
                        fontSize = 11.sp,
                        lineHeight = 16.sp
                    )
                )
            }
        }
    }
}

/**
 * Ornate Wooden Dropdown Picker (AFK Arena / Mythic Heroes Marketplace Selector):
 * Carved timber drop box with brass corner brackets, selected label, rotating chevron,
 * and expandable menu with checkmark seals.
 */
@Composable
fun TimberDropDownPicker(
    label: String,
    options: List<String>,
    selectedIndex: Int,
    onSelectIndex: (Int) -> Unit,
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        // Carved Timber Drop Box Trigger
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = NightNavyDark,
            border = BorderStroke(1.5.dp, if (expanded) NarutoGold else WoodBorder),
            shadowElevation = 6.dp,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (icon != null) {
                        icon()
                    }
                    Column {
                        Text(
                            text = label.uppercase(),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Bold,
                                color = ShinobiSteel,
                                letterSpacing = 0.5.sp
                            )
                        )
                        Text(
                            text = options.getOrElse(selectedIndex) { "Select" },
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Black,
                                color = NarutoGold
                            )
                        )
                    }
                }

                // Rotating Brass Arrow
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "Expand Filter",
                    tint = NarutoGold,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        // Expandable Timber Dropdown Menu
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(NightNavyDark)
                .border(1.5.dp, NarutoGold, RoundedCornerShape(8.dp))
        ) {
            options.forEachIndexed { index, option ->
                val isSelected = index == selectedIndex
                DropdownMenuItem(
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            if (isSelected) {
                                Box(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .clip(CircleShape)
                                        .background(NarutoOrange),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Selected",
                                        tint = Color.White,
                                        modifier = Modifier.size(12.dp)
                                    )
                                }
                            } else {
                                Spacer(modifier = Modifier.size(16.dp))
                            }

                            Text(
                                text = option,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = if (isSelected) NarutoGold else ParchmentBg,
                                    fontWeight = if (isSelected) FontWeight.Black else FontWeight.Medium,
                                    fontSize = 13.sp
                                )
                            )
                        }
                    },
                    onClick = {
                        onSelectIndex(index)
                        expanded = false
                    },
                    modifier = Modifier.background(
                        if (isSelected) NightNavySurface else Color.Transparent
                    )
                )
            }
        }
    }
}

/**
 * AFK Arena & Mythic Heroes Right-Side Collapsible Drop-Down Menu:
 * A sleek sculpted golden tab attached to the right edge of the screen that expands
 * downwards into a vertical column of circular metallic shortcut buttons:
 * 1. Training Hall (Shared Level, Resonance)
 * 2. Ninja Tool Shop (Tenten's Depot)
 * 3. Hokage Dispatch (Review Atlas / Missions)
 * 4. Will of Fire (Events / Summoning)
 * 5. Specimen Gallery (C01–C20 Component Catalog)
 */
@Composable
fun AfkRightSideDropdownMenu(
    onNavigateToTraining: () -> Unit,
    onNavigateToShop: () -> Unit,
    onOpenDispatch: () -> Unit,
    onNavigateToGallery: () -> Unit,
    modifier: Modifier = Modifier,
    onOpenEvents: () -> Unit = {}
) {
    var isExpanded by remember { mutableStateOf(false) }
    val arrowRotation by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        animationSpec = tween(durationMillis = 250),
        label = "dropdown_arrow"
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End
    ) {
        // Sculpted Gold Edge Tab Trigger
        Surface(
            shape = RoundedCornerShape(topStart = 14.dp, bottomStart = 14.dp),
            color = ModernGlassFill,
            border = BorderStroke(
                1.5.dp,
                Brush.linearGradient(listOf(MetallicGoldBright, MetallicGoldDark))
            ),
            shadowElevation = 10.dp,
            modifier = Modifier
                .clickable { isExpanded = !isExpanded }
        ) {
            Row(
                modifier = Modifier.padding(start = 10.dp, end = 6.dp, top = 6.dp, bottom = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Micro Indicator / Flame Crest
                Box(
                    modifier = Modifier
                        .size(18.dp)
                        .clip(CircleShape)
                        .background(NarutoOrange.copy(alpha = 0.25f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.Close else Icons.Default.KeyboardArrowDown,
                        contentDescription = if (isExpanded) "Collapse Quick Menu" else "Expand Quick Menu",
                        tint = MetallicGoldBright,
                        modifier = Modifier.size(14.dp)
                    )
                }

                Text(
                    text = if (isExpanded) "CLOSE" else "MENU",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Black,
                        color = MetallicGoldBright,
                        letterSpacing = 0.5.sp
                    )
                )

                // Gold Chevron Tab Edge
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = null,
                    tint = MetallicGoldBright,
                    modifier = Modifier
                        .size(16.dp)
                        .rotate(arrowRotation)
                )
            }
        }

        // Animated Dropdown Vertical Column
        AnimatedVisibility(
            visible = isExpanded,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 8.dp, end = 4.dp)
                    .background(
                        color = ModernGlassFill,
                        shape = RoundedCornerShape(14.dp)
                    )
                    .border(
                        BorderStroke(
                            1.5.dp,
                            Brush.linearGradient(
                                listOf(
                                    MetallicGoldBright.copy(alpha = 0.7f),
                                    Color(0x33FFFFFF),
                                    MetallicGoldDark.copy(alpha = 0.4f)
                                )
                            )
                        ),
                        RoundedCornerShape(14.dp)
                    )
                    .padding(vertical = 8.dp, horizontal = 6.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Shortcut 1: Training Hall
                AfkDropdownShortcutItem(
                    title = "TRAIN",
                    badge = "1",
                    crest = { TrainingBladesCrest(modifier = Modifier.size(20.dp)) },
                    onClick = {
                        isExpanded = false
                        onNavigateToTraining()
                    }
                )

                // Shortcut 2: Ninja Tool Shop
                AfkDropdownShortcutItem(
                    title = "SHOP",
                    badge = null,
                    crest = { ToolShopAnvilCrest(modifier = Modifier.size(20.dp)) },
                    onClick = {
                        isExpanded = false
                        onNavigateToShop()
                    }
                )

                // Shortcut 3: Hokage Dispatch
                AfkDropdownShortcutItem(
                    title = "DISPATCH",
                    badge = null,
                    crest = { RecruitmentSealScroll(modifier = Modifier.size(20.dp)) },
                    onClick = {
                        isExpanded = false
                        onOpenDispatch()
                    }
                )

                // Shortcut 4: Will of Fire Events
                AfkDropdownShortcutItem(
                    title = "EVENTS",
                    badge = "12",
                    crest = { WillOfFireCrest(modifier = Modifier.size(20.dp)) },
                    onClick = {
                        isExpanded = false
                        onOpenEvents()
                    }
                )

                // Shortcut 5: Specimen Gallery
                AfkDropdownShortcutItem(
                    title = "GALLERY",
                    badge = null,
                    crest = { KunaiSetItemArt(modifier = Modifier.size(20.dp)) },
                    onClick = {
                        isExpanded = false
                        onNavigateToGallery()
                    }
                )
            }
        }
    }
}

@Composable
private fun AfkDropdownShortcutItem(
    title: String,
    crest: @Composable () -> Unit,
    badge: String? = null,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(4.dp)
    ) {
        Box(contentAlignment = Alignment.TopEnd) {
            // Circular Metallic Medallion
            Surface(
                shape = CircleShape,
                color = ModernSlateElevated,
                border = BorderStroke(
                    1.5.dp,
                    Brush.linearGradient(listOf(MetallicGoldBright, MetallicGoldDark))
                ),
                shadowElevation = 6.dp,
                modifier = Modifier.size(38.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    crest()
                }
            }

            // Notification Badge Pip
            if (badge != null) {
                Surface(
                    shape = CircleShape,
                    color = NarutoOrange,
                    border = BorderStroke(1.dp, Color.White),
                    modifier = Modifier
                        .offset(x = 4.dp, y = (-2).dp)
                        .size(16.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = badge,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White
                            )
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 8.sp,
                fontWeight = FontWeight.Black,
                color = MetallicGoldBright,
                letterSpacing = 0.5.sp
            )
        )
    }
}

/**
 * C06 — Four-Root Navigation (Section 05 & 06):
 * 80dp high opaque indigo cloth (#223447) with narrow upper seam (#182735 / #2C435A).
 * Exactly four equal segments in strict order: Home, Squad, Campaign, Recruit.
 * Selected segment uses paper-toned inset plate (#FFF5DF), ink icon (#26323A),
 * bold ink label (13sp), and lacquer orange underline (#A8441C).
 * Unselected segments use warm ivory (#FFF5DF / #9CA9AB).
 * NO 5th tab, NO center battle disc, NO side shortcut rail, NO unexplained Close button.
 */
@Composable
fun FourRootNavigationDock(
    selectedTab: String,
    modifier: Modifier = Modifier,
    onTabSelected: (String) -> Unit = {}
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp),
        color = IndigoCloth,
        border = BorderStroke(1.dp, DeepCloth),
        shadowElevation = 8.dp
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Narrow Upper Seam
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(Color(0xFF2E465D))
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 6.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Tab 1: Home
                val isHome = selectedTab.equals("Home", ignoreCase = true) || selectedTab.equals("Village", ignoreCase = true)
                RootNavItem(
                    label = "Home",
                    isSelected = isHome,
                    icon = { VillageDockEmblem(isSelected = isHome) },
                    modifier = Modifier.weight(1f),
                    onClick = { onTabSelected("Home") }
                )

                // Tab 2: Squad
                val isSquad = selectedTab.equals("Squad", ignoreCase = true)
                RootNavItem(
                    label = "Squad",
                    isSelected = isSquad,
                    icon = { SquadDockEmblem(isSelected = isSquad) },
                    modifier = Modifier.weight(1f),
                    onClick = { onTabSelected("Squad") }
                )

                // Tab 3: Campaign
                val isCampaign = selectedTab.equals("Campaign", ignoreCase = true)
                RootNavItem(
                    label = "Campaign",
                    isSelected = isCampaign,
                    icon = { CampaignDockEmblem(isSelected = isCampaign) },
                    modifier = Modifier.weight(1f),
                    onClick = { onTabSelected("Campaign") }
                )

                // Tab 4: Recruit
                val isRecruit = selectedTab.equals("Recruit", ignoreCase = true)
                RootNavItem(
                    label = "Recruit",
                    isSelected = isRecruit,
                    icon = { RecruitDockEmblem(isSelected = isRecruit) },
                    modifier = Modifier.weight(1f),
                    onClick = { onTabSelected("Recruit") }
                )
            }
        }
    }
}

@Composable
private fun RootNavItem(
    label: String,
    isSelected: Boolean,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .padding(horizontal = 4.dp, vertical = 2.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(if (isSelected) PaperLight else Color.Transparent)
            .border(
                BorderStroke(
                    1.dp,
                    if (isSelected) PaperEdge else Color.Transparent
                ),
                RoundedCornerShape(6.dp)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            icon()

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = 13.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                    color = if (isSelected) InkCharcoal else ButtonTextCream
                ),
                maxLines = 1
            )

            if (isSelected) {
                Spacer(modifier = Modifier.height(2.dp))
                // Orange Underline Accent (Section 05)
                Box(
                    modifier = Modifier
                        .width(20.dp)
                        .height(2.5.dp)
                        .clip(RoundedCornerShape(1.5.dp))
                        .background(LacquerOrange)
                )
            } else {
                Spacer(modifier = Modifier.height(4.5.dp))
            }
        }
    }
}


