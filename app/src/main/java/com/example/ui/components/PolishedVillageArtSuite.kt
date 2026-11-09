package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

// =========================================================================
// POLISHED NARUTO IDLE RPG EMBLEMS & ICONS
// Faithfully matches Polished_Konoha_Village_RPG_UI_c06d05f5.png
// =========================================================================

/**
 * 1. MISSIONS EMBLEM (Top-Center Plaque)
 * Unrolled parchment mission scroll with jagged/rippled edges and a red wax swirl seal.
 */
@Composable
fun MissionsScrollEmblem(modifier: Modifier = Modifier.size(32.dp)) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Parchment paper sheet (unrolled angle)
        val parchment = Path().apply {
            moveTo(w * 0.18f, h * 0.16f)
            lineTo(w * 0.82f, h * 0.12f)
            lineTo(w * 0.88f, h * 0.80f)
            lineTo(w * 0.78f, h * 0.88f)
            lineTo(w * 0.16f, h * 0.86f)
            lineTo(w * 0.12f, h * 0.22f)
            close()
        }
        drawPath(
            path = parchment,
            brush = Brush.linearGradient(
                listOf(Color(0xFFFFF7E2), Color(0xFFF0DEC0), Color(0xFFDCC49E))
            )
        )
        // Rolled top scroll tube
        drawRoundRect(
            brush = Brush.verticalGradient(listOf(Color(0xFF8A5A2B), Color(0xFF5D3A18))),
            topLeft = Offset(w * 0.10f, h * 0.10f),
            size = Size(w * 0.78f, h * 0.14f),
            cornerRadius = CornerRadius(3f, 3f)
        )
        // Rolled bottom scroll tube
        drawRoundRect(
            brush = Brush.verticalGradient(listOf(Color(0xFF8A5A2B), Color(0xFF5D3A18))),
            topLeft = Offset(w * 0.12f, h * 0.80f),
            size = Size(w * 0.78f, h * 0.14f),
            cornerRadius = CornerRadius(3f, 3f)
        )

        // Red Vermilion Wax Leaf Spiral Seal in center of parchment
        val sealCenter = Offset(w * 0.50f, h * 0.48f)
        drawCircle(
            brush = Brush.radialGradient(
                listOf(Color(0xFFFF5252), Color(0xFFD32F2F), Color(0xFF8B0000)),
                center = sealCenter,
                radius = w * 0.18f
            ),
            radius = w * 0.16f,
            center = sealCenter
        )
        // Red Leaf swirl emblem on wax
        val leafSwirl = Path().apply {
            moveTo(sealCenter.x, sealCenter.y - 4f)
            cubicTo(sealCenter.x + 5f, sealCenter.y - 4f, sealCenter.x + 5f, sealCenter.y + 4f, sealCenter.x, sealCenter.y + 4f)
            cubicTo(sealCenter.x - 4f, sealCenter.y + 4f, sealCenter.x - 4f, sealCenter.y - 1f, sealCenter.x, sealCenter.y - 1f)
        }
        drawPath(path = leafSwirl, color = Color(0xFFFFD54F), style = Stroke(1.8f, cap = StrokeCap.Round))

        // Red tassel cords hanging from bottom
        drawLine(color = Color(0xFFD32F2F), start = Offset(w * 0.35f, h * 0.86f), end = Offset(w * 0.32f, h * 0.98f), strokeWidth = 2f)
        drawLine(color = Color(0xFFD32F2F), start = Offset(w * 0.65f, h * 0.86f), end = Offset(w * 0.68f, h * 0.98f), strokeWidth = 2f)
    }
}

/**
 * 2. TRAINING HALL EMBLEM (Upper-Left Plaque)
 * Wooden archery practice target with red bullseye rings and a steel kunai blade stuck into the upper-left.
 */
