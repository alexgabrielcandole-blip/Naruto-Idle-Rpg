package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R

/**
 * Chamfered hexagonal plaque shape matching the authentic Naruto tactical UI.
 */
val TacticalPlaqueShape = GenericShape { size, _ ->
    val c = 5.5f
    moveTo(c, 0f)
    lineTo(size.width - c, 0f)
    lineTo(size.width, c)
    lineTo(size.width, size.height - c)
    lineTo(size.width - c, size.height)
    lineTo(c, size.height)
    lineTo(0f, size.height - c)
    lineTo(0f, c)
    close()
}

/**
 * Chevron row badge shape on the stage edge ("FRONT" and "REAR").
 */
val ChevronBadgeShape = GenericShape { size, _ ->
    val c = 5f
    moveTo(0f, 0f)
    lineTo(size.width - c, 0f)
    lineTo(size.width, size.height / 2f)
    lineTo(size.width - c, size.height)
    lineTo(0f, size.height)
    close()
}

/**
 * FormationStage: Unified single-bounded coordinate space containing the painted Konoha courtyard
 * background paving and the 5-slot tactical shinobi battle formation (2 front, 3 rear).
 *
 * Design Authority & Corrections:
 * 1. Single shared coordinate space: Background (910x848) and all 5 slots share identical normalized coordinates.
 * 2. Continuous open paving: Paving extends behind both rows and their plaques to the bottom boundary.
 * 3. Standing Sasuke: Sasuke stands tall in his authentic upright stance with kunai at his side.
 * 4. Ground perspective ellipses: Beveled aged-brass rims, soft contact shadows, and radiant amber-gold ring for Naruto.
 * 5. Authentic tactical name plaques: Chamfered deep teal badges with aged-brass frames mounted at each slot's front rim.
 */
