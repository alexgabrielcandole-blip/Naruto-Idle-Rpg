package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

enum class ButtonVisualState {
    NORMAL,
    PRESSED,
    DISABLED,
    LOCKED
}

/**
 * C01 — Primary Action Button (Section 06):
 * 52dp default height, minimum 120dp width, 16dp horizontal padding;
 * dark lacquer face (#A8441C), subtle upper light (#D67836), narrow dark bottom lip (#6B250B)
 * and cream 16sp label (#FFF5DF).
 * Corner radius 6dp with 1dp dark edge. Pressed lowers content 1dp.
 * 100% inert in this presentation build.
 */
@Composable
fun PrimaryActionButton(
    text: String,
    modifier: Modifier = Modifier,
    visualState: ButtonVisualState = ButtonVisualState.NORMAL,
    leadingIcon: ImageVector? = null,
    lockedReason: String? = null,
    onClick: () -> Unit = {}
) {
    val isEnabled = visualState == ButtonVisualState.NORMAL || visualState == ButtonVisualState.PRESSED
    val isLocked = visualState == ButtonVisualState.LOCKED
    val isDisabled = visualState == ButtonVisualState.DISABLED

    val interactionSource = remember { MutableInteractionSource() }
    val isPressedInteraction by interactionSource.collectIsPressedAsState()
    val isPressed = visualState == ButtonVisualState.PRESSED || isPressedInteraction

    val faceBrush = when {
        isDisabled -> Brush.verticalGradient(
            listOf(Color(0xFF6B7280), Color(0xFF4B5563))
        )
        isLocked -> Brush.verticalGradient(
            listOf(Color(0xFF374151), Color(0xFF1F2937))
        )
        isPressed -> Brush.verticalGradient(
            listOf(Color(0xFF8F3815), Color(0xFF752C0E))
        )
        else -> Brush.verticalGradient(
            listOf(LacquerHighlight, LacquerOrange, Color(0xFF8D3412))
        )
    }

    val lipColor = when {
        isDisabled -> Color(0xFF374151)
        isLocked -> Color(0xFF111827)
        isPressed -> Color(0xFF4D1A07)
        else -> LacquerDarkLip
    }

    val edgeStrokeColor = when {
        isDisabled -> Color(0xFF4B5563)
        isLocked -> Color(0xFF1F2937)
        isPressed -> Color(0xFF6B250B)
        else -> Color(0xFF7A2A0C)
    }

    Column(
        modifier = modifier.widthIn(min = 120.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .shadow(
                    elevation = if (isPressed) 1.dp else 4.dp,
                    shape = RoundedCornerShape(6.dp)
                )
                // 2dp Lower Lip Bevel
                .background(lipColor, RoundedCornerShape(6.dp))
                .padding(bottom = if (isPressed) 1.dp else 2.5.dp)
                // Main Face
                .clip(RoundedCornerShape(6.dp))
                .background(faceBrush)
                .border(1.dp, edgeStrokeColor, RoundedCornerShape(6.dp))
                .clickable(
                    enabled = isEnabled,
                    interactionSource = interactionSource,
                    indication = ripple()
                ) {
                    onClick() // Inert presentation callback
                },
            contentAlignment = Alignment.Center
        ) {
            // Upper Rim Specular Light
            if (!isDisabled && !isLocked) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(14.dp)
                        .align(Alignment.TopCenter)
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.White.copy(alpha = 0.22f), Color.Transparent)
                            )
                        )
                )
            }

            // Button Core Content (lowered 1dp when pressed)
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .offset(y = if (isPressed) 1.dp else 0.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if (isLocked) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Locked",
                        tint = ShinobiSteel,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                } else if (leadingIcon != null) {
                    Icon(
                        imageVector = leadingIcon,
                        contentDescription = null,
                        tint = ButtonTextCream,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }

                Text(
                    text = text,
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = if (isDisabled) ShinobiSteel else ButtonTextCream,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }

        if (lockedReason != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = lockedReason,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = DangerInk,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}

/**
 * C02 — Secondary Action Button (Section 06):
 * Paper face (#F2E5CB), 1dp ink/steel edge (#B7A98E), dark 16sp label (#26323A), 52dp height.
 */
@Composable
fun SecondaryActionButton(
    text: String,
    modifier: Modifier = Modifier,
    visualState: ButtonVisualState = ButtonVisualState.NORMAL,
    leadingIcon: ImageVector? = null,
    isEnabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    val activeEnabled = isEnabled && (visualState == ButtonVisualState.NORMAL || visualState == ButtonVisualState.PRESSED)
    val isPressed = visualState == ButtonVisualState.PRESSED

    val bgColor = when {
        !activeEnabled || visualState == ButtonVisualState.DISABLED -> PaperBase.copy(alpha = 0.5f)
        isPressed -> PaperEdge
        else -> PaperLight
    }

    Box(
        modifier = modifier
            .widthIn(min = 100.dp)
            .height(52.dp)
            .shadow(if (isPressed) 0.dp else 2.dp, RoundedCornerShape(6.dp))
            .clip(RoundedCornerShape(6.dp))
            .background(bgColor)
            .border(
                BorderStroke(
                    1.dp,
                    if (visualState == ButtonVisualState.DISABLED) ShinobiSteel.copy(alpha = 0.4f) else PaperEdge
                ),
                RoundedCornerShape(6.dp)
            )
            .clickable(
                enabled = activeEnabled,
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple()
            ) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            if (leadingIcon != null) {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = if (visualState == ButtonVisualState.DISABLED) MutedInk else InkCharcoal,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge.copy(
                    color = if (visualState == ButtonVisualState.DISABLED) MutedInk else InkCharcoal,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
    }
}

/**
 * C04 — Child Header (Section 06):
 * 56dp normal height; Back arrow plus readable destination title; paper texture or dark backing.
 */
@Composable
fun DestinationHeader(
    title: String,
    parentName: String? = "Village",
    subtitle: String? = null,
    onBackClick: () -> Unit = {},
    rightContent: @Composable (RowScope.() -> Unit)? = null
) {
    Surface(
        color = PaperLight,
        shadowElevation = 2.dp,
        border = BorderStroke(1.dp, PaperEdge),
        modifier = Modifier
            .fillMaxWidth()
            .height(if (subtitle != null) 64.dp else 56.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .clickable { onBackClick() }
                    .padding(horizontal = 8.dp, vertical = 6.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = LacquerOrange,
                    modifier = Modifier.size(22.dp)
                )
                if (parentName != null) {
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = parentName,
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = MutedInk,
                            fontSize = 13.sp
                        )
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = InkCharcoal,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    maxLines = 1,
                    textAlign = TextAlign.Center
                )
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MutedInk,
                            fontSize = 11.sp
                        ),
                        maxLines = 1,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Box(
                modifier = Modifier.widthIn(min = 48.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                if (rightContent != null) {
                    Row(content = rightContent)
                } else {
                    Spacer(modifier = Modifier.width(48.dp))
                }
            }
        }
    }
}

/**
 * Supporting Embossed Button for Secondary Dialogs
 */
@Composable
fun EmbossedNinjaCrestButton(
    text: String,
    modifier: Modifier = Modifier,
    visualState: ButtonVisualState = ButtonVisualState.NORMAL,
    crest: (@Composable () -> Unit)? = null,
    lockedReason: String? = null,
    onClick: () -> Unit = {}
) {
    PrimaryActionButton(
        text = text,
        modifier = modifier,
        visualState = visualState,
        lockedReason = lockedReason,
        onClick = onClick
    )
}

@Composable
fun SculptedPedestalButton(
    text: String,
    modifier: Modifier = Modifier,
    visualState: ButtonVisualState = ButtonVisualState.NORMAL,
    leadingCrest: (@Composable () -> Unit)? = null,
    trailingBadge: String? = null,
    lockedReason: String? = null,
    onClick: () -> Unit = {}
) {
    PrimaryActionButton(
        text = text,
        modifier = modifier,
        visualState = visualState,
        lockedReason = lockedReason,
        onClick = onClick
    )
}