@Composable
fun TrainingTargetEmblem(modifier: Modifier = Modifier.size(32.dp)) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val targetCenter = Offset(w * 0.46f, h * 0.52f)

        // Wooden A-Frame Stand Legs
        drawLine(color = Color(0xFF6B4522), start = targetCenter, end = Offset(w * 0.22f, h * 0.94f), strokeWidth = 3f, cap = StrokeCap.Round)
        drawLine(color = Color(0xFF6B4522), start = targetCenter, end = Offset(w * 0.70f, h * 0.94f), strokeWidth = 3f, cap = StrokeCap.Round)
        drawLine(color = Color(0xFF533418), start = targetCenter, end = Offset(w * 0.46f, h * 0.96f), strokeWidth = 3f, cap = StrokeCap.Round)

        // Outer Wooden Target Rim
        drawCircle(
            brush = Brush.radialGradient(listOf(Color(0xFFEEDBB5), Color(0xFFC7A978), Color(0xFF8F6E40)), center = targetCenter),
            radius = w * 0.34f,
            center = targetCenter
        )
        drawCircle(color = Color(0xFF5D3A18), radius = w * 0.34f, center = targetCenter, style = Stroke(2f))

        // White Target Face
        drawCircle(color = Color(0xFFFAF7EE), radius = w * 0.28f, center = targetCenter)

        // Red Bullseye Outer Ring
        drawCircle(color = Color(0xFFD32F2F), radius = w * 0.20f, center = targetCenter, style = Stroke(width = 3.5f))

        // Red Bullseye Center
        drawCircle(color = Color(0xFFD32F2F), radius = w * 0.08f, center = targetCenter)

        // Steel Kunai Blade Stuck into Upper-Left of Target
        val kunaiStart = Offset(w * 0.12f, h * 0.14f)
        val kunaiImpact = Offset(targetCenter.x - w * 0.10f, targetCenter.y - h * 0.10f)

        // Kunai blade
        val blade = Path().apply {
            moveTo(kunaiImpact.x, kunaiImpact.y)
            lineTo(kunaiImpact.x - 4f, kunaiImpact.y - 12f)
            lineTo(kunaiStart.x, kunaiStart.y)
            lineTo(kunaiImpact.x + 8f, kunaiImpact.y - 6f)
            close()
        }
        drawPath(path = blade, brush = Brush.linearGradient(listOf(Color(0xFFECEFF1), Color(0xFF90A4AE), Color(0xFF455A64))))

        // Kunai handle & ring pommel
        drawLine(color = Color(0xFFEEEEEE), start = kunaiStart, end = Offset(kunaiStart.x - 6f, kunaiStart.y - 6f), strokeWidth = 3f)
        drawCircle(color = Color(0xFF607D8B), radius = 3.5f, center = Offset(kunaiStart.x - 8f, kunaiStart.y - 8f), style = Stroke(2f))
    }
}

/**
 * 3. TOOL SHOP EMBLEM (Upper-Right Plaque)
 * Brown leather ninja tool pouch with a strapped buckle and a steel kunai blade tucked behind it.
 */
