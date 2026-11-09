package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.sample.SampleShinobi
import com.example.data.sample.ShinobiRole
import com.example.ui.theme.*

/**
 * C05 Portrait Card:
 * Standard ninja roster card. 86 x 118 dp (or adaptive), rounded corners,
 * role badge chip, level indicator, character emblem/avatar, name strip at bottom,
 * and distinct SELECTED (gold border + checkmark) and DEPLOYED states.
 */
@Composable
fun PortraitCard(
    shinobi: SampleShinobi,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    isDeployed: Boolean = shinobi.isDeployed,
    isLocked: Boolean = !shinobi.isOwned,
    onClick: () -> Unit = {}
) {
    val borderColor = when {
        isSelected -> NarutoGold
        isDeployed -> LeafGreen
        isLocked -> ShinobiSteel.copy(alpha = 0.4f)
        else -> WoodBorder
    }

    val borderWidth = if (isSelected) 2.5.dp else 1.5.dp

    Card(
        modifier = modifier
            .width(92.dp)
            .height(124.dp)
            .shadow(if (isSelected) 6.dp else 2.dp, RoundedCornerShape(8.dp))
            .border(borderWidth, borderColor, RoundedCornerShape(8.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isLocked) NightNavySurface else NightNavyCard
        )
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Character artwork area
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Top row: Role badge and Level
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(3.dp))
                            .background(shinobi.role.badgeColor)
                            .padding(horizontal = 4.dp, vertical = 1.dp)
                    ) {
                        Text(
                            text = shinobi.role.label.take(3).uppercase(),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White
                            )
                        )
                    }

                    Text(
                        text = "Lv.${shinobi.level}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = ParchmentBg
                        )
                    )
                }

                // Middle: Illustrated Shinobi Face Portrait
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .border(1.5.dp, borderColor, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    if (isLocked) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Locked",
                            tint = ShinobiSteel,
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        ShinobiFacePortrait(
                            shinobiId = shinobi.id,
                            modifier = Modifier.size(54.dp)
                        )
                    }
                }

                // 3-Star Rating Pips (AFK Arena / Mythic Heroes Tier)
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(3) {
                        Text(
                            text = "★",
                            color = NarutoGold,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }

                // Bottom name plate
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(4.dp))
                        .background(InkCharcoal)
                        .padding(vertical = 2.dp, horizontal = 2.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = shinobi.name.split(" ").first(),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) NarutoGold else ParchmentBg
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Top-right corner tag: In Squad / Deployed tag
            if (isDeployed) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 2.dp, y = (-2).dp)
                        .clip(RoundedCornerShape(bottomStart = 6.dp))
                        .background(LeafGreen)
                        .padding(horizontal = 4.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "SQUAD",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 7.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White
                        )
                    )
                }
            }

            // Selected checkmark overlay
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(4.dp)
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(NarutoGold),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Selected",
                        tint = InkCharcoal,
                        modifier = Modifier.size(12.dp)
                    )
                }
            }
        }
    }
}

/**
 * AFK Arena / Mythic Heroes Full SSR Hero Splash Card:
 * Renders full anime character splash art with sculpted gold bezel,
 * 5 stars, role tag, and level pill.
 */
@Composable
fun SsrHeroCard(
    shinobi: SampleShinobi,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .width(104.dp)
            .height(144.dp)
            .clickable(onClick = onClick)
    ) {
        ShinobiSplashPortrait(
            shinobiId = shinobi.id,
            modifier = Modifier.fillMaxSize(),
            rarity = "SSR",
            stars = 5,
            level = shinobi.level,
            shape = RoundedCornerShape(10.dp)
        )

        if (isSelected) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .border(2.5.dp, NarutoOrange, RoundedCornerShape(10.dp))
            )
        }
    }
}

/**
 * C06 Item Tile:
 * 88-100dp square art cell, item quality tier border, count badge, price strip,
 * sold out / owned overlay.
 */
@Composable
fun ItemTile(
    name: String,
    iconEmoji: String,
    priceText: String,
    quantity: String,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    isSoldOut: Boolean = false,
    isOwned: Boolean = false,
    qualityColor: Color = ShinobiSteel,
    onClick: () -> Unit = {}
) {
    val borderColor = when {
        isSelected -> NarutoGold
        isSoldOut -> ShinobiSteel.copy(alpha = 0.3f)
        else -> qualityColor
    }

    Card(
        modifier = modifier
            .width(96.dp)
            .height(112.dp)
            .shadow(if (isSelected) 4.dp else 1.dp, RoundedCornerShape(6.dp))
            .border(if (isSelected) 2.dp else 1.dp, borderColor, RoundedCornerShape(6.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) NightNavyLight else NightNavyCard
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top row with quantity
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 2.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = quantity,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = ChakraCyan
                    )
                )
            }

            // Middle Item Graphic
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(NightNavySurface)
                    .border(1.dp, WoodBorder, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                when {
                    name.contains("Kunai", ignoreCase = true) -> KunaiSetItemArt(modifier = Modifier.size(34.dp))
                    name.contains("Vest", ignoreCase = true) -> ChuninVestItemArt(modifier = Modifier.size(34.dp))
                    name.contains("Pill", ignoreCase = true) -> SoldierPillsItemArt(modifier = Modifier.size(34.dp))
                    name.contains("Scroll", ignoreCase = true) || name.contains("Seal", ignoreCase = true) -> RecruitmentSealScroll(modifier = Modifier.size(32.dp))
                    else -> ToolShopAnvilCrest(modifier = Modifier.size(32.dp))
                }

                if (isSoldOut) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.6f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "SOLD",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black,
                                color = VillageVermilion
                            )
                        )
                    }
                }
            }

            // Bottom strip: Item name and price
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(InkCharcoal)
                    .padding(vertical = 3.dp, horizontal = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = ParchmentBg
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = if (isOwned) "Owned" else priceText,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 8.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isOwned) LeafGreen else NarutoGold
                    )
                )
            }
        }
    }
}

