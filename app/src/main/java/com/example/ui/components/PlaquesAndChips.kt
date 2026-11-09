package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.*

/**
 * C03 — Account / Resource Header (Section 06):
 * 56dp normal height (or 88dp two-row at large text scale).
 * Warm paper surface (#F2E5CB / #FFF5DF) with thin indigo edge seam (#223447 / #B7A98E).
 * Left: Portrait 40dp within 48dp envelope (Naruto portrait), name "Konoha Genin" (15sp ink #26323A).
 * Right: Two compact resource groups:
 *   - Ryo coin (18-20dp) + "1,280" (15sp tabular ink)
 *   - Recruitment seals scroll (18-20dp) + "12" (15sp tabular ink)
 * Clean: NO plus badges, NO invented level badge, NO glowing rings.
 */
@Composable
fun AccountResourceHeader(
    modifier: Modifier = Modifier,
    accountName: String = "Konoha Genin",
    ryoAmount: String = "1,280",
    sealAmount: String = "12",
    isLargeText: Boolean = false,
    onProfileClick: () -> Unit = {}
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(if (isLargeText) 88.dp else 56.dp),
        color = PaperLight,
        shadowElevation = 3.dp,
        border = BorderStroke(1.dp, PaperEdge)
    ) {
        if (!isLargeText) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Left: Player Profile Envelope (48dp with 40dp circular portrait)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.clickable { onProfileClick() }
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(PaperBase)
                            .border(1.5.dp, BrassAccent, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.portrait_naruto),
                            contentDescription = "Player Portrait: $accountName",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                        )
                    }

                    Text(
                        text = accountName,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = InkCharcoal,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    )
                }

                // Right: Two compact resource tokens
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Currency 1: Ryo Gold Coins
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        KonohaRyoCoin(modifier = Modifier.size(19.dp))
                        Text(
                            text = ryoAmount,
                            style = MaterialTheme.typography.labelLarge.copy(
                                color = InkCharcoal,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        )
                    }

                    // Currency 2: Recruitment Scrolls / Seals
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        RecruitmentSealScroll(modifier = Modifier.size(19.dp))
                        Text(
                            text = sealAmount,
                            style = MaterialTheme.typography.labelLarge.copy(
                                color = InkCharcoal,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        )
                    }
                }
            }
        } else {
            // 88dp Two-Row Variant for Large Text / Accessibility
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 14.dp, vertical = 6.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(PaperBase)
                            .border(1.dp, BrassAccent, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.portrait_naruto),
                            contentDescription = accountName,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(32.dp).clip(CircleShape)
                        )
                    }
                    Text(
                        text = accountName,
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = InkCharcoal,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        KonohaRyoCoin(modifier = Modifier.size(18.dp))
                        Text(
                            text = ryoAmount,
                            style = MaterialTheme.typography.labelLarge.copy(
                                color = InkCharcoal,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        RecruitmentSealScroll(modifier = Modifier.size(18.dp))
                        Text(
                            text = sealAmount,
                            style = MaterialTheme.typography.labelLarge.copy(
                                color = InkCharcoal,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        )
                    }
                }
            }
        }
    }
}

/**
 * C05 — Building Plaque (Section 06):
 * 28–32dp tall paper-on-wood plaque holding 14sp title (#26323A).
 * Normal: Warm ivory paper with dark timber border (#7C5C38) and brass rivets (#B68B4E).
 * Selected: Restrained edge light (#B68B4E) or brighter plaque fill (#FFF5DF).
 * Locked: Steel padlock + short lock condition.
 */