@Composable
fun ToolShopPouchEmblem(modifier: Modifier = Modifier.size(32.dp)) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Steel Kunai Blade Tucked Behind Pouch (Angled down)
        val kunaiTip = Offset(w * 0.88f, h * 0.82f)
        val kunaiHilt = Offset(w * 0.65f, h * 0.22f)
        val blade = Path().apply {
            moveTo(kunaiTip.x, kunaiTip.y)
            lineTo(kunaiTip.x - 12f, kunaiTip.y - 8f)
            lineTo(kunaiHilt.x, kunaiHilt.y)
            lineTo(kunaiTip.x - 4f, kunaiTip.y - 14f)
            close()
        }
        drawPath(path = blade, brush = Brush.linearGradient(listOf(Color(0xFFCFD8DC), Color(0xFF78909C), Color(0xFF37474F))))
        // Kunai Pommel Ring
        drawCircle(color = Color(0xFF546E7A), radius = 4f, center = Offset(kunaiHilt.x - 4f, kunaiHilt.y - 4f), style = Stroke(2f))

        // Leather Tool Pouch Body (Brown rich leather)
        val pouchRect = Rect(w * 0.12f, h * 0.28f, w * 0.74f, h * 0.88f)
        drawRoundRect(
            brush = Brush.verticalGradient(
                listOf(Color(0xFF9E6534), Color(0xFF7C4B24), Color(0xFF543014))
            ),
            topLeft = Offset(pouchRect.left, pouchRect.top),
            size = Size(pouchRect.width, pouchRect.height),
            cornerRadius = CornerRadius(6f, 6f)
        )
        drawRoundRect(
            color = Color(0xFF3E2109),
            topLeft = Offset(pouchRect.left, pouchRect.top),
            size = Size(pouchRect.width, pouchRect.height),
            cornerRadius = CornerRadius(6f, 6f),
            style = Stroke(1.5f)
        )

        // Pouch Folded Flap
        val flap = Path().apply {
            moveTo(pouchRect.left, pouchRect.top)
            lineTo(pouchRect.right, pouchRect.top)
            lineTo(pouchRect.right - 4f, pouchRect.top + pouchRect.height * 0.52f)
            lineTo(pouchRect.left + pouchRect.width * 0.5f, pouchRect.top + pouchRect.height * 0.64f)
            lineTo(pouchRect.left + 4f, pouchRect.top + pouchRect.height * 0.52f)
            close()
        }
        drawPath(
            path = flap,
            brush = Brush.verticalGradient(
                listOf(Color(0xFFB0733E), Color(0xFF865127), Color(0xFF5C3314))
            )
        )
        drawPath(path = flap, color = Color(0xFF3E2109), style = Stroke(1.5f))

        // Golden Metal Buckle & Strap
        drawRoundRect(
            color = Color(0xFFFFD54F),
            topLeft = Offset(pouchRect.left + pouchRect.width * 0.40f, pouchRect.top + pouchRect.height * 0.44f),
            size = Size(w * 0.18f, h * 0.18f),
            cornerRadius = CornerRadius(2f, 2f)
        )
        drawRoundRect(
            color = Color(0xFF3E2109),
            topLeft = Offset(pouchRect.left + pouchRect.width * 0.43f, pouchRect.top + pouchRect.height * 0.47f),
            size = Size(w * 0.12f, h * 0.12f),
            cornerRadius = CornerRadius(1.5f, 1.5f)
        )
    }
}

/**
 * 4. ARCHIVE EMBLEM (Mid-Left Plaque)
 * Three stacked rolled wooden ninja scrolls (two on bottom, one on top) tied with red silk cords.
 */
@Composable
fun ArchiveScrollsEmblem(modifier: Modifier = Modifier.size(32.dp)) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Scroll 1: Bottom-Left
        drawRolledScrollTube(this, Offset(w * 0.08f, h * 0.56f), Size(w * 0.46f, h * 0.28f), Color(0xFFD2B58A))
        // Scroll 2: Bottom-Right
        drawRolledScrollTube(this, Offset(w * 0.46f, h * 0.56f), Size(w * 0.46f, h * 0.28f), Color(0xFFC7A678))
        // Scroll 3: Top-Center
        drawRolledScrollTube(this, Offset(w * 0.26f, h * 0.24f), Size(w * 0.48f, h * 0.28f), Color(0xFFDFCA9E))

        // Red Tassel Ties around the scroll stack
        drawCircle(color = Color(0xFFD32F2F), radius = 3.5f, center = Offset(w * 0.50f, h * 0.50f))
        drawLine(color = Color(0xFFD32F2F), start = Offset(w * 0.50f, h * 0.50f), end = Offset(w * 0.42f, h * 0.88f), strokeWidth = 2f)
        drawLine(color = Color(0xFFD32F2F), start = Offset(w * 0.50f, h * 0.50f), end = Offset(w * 0.58f, h * 0.88f), strokeWidth = 2f)
    }
}

private fun drawRolledScrollTube(drawScope: androidx.compose.ui.graphics.drawscope.DrawScope, pos: Offset, sz: Size, parchmentColor: Color) {
    with(drawScope) {
        // Scroll tube body
        drawRoundRect(
            brush = Brush.verticalGradient(
                listOf(parchmentColor, parchmentColor.copy(alpha = 0.85f), Color(0xFF7A5830)),
                startY = pos.y,
                endY = pos.y + sz.height
            ),
            topLeft = pos,
            size = sz,
            cornerRadius = CornerRadius(sz.height / 2f, sz.height / 2f)
        )
        drawRoundRect(
            color = Color(0xFF4A321A),
            topLeft = pos,
            size = sz,
            cornerRadius = CornerRadius(sz.height / 2f, sz.height / 2f),
            style = Stroke(1.2f)
        )
        // Red Ribbon Band around center
        drawRect(
            color = Color(0xFFD32F2F),
            topLeft = Offset(pos.x + sz.width * 0.42f, pos.y),
            size = Size(sz.width * 0.16f, sz.height)
        )
    }
}