@Composable
fun FormationStage(
    modifier: Modifier = Modifier,
    selectedShinobi: String = "Naruto",
    onSlotSelected: (String) -> Unit = {}
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 330.dp)
    ) {
        val stageWidth = maxWidth
        val stageHeight = if (maxHeight != Dp.Infinity && maxHeight > 0.dp) maxHeight else 350.dp

        // =========================================================================
        // 2. ENEMY APPROACH INDICATOR (Centered Top)
        // =========================================================================
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = stageHeight * 0.038f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .clip(TacticalPlaqueShape)
                    .background(Color(0xFF0C242C).copy(alpha = 0.94f))
                    .border(BorderStroke(1.2.dp, Color(0xFFAE8C55)), TacticalPlaqueShape)
                    .padding(horizontal = 14.dp, vertical = 4.dp)
                    .testTag("indicator_enemy_approach")
            ) {
                Text(
                    text = "Enemy approach",
                    style = androidx.compose.ui.text.TextStyle(
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFF2D7),
                        letterSpacing = 0.3.sp
                    )
                )
            }

            // Downward Gold Triangle Arrow pointing to the courtyard gate
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

        // =========================================================================
        // 3. ROW BADGES ON LEFT EDGE ("FRONT" & "REAR")
        // =========================================================================
        val frontBadgeTop = stageHeight * 0.354f
        val rearBadgeTop = stageHeight * 0.811f

        StageRowBadge(
            text = "FRONT",
            testTag = "badge_front_row",
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = 6.dp, y = frontBadgeTop)
        )

        StageRowBadge(
            text = "REAR",
            testTag = "badge_rear_row",
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = 6.dp, y = rearBadgeTop)
        )

        // =========================================================================
        // 4. THE 5 FORMATION SLOTS (Normalized Coordinate Grounding)
        // =========================================================================

        // FRONT ROW: Gaara (0.334, 0.478), Sasuke (0.664, 0.478)
        // Figure height: ~31.8% of stage height
        val frontFigureHeight = stageHeight * 0.318f
        val frontEllipseWidth = stageWidth * 0.192f // ~69.dp at 360dp
        val frontEllipseHeight = stageHeight * 0.061f // ~21.dp

        // Front Left: Gaara (Tank — Guardian / Protector)
        PositionedFormationSlot(
            name = "Gaara",
            drawableRes = R.drawable.figure_gaara,
            aspectRatio = 535f / 1215f,
            figureHeight = frontFigureHeight,
            ellipseWidth = frontEllipseWidth,
            ellipseHeight = frontEllipseHeight,
            footX = stageWidth * 0.334f,
            footY = stageHeight * 0.478f,
            isSelected = selectedShinobi.equals("Gaara", ignoreCase = true),
            isOpen = false,
            testTag = "slot_front_left_gaara",
            onSlotClick = { onSlotSelected("Gaara") }
        )

        // Front Right: Standing Sasuke (Damage Dealer — Finisher / Duelist)
        PositionedFormationSlot(
            name = "Sasuke",
            drawableRes = R.drawable.figure_sasuke,
            aspectRatio = 106f / 278f,
            figureHeight = frontFigureHeight,
            ellipseWidth = frontEllipseWidth,
            ellipseHeight = frontEllipseHeight,
            footX = stageWidth * 0.664f,
            footY = stageHeight * 0.478f,
            isSelected = selectedShinobi.equals("Sasuke", ignoreCase = true),
            isOpen = false,
            testTag = "slot_front_right_sasuke",
            onSlotClick = { onSlotSelected("Sasuke") }
        )

        // REAR ROW: Neji (0.189, 0.879), Naruto (0.500, 0.879), Open (0.820, 0.879)
        // Figure height: ~34.8% of stage height
        val rearFigureHeight = stageHeight * 0.348f
        val rearEllipseWidth = stageWidth * 0.231f // ~83.dp at 360dp
        val rearEllipseHeight = stageHeight * 0.073f // ~25.dp

        // Rear Left: Neji (Support — Scout / Rear Access)
        PositionedFormationSlot(
            name = "Neji",
            drawableRes = R.drawable.figure_neji,
            aspectRatio = 781f / 1196f,
            figureHeight = rearFigureHeight,
            ellipseWidth = rearEllipseWidth,
            ellipseHeight = rearEllipseHeight,
            footX = stageWidth * 0.189f,
            footY = stageHeight * 0.879f,
            isSelected = selectedShinobi.equals("Neji", ignoreCase = true),
            isOpen = false,
            testTag = "slot_rear_left_neji",
            onSlotClick = { onSlotSelected("Neji") }
        )

        // Rear Center: Naruto (Damage Dealer — Hybrid / Flex) [SELECTED]
        PositionedFormationSlot(
            name = "Naruto",
            drawableRes = R.drawable.figure_naruto,
            aspectRatio = 494f / 1194f,
            figureHeight = rearFigureHeight * 1.02f,
            ellipseWidth = rearEllipseWidth,
            ellipseHeight = rearEllipseHeight,
            footX = stageWidth * 0.500f,
            footY = stageHeight * 0.879f,
            isSelected = selectedShinobi.equals("Naruto", ignoreCase = true),
            isOpen = false,
            testTag = "slot_rear_center_naruto",
            onSlotClick = { onSlotSelected("Naruto") }
        )

        // Rear Right: Open Slot (Dashed Ellipse + Plus Icon)
        PositionedFormationSlot(
            name = "Open",
            drawableRes = 0,
            aspectRatio = 1f,
            figureHeight = rearFigureHeight,
            ellipseWidth = rearEllipseWidth,
            ellipseHeight = rearEllipseHeight,
            footX = stageWidth * 0.820f,
            footY = stageHeight * 0.879f,
            isSelected = false,
            isOpen = true,
            testTag = "slot_rear_right_open",
            onSlotClick = { onSlotSelected("Open") }
        )
    }
}

/**
 * Individual formation slot positioned deterministically by visible foot anchor (footX, footY).
 * - Canvas draws the grounded perspective ellipse and soft contact shadow.
 * - Image draws the character standing with visible feet grounded.
 * - Plaque sits cleanly at the front rim of the ellipse.
 */