@Composable
fun BuildingPlaque(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    subLabel: String? = subtitle,
    iconEmoji: String? = null,
    notificationCount: Int? = null,
    badgeText: String? = null,
    isSelected: Boolean = false,
    isLocked: Boolean = false,
    lockCondition: String? = null,
    onClick: () -> Unit = {}
) {
    val plaqueBg = if (isSelected) {
        Brush.verticalGradient(listOf(Color(0xFFFFFBF0), PaperLight))
    } else if (isLocked) {
        Brush.verticalGradient(listOf(Color(0xFFE0D8C8), Color(0xFFC8BEAC)))
    } else {
        Brush.verticalGradient(listOf(PaperLight, PaperBase))
    }

    val timberBorderColor = if (isSelected) BrassAccent else TimberBorder
    val borderWidth = if (isSelected) 2.dp else 1.5.dp

    Column(
        modifier = modifier.clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            shape = RoundedCornerShape(4.dp),
            color = TimberWood,
            shadowElevation = if (isSelected) 6.dp else 3.dp,
            border = BorderStroke(borderWidth, timberBorderColor)
        ) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 2.dp, vertical = 2.dp)
                    .background(plaqueBg, RoundedCornerShape(2.dp))
                    .padding(horizontal = 10.dp, vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    if (isLocked) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Locked",
                            tint = DangerInk,
                            modifier = Modifier
                                .size(14.dp)
                                .padding(end = 4.dp)
                        )
                    }

                    Text(
                        text = title,
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = if (isLocked) MutedInk else InkCharcoal,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.3.sp
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        val effectiveSub = subLabel ?: subtitle
        if (isLocked && lockCondition != null) {
            Spacer(modifier = Modifier.height(2.dp))
            Surface(
                shape = RoundedCornerShape(3.dp),
                color = DeepCloth.copy(alpha = 0.85f),
                border = BorderStroke(0.5.dp, DangerInk)
            ) {
                Text(
                    text = lockCondition,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp),
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = ButtonTextCream,
                        fontSize = 9.5.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center
                )
            }
        } else if (effectiveSub != null) {
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = effectiveSub,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MutedInk,
                    fontSize = 10.sp
                )
            )
        }
    }
}

/**
 * C18 — Empty / Locked / Error State Presentation (Section 06):
 * Small local illustration, clear heading, short explanation, and one next-action specimen.
 */
@Composable
fun EmptyLockedState(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    isLocked: Boolean = true,
    actionLabel: String? = null,
    onActionClick: () -> Unit = {}
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        color = PaperLight,
        border = BorderStroke(1.dp, PaperEdge),
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(PaperBase)
                    .border(1.dp, if (isLocked) DangerInk else PaperEdge, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                if (isLocked) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Locked",
                        tint = DangerInk,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    RecruitmentSealScroll(modifier = Modifier.size(26.dp))
                }
            }

            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = InkCharcoal,
                    fontSize = 16.sp
                ),
                textAlign = TextAlign.Center
            )

            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MutedInk,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                ),
                textAlign = TextAlign.Center
            )

            if (actionLabel != null) {
                Spacer(modifier = Modifier.height(4.dp))
                PrimaryActionButton(
                    text = actionLabel,
                    onClick = onActionClick
                )
            }
        }
    }
}

/**
 * Legacy compatibility chips
 */
@Composable
fun ResourceChip(
    iconEmoji: String = "",
    modifier: Modifier = Modifier,
    amount: String = "",
    label: String? = null,
    icon: (@Composable () -> Unit)? = null,
    onClick: () -> Unit = {}
) {
    val displayAmount = if (amount.isNotEmpty()) amount else (label ?: "")
    Surface(
        color = PaperLight,
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.dp, PaperEdge),
        modifier = modifier.height(32.dp).clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            if (icon != null) {
                icon()
            } else {
                KonohaRyoCoin(modifier = Modifier.size(16.dp))
            }
            Text(
                text = displayAmount,
                style = MaterialTheme.typography.labelMedium.copy(
                    color = InkCharcoal,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            )
        }
    }
}

@Composable
fun NotificationMarker(
    count: Int? = null,
    modifier: Modifier = Modifier,
    color: Color = LacquerOrange
) {
    Box(
        modifier = modifier
            .size(if (count != null) 16.dp else 8.dp)
            .background(color, CircleShape)
            .border(1.dp, PaperLight, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        if (count != null && count > 0) {
            Text(
                text = if (count > 9) "9+" else count.toString(),
                style = MaterialTheme.typography.labelSmall.copy(
                    color = ButtonTextCream,
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@Composable
fun ProgressRail(
    current: Long,
    max: Long,
    label: String,
    modifier: Modifier = Modifier,
    valueText: String? = null,
    trackColor: Color = PaperEdge,
    fillColor: Color = LacquerOrange
) {
    val progress = if (max > 0) (current.toFloat() / max.toFloat()).coerceIn(0f, 1f) else 0f
    val displayValue = valueText ?: "$current / $max"

    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MutedInk,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
            )
            Text(
                text = displayValue,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = InkCharcoal,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(trackColor)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(progress)
                    .clip(RoundedCornerShape(3.dp))
                    .background(fillColor)
            )
        }
    }
}

@Composable
fun ProgressRail(
    current: Int,
    max: Int,
    label: String,
    modifier: Modifier = Modifier,
    valueText: String? = null,
    trackColor: Color = PaperEdge,
    fillColor: Color = LacquerOrange
) {
    ProgressRail(
        current = current.toLong(),
        max = max.toLong(),
        label = label,
        modifier = modifier,
        valueText = valueText,
        trackColor = trackColor,
        fillColor = fillColor
    )
}