/**
 * 5. STOREHOUSE EMBLEM (Mid-Right Plaque)
 * Wooden timber supply crate with diagonal plank slats, and a rolled delivery scroll leaning against it.
 */
@Composable
fun StorehouseCrateEmblem(modifier: Modifier = Modifier.size(32.dp)) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Wooden Crate Cube (Isometric/slight perspective)
        val crateRect = Rect(w * 0.12f, h * 0.28f, w * 0.68f, h * 0.84f)
        drawRect(
            brush = Brush.verticalGradient(
                listOf(Color(0xFFB07D48), Color(0xFF865B32), Color(0xFF5D3D1E))
            ),
            topLeft = Offset(crateRect.left, crateRect.top),
            size = Size(crateRect.width, crateRect.height)
        )
        // Outer Timber Frame
        drawRect(
            color = Color(0xFF432A13),
            topLeft = Offset(crateRect.left, crateRect.top),
            size = Size(crateRect.width, crateRect.height),
            style = Stroke(2f)
        )
        // Diagonal Brace Slats (X pattern)
        drawLine(color = Color(0xFF432A13), start = Offset(crateRect.left, crateRect.top), end = Offset(crateRect.right, crateRect.bottom), strokeWidth = 2f)
        drawLine(color = Color(0xFF432A13), start = Offset(crateRect.left, crateRect.bottom), end = Offset(crateRect.right, crateRect.top), strokeWidth = 2f)

        // Corner Brass Studs
        drawCircle(color = Color(0xFFFFD54F), radius = 2f, center = Offset(crateRect.left + 4f, crateRect.top + 4f))
        drawCircle(color = Color(0xFFFFD54F), radius = 2f, center = Offset(crateRect.right - 4f, crateRect.top + 4f))
        drawCircle(color = Color(0xFFFFD54F), radius = 2f, center = Offset(crateRect.left + 4f, crateRect.bottom - 4f))
        drawCircle(color = Color(0xFFFFD54F), radius = 2f, center = Offset(crateRect.right - 4f, crateRect.bottom - 4f))

        // Delivery Scroll Leaning in Front of Crate (angled)
        val scrollPath = Path().apply {
            moveTo(w * 0.50f, h * 0.54f)
            lineTo(w * 0.90f, h * 0.84f)
            lineTo(w * 0.84f, h * 0.92f)
            lineTo(w * 0.44f, h * 0.62f)
            close()
        }
        drawPath(
            path = scrollPath,
            brush = Brush.linearGradient(listOf(Color(0xFFFFF6DF), Color(0xFFDCC49E)))
        )
        drawPath(path = scrollPath, color = Color(0xFF5D3A18), style = Stroke(1.2f))

        // Red cord tied around delivery scroll
        drawLine(color = Color(0xFFD32F2F), start = Offset(w * 0.64f, h * 0.65f), end = Offset(w * 0.72f, h * 0.77f), strokeWidth = 3f)
    }
}

/**
 * 6. MISSION STRIP 3D JUTSU SCROLL EMBLEM (Bottom Contextual Strip)
 * Large 3D rolled jutsu scroll with wooden roller handles, red tassel cords, and engraved Leaf swirl.
 */