@Composable
private fun PositionedFormationSlot(
    name: String,
    drawableRes: Int,
    aspectRatio: Float,
    figureHeight: Dp,
    ellipseWidth: Dp,
    ellipseHeight: Dp,
    footX: Dp,
    footY: Dp,
    isSelected: Boolean,
    isOpen: Boolean,
    testTag: String,
    onSlotClick: () -> Unit
) {
    val figureWidth = figureHeight * aspectRatio
    val plaqueWidth = if (ellipseWidth < 74.dp) 64.dp else 72.dp
    val slotTouchWidth = maxOf(figureWidth, ellipseWidth, plaqueWidth).coerceAtLeast(60.dp)
    val slotTouchHeight = figureHeight + ellipseHeight + 16.dp

    // Bounded slot container positioned so foot anchor is at (footX, footY)
    Box(
        modifier = Modifier
            .offset(
                x = footX - (slotTouchWidth / 2f),
                y = footY - figureHeight
            )
            .size(width = slotTouchWidth, height = slotTouchHeight)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = Color(0xFFFFD54F))
            ) { onSlotClick() }
            .testTag(testTag)
            .semantics {
                contentDescription = "$name Formation Slot"
                role = Role.Button
            }
    ) {
        // Ground Ellipse & Contact Shadow
        // Center of ellipse is at relative y = figureHeight inside this box
        Canvas(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = figureHeight - (ellipseHeight / 2f))
                .size(width = ellipseWidth, height = ellipseHeight)
        ) {
            val w = size.width
            val h = size.height

            // 1. Soft contact shadow on stone paving beneath feet
            drawOval(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xB0000000),
                        Color(0x55000000),
                        Color.Transparent
                    ),
                    center = Offset(w / 2f, h / 2f),
                    radius = w / 2f
                ),
                topLeft = Offset(-2f, -1f),
                size = Size(w + 4f, h + 2f)
            )

            if (isOpen) {
                // Dashed perspective ellipse for open slot
                drawOval(
                    color = Color(0xFFA6854F),
                    topLeft = Offset(1.5f, 1.5f),
                    size = Size(w - 3f, h - 3f),
                    style = Stroke(
                        width = 1.6.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(9f, 7f), 0f)
                    )
                )
            } else if (isSelected) {
                // Radiant Amber/Gold selection ring for selected shinobi (Naruto)
                // Outer diffuse amber glow
                drawOval(
                    color = Color(0x66FFD54F),
                    topLeft = Offset(-3.5f, -2.5f),
                    size = Size(w + 7f, h + 5f),
                    style = Stroke(width = 3.5.dp.toPx())
                )
                // Main sharp radiant gold torus ring
                drawOval(
                    color = Color(0xFFFFD54F),
                    topLeft = Offset(1.5f, 1.5f),
                    size = Size(w - 3f, h - 3f),
                    style = Stroke(width = 2.2.dp.toPx())
                )
                // Translucent warm amber fill (stone paving shows through!)
                drawOval(
                    color = Color(0x30FFD54F),
                    topLeft = Offset(3f, 3f),
                    size = Size(w - 6f, h - 6f)
                )
            } else {
                // Thin perspective aged-brass beveled ring for deployed unselected characters
                // Bottom bevel shadow
                drawOval(
                    color = Color(0x55000000),
                    topLeft = Offset(2f, 3f),
                    size = Size(w - 4f, h - 4f),
                    style = Stroke(width = 2.dp.toPx())
                )
                // Main brass rim
                drawOval(
                    color = Color(0xFFAE8C55),
                    topLeft = Offset(1.5f, 1.5f),
                    size = Size(w - 3f, h - 3f),
                    style = Stroke(width = 1.8.dp.toPx())
                )
                // Inner dark ambient occlusion
                drawOval(
                    color = Color(0x20000000),
                    topLeft = Offset(3f, 3f),
                    size = Size(w - 6f, h - 6f)
                )
            }
        }

        // Character Figure Standee (or '+' Icon for Open Slot)
        if (!isOpen && drawableRes != 0) {
            Image(
                painter = painterResource(id = drawableRes),
                contentDescription = name,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .size(width = figureWidth, height = figureHeight)
            )
        } else if (isOpen) {
            // Plus Sign centered inside the open ellipse
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = figureHeight - (ellipseHeight / 2f))
                    .size(width = ellipseWidth, height = ellipseHeight),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "+",
                    style = androidx.compose.ui.text.TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFAE8C55)
                    )
                )
            }
        }

        // Tactical Hexagonal / Chamfered Name Plaque at the front rim of the ellipse
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = figureHeight + (ellipseHeight / 2f) - 6.dp)
                .clip(TacticalPlaqueShape)
                .background(
                    if (isSelected) Color(0xFF133642) else Color(0xFF0C242C)
                )
                .border(
                    BorderStroke(
                        width = if (isSelected) 1.5.dp else 1.2.dp,
                        color = if (isSelected) Color(0xFFFFD54F) else Color(0xFFAE8C55)
                    ),
                    TacticalPlaqueShape
                )
                .padding(horizontal = 12.dp, vertical = 2.dp)
        ) {
            Text(
                text = name,
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected) Color(0xFFFFD54F) else Color(0xFFFFF2D7)
                )
            )
        }
    }
}

/**
 * Chevron Row Badge on Courtyard Edge ("FRONT" / "REAR").
 */
@Composable
private fun StageRowBadge(
    text: String,
    testTag: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(ChevronBadgeShape)
            .background(Color(0xFF0C242C).copy(alpha = 0.94f))
            .border(BorderStroke(1.dp, Color(0xFFAE8C55)), ChevronBadgeShape)
            .padding(horizontal = 7.dp, vertical = 2.5.dp)
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