/**
 * C10 Character Stage:
 * Character standing on an oval ground shadow with chakra aura / role token and level badge.
 */
@Composable
fun CharacterStage(
    shinobi: SampleShinobi,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    showAura: Boolean = true
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(100.dp, 120.dp)
        ) {
            // Oval ground shadow
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .width(72.dp)
                    .height(16.dp)
                    .clip(RoundedCornerShape(50))
                    .background(
                        Brush.radialGradient(
                            colors = listOf(Color.Black.copy(alpha = 0.6f), Color.Transparent)
                        )
                    )
            )

            // Chakra Aura glow when active/selected
            if (showAura) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    if (isSelected) NarutoGold.copy(alpha = 0.35f) else ChakraCyan.copy(alpha = 0.2f),
                                    Color.Transparent
                                )
                            )
                        )
                )
            }

            // Illustrated Character Portrait with Jutsu Aura
            ShinobiFacePortrait(
                shinobiId = shinobi.id,
                modifier = Modifier.size(76.dp)
            )
        }

        // Base plate under character
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = NightNavySurface,
            border = BorderStroke(1.dp, if (isSelected) NarutoGold else WoodBorder),
            modifier = Modifier.padding(top = 4.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(shinobi.role.badgeColor)
                )
                Text(
                    text = shinobi.name,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = ParchmentBg,
                        fontSize = 11.sp
                    )
                )
                Text(
                    text = "Lv.${shinobi.level}",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = ChakraCyan,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
    }
}

/**
 * C11 Formation Pad:
 * Tactical 5-slot pad layout (2 Front, 3 Rear).
 * Supports empty pad state vs occupied pad state with occupant shinobi.
 */
@Composable
fun FormationSlotPad(
    slotIndex: Int,
    isFrontLine: Boolean,
    occupant: SampleShinobi?,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    onClick: () -> Unit = {}
) {
    val borderColor = when {
        isSelected -> NarutoGold
        occupant != null -> LeafGreen
        else -> WoodBorder
    }

    Surface(
        modifier = modifier
            .width(62.dp)
            .height(72.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        color = if (occupant != null) NightNavyCard else NightNavySurface.copy(alpha = 0.7f),
        border = BorderStroke(if (isSelected) 2.dp else 1.dp, borderColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(3.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Position header
            Text(
                text = if (isFrontLine) "FRONT" else "REAR",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 7.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isFrontLine) VillageVermilion else ChakraCyan
                )
            )

            // Content
            if (occupant != null) {
                ShinobiFacePortrait(
                    shinobiId = occupant.id,
                    modifier = Modifier.size(32.dp)
                )
                Text(
                    text = occupant.name.split(" ").first(),
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold,
                        color = ParchmentBg
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .border(1.dp, ShinobiSteel.copy(alpha = 0.4f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "+",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = ShinobiSteel.copy(alpha = 0.6f),
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                Text(
                    text = "Slot ${slotIndex + 1}",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 7.sp,
                        color = ShinobiSteel
                    )
                )
            }
        }
    }
}

/**
 * C12 Status Token:
 * Indicator glyph for combat states: Wound, Silence, Stun, Mark, Shield, Taunt, Cleanse.
 */
enum class StatusEffectType(
    val label: String,
    val glyph: String,
    val color: Color
) {
    WOUND("Wound", "🩸", VillageVermilion),
    SILENCE("Silence", "🤐", GenjutsuPurple),
    STUN("Stun", "⚡", AmberYellow),
    MARK("Mark", "🎯", VillageVermilion),
    SHIELD("Shield", "🛡️", ChakraCyan),
    TAUNT("Taunt", "💢", NarutoOrange),
    CLEANSE("Cleanse", "🍃", HealGreen)
}

@Composable
fun StatusToken(
    effect: StatusEffectType,
    modifier: Modifier = Modifier,
    stacks: Int = 1
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(4.dp),
        color = effect.color.copy(alpha = 0.25f),
        border = BorderStroke(1.dp, effect.color)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(text = effect.glyph, fontSize = 10.sp)
            Text(
                text = effect.label,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold,
                    color = ParchmentBg
                )
            )
            if (stacks > 1) {
                Text(
                    text = "x$stacks",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Black,
                        color = AmberYellow
                    )
                )
            }
        }
    }
}