@Composable
fun MissionStripScrollEmblem(modifier: Modifier = Modifier.size(42.dp)) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Wooden Roller Knobs (Top & Bottom)
        drawRoundRect(
            brush = Brush.verticalGradient(listOf(Color(0xFF9E6534), Color(0xFF5D3818))),
            topLeft = Offset(w * 0.08f, h * 0.18f),
            size = Size(w * 0.10f, h * 0.64f),
            cornerRadius = CornerRadius(2f, 2f)
        )
        drawRoundRect(
            brush = Brush.verticalGradient(listOf(Color(0xFF9E6534), Color(0xFF5D3818))),
            topLeft = Offset(w * 0.82f, h * 0.18f),
            size = Size(w * 0.10f, h * 0.64f),
            cornerRadius = CornerRadius(2f, 2f)
        )

        // Rolled Parchment Cylinder Body
        val tubeRect = Rect(w * 0.14f, h * 0.24f, w * 0.86f, h * 0.76f)
        drawRoundRect(
            brush = Brush.verticalGradient(
                listOf(Color(0xFFFFF9E8), Color(0xFFEADBBE), Color(0xFFC7B18E))
            ),
            topLeft = Offset(tubeRect.left, tubeRect.top),
            size = Size(tubeRect.width, tubeRect.height),
            cornerRadius = CornerRadius(tubeRect.height / 2f, tubeRect.height / 2f)
        )
        drawRoundRect(
            color = Color(0xFF6B4822),
            topLeft = Offset(tubeRect.left, tubeRect.top),
            size = Size(tubeRect.width, tubeRect.height),
            cornerRadius = CornerRadius(tubeRect.height / 2f, tubeRect.height / 2f),
            style = Stroke(1.5f)
        )

        // Engraved Dark Leaf Swirl on Parchment
        val center = Offset(tubeRect.left + tubeRect.width * 0.38f, tubeRect.top + tubeRect.height * 0.50f)
        val swirl = Path().apply {
            moveTo(center.x, center.y - 5f)
            cubicTo(center.x + 6f, center.y - 5f, center.x + 6f, center.y + 5f, center.x, center.y + 5f)
            cubicTo(center.x - 5f, center.y + 5f, center.x - 5f, center.y - 2f, center.x, center.y - 2f)
        }
        drawPath(path = swirl, color = Color(0xFF4A321A), style = Stroke(2f, cap = StrokeCap.Round))

        // Red Silk Ribbon Band
        drawRect(
            color = Color(0xFFD32F2F),
            topLeft = Offset(w * 0.64f, tubeRect.top),
            size = Size(w * 0.12f, tubeRect.height)
        )
        // Red Hanging Tassel Ribbon Tails
        val tassel1 = Path().apply {
            moveTo(w * 0.66f, tubeRect.bottom)
            lineTo(w * 0.60f, h * 0.94f)
            lineTo(w * 0.68f, h * 0.88f)
            close()
        }
        drawPath(path = tassel1, color = Color(0xFFD32F2F))
        val tassel2 = Path().apply {
            moveTo(w * 0.72f, tubeRect.bottom)
            lineTo(w * 0.78f, h * 0.94f)
            lineTo(w * 0.70f, h * 0.88f)
            close()
        }
        drawPath(path = tassel2, color = Color(0xFFB71C1C))
    }
}

// =========================================================================
// BOTTOM NAVIGATION ICONS (Strictly matching Polished screenshot)
// =========================================================================

/**
 * 7. HOME ACTIVE: Glowing Golden Konoha Leaf Swirl
 */
@Composable
fun HomeGoldenLeafEmblem(modifier: Modifier = Modifier.size(30.dp)) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val center = Offset(w * 0.5f, h * 0.48f)

        // Radiant Amber/Gold Outer Glow
        drawCircle(
            brush = Brush.radialGradient(
                listOf(Color(0x66FFD54F), Color(0x22FFA000), Color.Transparent),
                center = center,
                radius = w * 0.50f
            ),
            radius = w * 0.48f,
            center = center
        )

        // Leaf outline / spiral emblem in rich gold
        val leafPath = Path().apply {
            moveTo(center.x, h * 0.10f)
            cubicTo(w * 0.90f, h * 0.20f, w * 0.92f, h * 0.72f, center.x, h * 0.86f)
            cubicTo(w * 0.08f, h * 0.72f, w * 0.10f, h * 0.20f, center.x, h * 0.10f)
            close()
        }
        drawPath(
            path = leafPath,
            brush = Brush.linearGradient(listOf(Color(0xFFFFEE7C), Color(0xFFF5C542), Color(0xFFD49A24))),
            style = Stroke(width = 3.2f)
        )

        // Center Leaf Spiral
        val spiralPath = Path().apply {
            moveTo(center.x, center.y - h * 0.14f)
            cubicTo(center.x + w * 0.18f, center.y - h * 0.14f, center.x + w * 0.18f, center.y + h * 0.16f, center.x, center.y + h * 0.16f)
            cubicTo(center.x - w * 0.14f, center.y + h * 0.16f, center.x - w * 0.14f, center.y - h * 0.06f, center.x, center.y - h * 0.06f)
        }
        drawPath(
            path = spiralPath,
            brush = Brush.linearGradient(listOf(Color(0xFFFFEE7C), Color(0xFFF5C542))),
            style = Stroke(width = 2.8f, cap = StrokeCap.Round)
        )
    }
}

/**
 * 8. SQUAD INACTIVE: Three Shinobi Busts (Kakashi in center)
 */
@Composable
fun SquadBustsEmblem(modifier: Modifier = Modifier.size(30.dp)) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val skinColor = Color(0xFFCAD5D3)
        val hairColor = Color(0xFFE2EBF0)

        // Left squad mate bust (background)
        drawCircle(color = skinColor.copy(alpha = 0.7f), radius = w * 0.11f, center = Offset(w * 0.25f, h * 0.38f))
        drawRoundRect(color = Color(0xFF90A4AE), topLeft = Offset(w * 0.12f, h * 0.50f), size = Size(w * 0.26f, h * 0.32f), cornerRadius = CornerRadius(4f, 4f))

        // Right squad mate bust (background)
        drawCircle(color = skinColor.copy(alpha = 0.7f), radius = w * 0.11f, center = Offset(w * 0.75f, h * 0.38f))
        drawRoundRect(color = Color(0xFF90A4AE), topLeft = Offset(w * 0.62f, h * 0.50f), size = Size(w * 0.26f, h * 0.32f), cornerRadius = CornerRadius(4f, 4f))

        // Center primary Kakashi bust (foreground)
        // Silver spiky hair
        val hair = Path().apply {
            moveTo(w * 0.34f, h * 0.26f)
            lineTo(w * 0.40f, h * 0.10f)
            lineTo(w * 0.50f, h * 0.18f)
            lineTo(w * 0.60f, h * 0.08f)
            lineTo(w * 0.66f, h * 0.26f)
            close()
        }
        drawPath(path = hair, color = hairColor)

        // Face / Head
        drawCircle(color = skinColor, radius = w * 0.14f, center = Offset(w * 0.50f, h * 0.34f))

        // Forehead Protector Plate
        drawRoundRect(
            color = Color(0xFF607D8B),
            topLeft = Offset(w * 0.36f, h * 0.28f),
            size = Size(w * 0.28f, h * 0.07f),
            cornerRadius = CornerRadius(1.5f, 1.5f)
        )
        // Blue face mask
        drawRect(
            color = Color(0xFF263238),
            topLeft = Offset(w * 0.38f, h * 0.36f),
            size = Size(w * 0.24f, h * 0.12f)
        )

        // Flak jacket shoulders
        drawRoundRect(
            color = Color(0xFF455A64),
            topLeft = Offset(w * 0.24f, h * 0.48f),
            size = Size(w * 0.52f, h * 0.36f),
            cornerRadius = CornerRadius(5f, 5f)
        )
    }
}

/**
 * 9. CAMPAIGN INACTIVE: Illustrated Folded Route Map
 */
@Composable
fun CampaignMapEmblem(modifier: Modifier = Modifier.size(30.dp)) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // 3-Fold Map Sheet
        val map = Path().apply {
            moveTo(w * 0.12f, h * 0.26f)
            lineTo(w * 0.38f, h * 0.20f)
            lineTo(w * 0.64f, h * 0.26f)
            lineTo(w * 0.88f, h * 0.20f)
            lineTo(w * 0.88f, h * 0.80f)
            lineTo(w * 0.64f, h * 0.86f)
            lineTo(w * 0.38f, h * 0.80f)
            lineTo(w * 0.12f, h * 0.86f)
            close()
        }
        drawPath(
            path = map,
            brush = Brush.horizontalGradient(
                listOf(Color(0xFFE0D3B8), Color(0xFFFFF2D7), Color(0xFFD5C4A3))
            )
        )
        drawPath(path = map, color = Color(0xFF5D4037), style = Stroke(1.5f))

        // Fold Crease Lines
        drawLine(color = Color(0xFF8D6E63), start = Offset(w * 0.38f, h * 0.20f), end = Offset(w * 0.38f, h * 0.80f), strokeWidth = 1f)
        drawLine(color = Color(0xFF8D6E63), start = Offset(w * 0.64f, h * 0.26f), end = Offset(w * 0.64f, h * 0.86f), strokeWidth = 1f)

        // Green Terrain Contours
        drawCircle(color = Color(0xFF81C784).copy(alpha = 0.5f), radius = w * 0.12f, center = Offset(w * 0.28f, h * 0.50f))
        drawCircle(color = Color(0xFF81C784).copy(alpha = 0.5f), radius = w * 0.14f, center = Offset(w * 0.72f, h * 0.60f))

        // Red Dashed Waypoint Route
        val route = Path().apply {
            moveTo(w * 0.22f, h * 0.68f)
            cubicTo(w * 0.35f, h * 0.48f, w * 0.50f, h * 0.65f, w * 0.66f, h * 0.42f)
        }
        drawPath(
            path = route,
            color = Color(0xFFD32F2F),
            style = Stroke(
                width = 2.2f,
                cap = StrokeCap.Round,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(4f, 4f), 0f)
            )
        )

        // Red 'X' Destination Mark
        val xPos = Offset(w * 0.68f, h * 0.40f)
        drawLine(color = Color(0xFFD32F2F), start = Offset(xPos.x - 4f, xPos.y - 4f), end = Offset(xPos.x + 4f, xPos.y + 4f), strokeWidth = 2.5f)
        drawLine(color = Color(0xFFD32F2F), start = Offset(xPos.x + 4f, xPos.y - 4f), end = Offset(xPos.x - 4f, xPos.y + 4f), strokeWidth = 2.5f)
    }
}

/**
 * 10. RECRUIT INACTIVE: Ancient Summoning Scroll with Green Handles
 */
@Composable
fun RecruitAncientScrollEmblem(modifier: Modifier = Modifier.size(30.dp)) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Green Jade Scroll Roller Handles (Top & Bottom)
        drawRoundRect(
            brush = Brush.verticalGradient(listOf(Color(0xFF4DB6AC), Color(0xFF00796B), Color(0xFF004D40))),
            topLeft = Offset(w * 0.08f, h * 0.22f),
            size = Size(w * 0.09f, h * 0.56f),
            cornerRadius = CornerRadius(2f, 2f)
        )
        drawRoundRect(
            brush = Brush.verticalGradient(listOf(Color(0xFF4DB6AC), Color(0xFF00796B), Color(0xFF004D40))),
            topLeft = Offset(w * 0.83f, h * 0.22f),
            size = Size(w * 0.09f, h * 0.56f),
            cornerRadius = CornerRadius(2f, 2f)
        )

        // Cream Rolled Parchment Cylinder Body
        val tubeRect = Rect(w * 0.14f, h * 0.28f, w * 0.86f, h * 0.72f)
        drawRoundRect(
            brush = Brush.verticalGradient(
                listOf(Color(0xFFFFF9E8), Color(0xFFEADBBE), Color(0xFFC7B18E))
            ),
            topLeft = Offset(tubeRect.left, tubeRect.top),
            size = Size(tubeRect.width, tubeRect.height),
            cornerRadius = CornerRadius(tubeRect.height / 2f, tubeRect.height / 2f)
        )
        drawRoundRect(
            color = Color(0xFF5D4037),
            topLeft = Offset(tubeRect.left, tubeRect.top),
            size = Size(tubeRect.width, tubeRect.height),
            cornerRadius = CornerRadius(tubeRect.height / 2f, tubeRect.height / 2f),
            style = Stroke(1.5f)
        )

        // Vermilion Wax Seal in center
        val center = Offset(tubeRect.left + tubeRect.width * 0.50f, tubeRect.top + tubeRect.height * 0.50f)
        drawCircle(
            brush = Brush.radialGradient(
                listOf(Color(0xFFFF5252), Color(0xFFD32F2F), Color(0xFF8B0000)),
                center = center
            ),
            radius = w * 0.13f,
            center = center
        )

        // Gold Kanji / Swirl on Seal
        drawCircle(
            color = Color(0xFFFFD54F),
            radius = w * 0.06f,
            center = center,
            style = Stroke(1.5f)
        )
        drawLine(color = Color(0xFFFFD54F), start = Offset(center.x - 3f, center.y), end = Offset(center.x + 3f, center.y), strokeWidth = 1.5f)
    }
}
