package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.*

/**
 * Handcrafted Game Art Suite for Naruto Idle RPG.
 * Replaces all emojis and placeholder graphics with authentic mobile game assets:
 * - 3D Illustrated Game Currencies (Konoha Ryo Gold Coin) & Recruitment Seals.
 * - 3D Illustrated AFK Idle Loot Chest with radiant chakra aura.
 * - Fantasy Emblem Silhouettes for AFK Navigation Dock (Village, Squad, Campaign, Recruit).
 * - Building Landmark Pins & Crests.
 * - Character Silhouette Portraits (Naruto, Sasuke, Sakura, Kakashi, Shikamaru, Tenten).
 * - Game Equipment & Weapon Art.
 */

// ==========================================
// 1. CURRENCIES & TOKENS
// ==========================================

@Composable
fun KonohaRyoCoin(modifier: Modifier = Modifier.size(20.dp)) {
    Box(
        modifier = modifier
            .shadow(2.dp, CircleShape)
            .clip(CircleShape)
            .border(0.5.dp, Color(0xFFFFD54F), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_konoha_ryo_coin),
            contentDescription = "Konoha Ryo Gold Coin",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun RecruitmentSealScroll(modifier: Modifier = Modifier.size(20.dp)) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Rolled Parchment Body
        val scrollRect = Path().apply {
            moveTo(w * 0.15f, h * 0.2f)
            lineTo(w * 0.85f, h * 0.2f)
            lineTo(w * 0.85f, h * 0.8f)
            lineTo(w * 0.15f, h * 0.8f)
            close()
        }
        drawPath(
            path = scrollRect,
            brush = Brush.verticalGradient(
                listOf(Color(0xFFFFF6DF), Color(0xFFE5D2A4), Color(0xFFC9B07C))
            )
        )

        // Scroll Handles (Red Lacquer)
        drawRoundRect(
            color = Color(0xFFAA2211),
            topLeft = Offset(w * 0.08f, h * 0.15f),
            size = Size(w * 0.12f, h * 0.70f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(4f, 4f)
        )
        drawRoundRect(
            color = Color(0xFFAA2211),
            topLeft = Offset(w * 0.80f, h * 0.15f),
            size = Size(w * 0.12f, h * 0.70f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(4f, 4f)
        )

        // Central Red Silk Ribbon
        drawRect(
            color = Color(0xFFD32F2F),
            topLeft = Offset(w * 0.42f, h * 0.2f),
            size = Size(w * 0.16f, h * 0.6f)
        )

        // Vermilion Wax Seal
        val center = Offset(w * 0.5f, h * 0.5f)
        drawCircle(
            brush = Brush.radialGradient(
                listOf(Color(0xFFFF5252), Color(0xFFB71C1C)),
                center = center,
                radius = w * 0.22f
            ),
            radius = w * 0.20f,
            center = center
        )

        // Gold Fire / Leaf mark on seal
        drawCircle(
            color = Color(0xFFFFD54F),
            radius = w * 0.08f,
            center = center
        )
    }
}

@Composable
fun ChakraExpOrb(modifier: Modifier = Modifier.size(20.dp)) {
    Canvas(modifier = modifier) {
        val center = Offset(size.width / 2f, size.height / 2f)
        val r = size.minDimension / 2f

        // Outer Aura
        drawCircle(
            brush = Brush.radialGradient(
                listOf(Color(0x8054B9DB), Color(0x0054B9DB)),
                center = center,
                radius = r
            ),
            radius = r,
            center = center
        )

        // Core Glowing Chakra Sphere
        drawCircle(
            brush = Brush.radialGradient(
                listOf(Color(0xFFE1F5FE), Color(0xFF4FC3F7), Color(0xFF0288D1)),
                center = center - Offset(r * 0.2f, r * 0.2f),
                radius = r * 0.75f
            ),
            radius = r * 0.68f,
            center = center
        )

        // Swirling Chakra Energy Rings
        val arcStroke = Stroke(width = size.minDimension * 0.08f, cap = StrokeCap.Round)
        drawArc(
            color = Color(0xFFFFFFFF),
            startAngle = 30f,
            sweepAngle = 120f,
            useCenter = false,
            topLeft = Offset(center.x - r * 0.45f, center.y - r * 0.45f),
            size = Size(r * 0.9f, r * 0.9f),
            style = arcStroke
        )
        drawArc(
            color = Color(0xFF81D4FA),
            startAngle = 210f,
            sweepAngle = 110f,
            useCenter = false,
            topLeft = Offset(center.x - r * 0.45f, center.y - r * 0.45f),
            size = Size(r * 0.9f, r * 0.9f),
            style = arcStroke
        )
    }
}

// ==========================================
// 2. AFK IDLE TREASURE CHEST
// ==========================================

@Composable
fun AfkIdleChest(
    modifier: Modifier = Modifier.size(64.dp),
    isGlowing: Boolean = true
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // 1. Radiant Golden & Cyan Chakra Aura (Soft Falloff onto Cobblestones)
        if (isGlowing) {
            drawCircle(
                brush = Brush.radialGradient(
                    listOf(
                        Color(0xB3FFD54F),
                        Color(0x4DFFA000),
                        Color(0x2600E5FF),
                        Color.Transparent
                    ),
                    center = Offset(w * 0.5f, h * 0.58f),
                    radius = w * 0.62f
                ),
                radius = w * 0.62f,
                center = Offset(w * 0.5f, h * 0.58f)
            )
        }

        // 2. Ground Contact Shadow (Soft Oval Blending into Cobblestone)
        drawOval(
            brush = Brush.radialGradient(
                listOf(Color(0x99000000), Color(0x33000000), Color.Transparent),
                center = Offset(w * 0.5f, h * 0.84f),
                radius = w * 0.44f
            ),
            topLeft = Offset(w * 0.08f, h * 0.74f),
            size = Size(w * 0.84f, h * 0.22f)
        )

        // 3. Chest Body (Rich Dark Mahogany Timber)
        val bodyPath = Path().apply {
            moveTo(w * 0.16f, h * 0.46f)
            lineTo(w * 0.84f, h * 0.46f)
            lineTo(w * 0.88f, h * 0.84f)
            lineTo(w * 0.12f, h * 0.84f)
            close()
        }
        drawPath(
            path = bodyPath,
            brush = Brush.verticalGradient(
                listOf(Color(0xFF6D421D), Color(0xFF45270E), Color(0xFF2B1607))
            )
        )

        // 4. Chest Lid (Curved Barrel Vault with Specular Highlight)
        val lidPath = Path().apply {
            moveTo(w * 0.12f, h * 0.46f)
            cubicTo(w * 0.14f, h * 0.18f, w * 0.86f, h * 0.18f, w * 0.88f, h * 0.46f)
            close()
        }
        drawPath(
            path = lidPath,
            brush = Brush.verticalGradient(
                listOf(Color(0xFF8D5B28), Color(0xFF5A3414), Color(0xFF3B1E08))
            )
        )

        // Lid Top Specular Glare Arc
        val glarePath = Path().apply {
            moveTo(w * 0.22f, h * 0.38f)
            cubicTo(w * 0.28f, h * 0.22f, w * 0.72f, h * 0.22f, w * 0.78f, h * 0.38f)
            lineTo(w * 0.74f, h * 0.42f)
            cubicTo(w * 0.70f, h * 0.28f, w * 0.30f, h * 0.28f, w * 0.26f, h * 0.42f)
            close()
        }
        drawPath(path = glarePath, color = Color.White.copy(alpha = 0.22f))

        // 5. Metallic Gold Band Straps (Lid & Body)
        val goldBrush = Brush.horizontalGradient(
            listOf(Color(0xFFBF8819), Color(0xFFFFEE7C), Color(0xFFD49A24))
        )
        // Left Strap
        drawRect(
            brush = goldBrush,
            topLeft = Offset(w * 0.26f, h * 0.24f),
            size = Size(w * 0.08f, h * 0.60f)
        )
        // Right Strap
        drawRect(
            brush = goldBrush,
            topLeft = Offset(w * 0.66f, h * 0.24f),
            size = Size(w * 0.08f, h * 0.60f)
        )
        // Horizontal Seam Band (Lid Rim)
        drawRoundRect(
            brush = goldBrush,
            topLeft = Offset(w * 0.11f, h * 0.44f),
            size = Size(w * 0.78f, h * 0.07f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(2f, 2f)
        )
        // Bottom Base Gold Rim
        drawRoundRect(
            brush = goldBrush,
            topLeft = Offset(w * 0.11f, h * 0.81f),
            size = Size(w * 0.78f, h * 0.05f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(2f, 2f)
        )

        // 6. Brass Rivet Studs on Straps
        val rivetColor = Color(0xFFFFF8E1)
        val rivetShadow = Color(0xFF5D4037)
        val rivetY = listOf(0.28f, 0.40f, 0.54f, 0.68f, 0.78f)
        for (y in rivetY) {
            // Left Strap Rivets
            drawCircle(color = rivetShadow, radius = w * 0.018f, center = Offset(w * 0.30f, h * y + 1f))
            drawCircle(color = rivetColor, radius = w * 0.016f, center = Offset(w * 0.30f, h * y))
            // Right Strap Rivets
            drawCircle(color = rivetShadow, radius = w * 0.018f, center = Offset(w * 0.70f, h * y + 1f))
            drawCircle(color = rivetColor, radius = w * 0.016f, center = Offset(w * 0.70f, h * y))
        }

        // 7. Center Gold Lock Plate with Keyhole
        drawRoundRect(
            brush = goldBrush,
            topLeft = Offset(w * 0.41f, h * 0.42f),
            size = Size(w * 0.18f, h * 0.22f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(4f, 4f)
        )
        // Dark Inner Inset
        drawRoundRect(
            color = Color(0xFF261201),
            topLeft = Offset(w * 0.43f, h * 0.44f),
            size = Size(w * 0.14f, h * 0.18f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(3f, 3f)
        )

        // 8. Radiant Cyan Chakra Gem on Lock Clasp
        drawCircle(
            brush = Brush.radialGradient(
                listOf(Color(0xFFE0F7FA), Color(0xFF00E5FF), Color(0xFF0097A7)),
                center = Offset(w * 0.5f, h * 0.49f),
                radius = w * 0.05f
            ),
            radius = w * 0.045f,
            center = Offset(w * 0.5f, h * 0.49f)
        )

        // Keyhole
        drawCircle(
            color = Color(0xFF0A0501),
            radius = w * 0.018f,
            center = Offset(w * 0.5f, h * 0.56f)
        )
        drawLine(
            color = Color(0xFF0A0501),
            start = Offset(w * 0.5f, h * 0.56f),
            end = Offset(w * 0.5f, h * 0.60f),
            strokeWidth = 2.5f
        )
    }
}

// ==========================================
// 2B. AFK DOCK FANTASY EMBLEM SILHOUETTES
// ==========================================

// ==========================================
// 2B. SECTION 05 — ROOT NAVIGATION PICTOGRAMS
// ==========================================

/**
 * Root symbol 1 — Home: Simplified Leaf / village spiral emblem
 */
@Composable
fun VillageDockEmblem(
    isSelected: Boolean,
    modifier: Modifier = Modifier.size(26.dp)
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val center = Offset(w * 0.5f, h * 0.5f)
        val iconColor = if (isSelected) InkCharcoal else ButtonTextCream

        // Leaf outline / spiral emblem
        val leafPath = Path().apply {
            moveTo(center.x, h * 0.15f)
            cubicTo(w * 0.85f, h * 0.25f, w * 0.88f, h * 0.70f, center.x, h * 0.85f)
            cubicTo(w * 0.12f, h * 0.70f, w * 0.15f, h * 0.25f, center.x, h * 0.15f)
            close()
        }
        drawPath(
            path = leafPath,
            color = iconColor,
            style = Stroke(width = 2.5f)
        )

        // Center spiral / swirl
        val spiralPath = Path().apply {
            moveTo(center.x, center.y - h * 0.12f)
            cubicTo(center.x + w * 0.18f, center.y - h * 0.12f, center.x + w * 0.18f, center.y + h * 0.15f, center.x, center.y + h * 0.15f)
            cubicTo(center.x - w * 0.12f, center.y + h * 0.15f, center.x - w * 0.12f, center.y - h * 0.04f, center.x, center.y - h * 0.04f)
        }
        drawPath(
            path = spiralPath,
            color = iconColor,
            style = Stroke(width = 2.2f, cap = StrokeCap.Round)
        )
    }
}

/**
 * Root symbol 2 — Squad: Three distinct forehead-protected bust silhouettes
 */
@Composable
fun SquadDockEmblem(
    isSelected: Boolean,
    modifier: Modifier = Modifier.size(26.dp)
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val iconColor = if (isSelected) InkCharcoal else ButtonTextCream
        val secColor = if (isSelected) LacquerOrange else ShinobiSteel

        // Left bust silhouette (smaller, background)
        drawCircle(color = secColor, radius = w * 0.12f, center = Offset(w * 0.24f, h * 0.35f))
        drawOval(color = secColor, topLeft = Offset(w * 0.06f, h * 0.48f), size = Size(w * 0.36f, h * 0.38f))

        // Right bust silhouette (smaller, background)
        drawCircle(color = secColor, radius = w * 0.12f, center = Offset(w * 0.76f, h * 0.35f))
        drawOval(color = secColor, topLeft = Offset(w * 0.58f, h * 0.48f), size = Size(w * 0.36f, h * 0.38f))

        // Center primary bust silhouette (larger, foreground)
        drawCircle(color = iconColor, radius = w * 0.16f, center = Offset(w * 0.50f, h * 0.30f))
        // Forehead protector plate on center head
        drawRoundRect(
            color = if (isSelected) PaperLight else IndigoCloth,
            topLeft = Offset(w * 0.36f, h * 0.26f),
            size = Size(w * 0.28f, h * 0.08f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(1.5f, 1.5f)
        )
        // Center shoulders / jacket
        drawOval(color = iconColor, topLeft = Offset(w * 0.22f, h * 0.46f), size = Size(w * 0.56f, h * 0.44f))
    }
}

/**
 * Root symbol 3 — Campaign: Folded route map with a path pin
 */
@Composable
fun CampaignDockEmblem(
    isSelected: Boolean,
    modifier: Modifier = Modifier.size(26.dp)
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val iconColor = if (isSelected) InkCharcoal else ButtonTextCream
        val pinColor = if (isSelected) LacquerOrange else NarutoGold

        // 3-fold map polygon
        val mapPath = Path().apply {
            moveTo(w * 0.12f, h * 0.28f)
            lineTo(w * 0.38f, h * 0.22f)
            lineTo(w * 0.62f, h * 0.28f)
            lineTo(w * 0.88f, h * 0.22f)
            lineTo(w * 0.88f, h * 0.78f)
            lineTo(w * 0.62f, h * 0.84f)
            lineTo(w * 0.38f, h * 0.78f)
            lineTo(w * 0.12f, h * 0.84f)
            close()
        }
        drawPath(
            path = mapPath,
            color = iconColor,
            style = Stroke(width = 2.2f)
        )

        // Fold lines
        drawLine(color = iconColor.copy(alpha = 0.5f), start = Offset(w * 0.38f, h * 0.22f), end = Offset(w * 0.38f, h * 0.78f), strokeWidth = 1.5f)
        drawLine(color = iconColor.copy(alpha = 0.5f), start = Offset(w * 0.62f, h * 0.28f), end = Offset(w * 0.62f, h * 0.84f), strokeWidth = 1.5f)

        // Map route waypoint path
        val routePath = Path().apply {
            moveTo(w * 0.22f, h * 0.62f)
            cubicTo(w * 0.35f, h * 0.45f, w * 0.45f, h * 0.65f, w * 0.62f, h * 0.45f)
        }
        drawPath(
            path = routePath,
            color = pinColor,
            style = Stroke(width = 2.0f, cap = StrokeCap.Round, pathEffect = PathEffect.dashPathEffect(floatArrayOf(4f, 4f), 0f))
        )

        // Waypoint pin
        drawCircle(color = pinColor, radius = w * 0.08f, center = Offset(w * 0.65f, h * 0.42f))
    }
}

/**
 * Root symbol 4 — Recruit: Tied invitation scroll
 */
@Composable
fun RecruitDockEmblem(
    isSelected: Boolean,
    modifier: Modifier = Modifier.size(26.dp)
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val iconColor = if (isSelected) InkCharcoal else ButtonTextCream
        val ribbonColor = if (isSelected) LacquerOrange else NarutoOrange

        // Rolled scroll body (horizontal)
        drawRoundRect(
            color = iconColor,
            topLeft = Offset(w * 0.14f, h * 0.28f),
            size = Size(w * 0.72f, h * 0.44f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(4f, 4f),
            style = Stroke(width = 2.2f)
        )

        // Wooden scroll end handles
        drawRoundRect(
            color = ribbonColor,
            topLeft = Offset(w * 0.08f, h * 0.24f),
            size = Size(w * 0.08f, h * 0.52f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(2f, 2f)
        )
        drawRoundRect(
            color = ribbonColor,
            topLeft = Offset(w * 0.84f, h * 0.24f),
            size = Size(w * 0.08f, h * 0.52f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(2f, 2f)
        )

        // Tied ribbon cord around center
        drawRect(
            color = ribbonColor,
            topLeft = Offset(w * 0.44f, h * 0.26f),
            size = Size(w * 0.12f, h * 0.48f)
        )
        // Hanging ribbon tags
        val ribbonTag = Path().apply {
            moveTo(w * 0.46f, h * 0.74f)
            lineTo(w * 0.40f, h * 0.90f)
            lineTo(w * 0.50f, h * 0.84f)
            lineTo(w * 0.60f, h * 0.90f)
            lineTo(w * 0.54f, h * 0.74f)
            close()
        }
        drawPath(path = ribbonTag, color = ribbonColor)
    }
}

// ==========================================
// 3. VILLAGE LANDMARK CRESTS (FOR AFK PIN PLAQUES)
// ==========================================

@Composable
fun TrainingBladesCrest(modifier: Modifier = Modifier.size(24.dp)) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Crossed Katana/Kunai Blades
        drawLine(
            color = Color(0xFFCFD8DC),
            start = Offset(w * 0.2f, h * 0.2f),
            end = Offset(w * 0.8f, h * 0.8f),
            strokeWidth = w * 0.14f,
            cap = StrokeCap.Round
        )
        drawLine(
            color = Color(0xFFFFFFFF),
            start = Offset(w * 0.25f, h * 0.25f),
            end = Offset(w * 0.75f, h * 0.75f),
            strokeWidth = w * 0.06f,
            cap = StrokeCap.Round
        )

        drawLine(
            color = Color(0xFF90A4AE),
            start = Offset(w * 0.8f, h * 0.2f),
            end = Offset(w * 0.2f, h * 0.8f),
            strokeWidth = w * 0.14f,
            cap = StrokeCap.Round
        )
        drawLine(
            color = Color(0xFFECEFF1),
            start = Offset(w * 0.75f, h * 0.25f),
            end = Offset(w * 0.25f, h * 0.75f),
            strokeWidth = w * 0.06f,
            cap = StrokeCap.Round
        )

        // Center Gold Binding Boss
        drawCircle(
            brush = Brush.radialGradient(listOf(Color(0xFFFFEE7C), Color(0xFFC67D00))),
            radius = w * 0.18f,
            center = Offset(w * 0.5f, h * 0.5f)
        )
    }
}

@Composable
fun ToolShopAnvilCrest(modifier: Modifier = Modifier.size(24.dp)) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Steel Shuriken 4-point Star
        val starPath = Path().apply {
            moveTo(w * 0.5f, h * 0.12f)
            lineTo(w * 0.62f, h * 0.38f)
            lineTo(w * 0.88f, h * 0.5f)
            lineTo(w * 0.62f, h * 0.62f)
            lineTo(w * 0.5f, h * 0.88f)
            lineTo(w * 0.38f, h * 0.62f)
            lineTo(w * 0.12f, h * 0.5f)
            lineTo(w * 0.38f, h * 0.38f)
            close()
        }
        drawPath(
            path = starPath,
            brush = Brush.radialGradient(
                listOf(Color(0xFFCFD8DC), Color(0xFF546E7A), Color(0xFF263238))
            )
        )

        // Center Hole
        drawCircle(
            color = Color(0xFF192839),
            radius = w * 0.10f,
            center = Offset(w * 0.5f, h * 0.5f)
        )
        drawCircle(
            color = Color(0xFFFFD54F),
            radius = w * 0.11f,
            center = Offset(w * 0.5f, h * 0.5f),
            style = Stroke(2f)
        )
    }
}

@Composable
fun HokageHatCrest(modifier: Modifier = Modifier.size(24.dp)) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Triangular Red Hokage Hat Silhouette
        val hatPath = Path().apply {
            moveTo(w * 0.5f, h * 0.12f)
            lineTo(w * 0.88f, h * 0.72f)
            lineTo(w * 0.12f, h * 0.72f)
            close()
        }
        drawPath(
            path = hatPath,
            brush = Brush.verticalGradient(
                listOf(Color(0xFFE53935), Color(0xFFB71C1C))
            )
        )

        // White Front Center Panel
        val whitePanel = Path().apply {
            moveTo(w * 0.5f, h * 0.12f)
            lineTo(w * 0.65f, h * 0.72f)
            lineTo(w * 0.35f, h * 0.72f)
            close()
        }
        drawPath(path = whitePanel, color = Color(0xFFFAFAFA))

        // Red "Fire" (火) Kanji Diamond Insignia
        drawCircle(
            color = Color(0xFFD32F2F),
            radius = w * 0.12f,
            center = Offset(w * 0.5f, h * 0.50f)
        )

        // White Brim Band
        drawRect(
            color = Color(0xFFECEFF1),
            topLeft = Offset(w * 0.08f, h * 0.72f),
            size = Size(w * 0.84f, h * 0.14f)
        )
    }
}

@Composable
fun WillOfFireCrest(modifier: Modifier = Modifier.size(24.dp)) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val center = Offset(w * 0.5f, h * 0.55f)

        // Swirling Flame Petals
        val flamePath = Path().apply {
            moveTo(center.x, h * 0.1f)
            cubicTo(w * 0.85f, h * 0.3f, w * 0.9f, h * 0.75f, center.x, h * 0.9f)
            cubicTo(w * 0.1f, h * 0.75f, w * 0.15f, h * 0.3f, center.x, h * 0.1f)
            close()
        }
        drawPath(
            path = flamePath,
            brush = Brush.verticalGradient(
                listOf(Color(0xFFFFEB3B), Color(0xFFFF9800), Color(0xFFD84315))
            )
        )

        // Inner Leaf Core
        val leafCore = Path().apply {
            moveTo(center.x, h * 0.32f)
            cubicTo(w * 0.65f, h * 0.45f, w * 0.68f, h * 0.7f, center.x, h * 0.78f)
            cubicTo(w * 0.32f, h * 0.7f, w * 0.35f, h * 0.45f, center.x, h * 0.32f)
            close()
        }
        drawPath(path = leafCore, color = Color(0xFFFFFFFF))
    }
}

@Composable
fun MemorialStoneCrest(modifier: Modifier = Modifier.size(24.dp)) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Tapered Stone Tablet
        val stonePath = Path().apply {
            moveTo(w * 0.28f, h * 0.15f)
            lineTo(w * 0.72f, h * 0.15f)
            lineTo(w * 0.80f, h * 0.85f)
            lineTo(w * 0.20f, h * 0.85f)
            close()
        }
        drawPath(
            path = stonePath,
            brush = Brush.verticalGradient(
                listOf(Color(0xFF78909C), Color(0xFF455A64), Color(0xFF263238))
            )
        )

        // Carved Inscription Lines
        drawLine(
            color = Color(0xFFCFD8DC),
            start = Offset(w * 0.35f, h * 0.32f),
            end = Offset(w * 0.65f, h * 0.32f),
            strokeWidth = 2.5f
        )
        drawLine(
            color = Color(0xFFCFD8DC),
            start = Offset(w * 0.32f, h * 0.46f),
            end = Offset(w * 0.68f, h * 0.46f),
            strokeWidth = 2.5f
        )
        drawLine(
            color = Color(0xFFCFD8DC),
            start = Offset(w * 0.35f, h * 0.60f),
            end = Offset(w * 0.65f, h * 0.60f),
            strokeWidth = 2.5f
        )
    }
}

// ==========================================
// 4. SHINOBI CHARACTER PORTRAIT RENDERERS
// ==========================================

fun getShinobiDrawable(shinobiId: String): Int {
    return when (shinobiId) {
        "naruto_01" -> R.drawable.portrait_naruto
        "sasuke_01" -> R.drawable.portrait_sasuke
        "sakura_01" -> R.drawable.portrait_sakura
        "kakashi_01" -> R.drawable.portrait_kakashi
        "shikamaru_01" -> R.drawable.portrait_naruto
        else -> R.drawable.portrait_tenten
    }
}

/**
 * Compact circular / rounded avatar cutout of the anime character splash art
 * with 3D gold embossed rim, drop shadow, and clean crop.
 */
@Composable
fun ShinobiFacePortrait(
    shinobiId: String,
    modifier: Modifier = Modifier.size(52.dp)
) {
    val drawableId = getShinobiDrawable(shinobiId)

    Box(
        modifier = modifier
            .shadow(4.dp, CircleShape)
            .clip(CircleShape)
            .border(1.5.dp, NarutoGold, CircleShape)
            .background(NightNavyDark),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = drawableId),
            contentDescription = shinobiId,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

/**
 * AFK Arena / Mythic Heroes High-Res SSR Character Splash Card:
 * Features high-resolution anime character splash art, 3D sculpted gold bezel,
 * corner dragon brackets, glowing SSR rarity tag, elemental crest, and 5 gold stars.
 */
@Composable
fun ShinobiSplashPortrait(
    shinobiId: String,
    modifier: Modifier = Modifier,
    rarity: String = "SSR",
    stars: Int = 5,
    level: Int? = 10,
    shape: androidx.compose.ui.graphics.Shape = RoundedCornerShape(10.dp)
) {
    val drawableId = getShinobiDrawable(shinobiId)

    Box(
        modifier = modifier
            .shadow(8.dp, shape)
            // Heavy 3D Basalt & Gold Foundation Bezel
            .background(Color(0xFF3E1F03), shape)
            .padding(bottom = 3.dp)
            .clip(shape)
            .border(2.dp, NarutoGold, shape)
            .background(NightNavyDark),
        contentAlignment = Alignment.Center
    ) {
        // High-Resolution Character Splash Art
        Image(
            painter = painterResource(id = drawableId),
            contentDescription = shinobiId,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Bottom Shadow Vignette for Badges and Stars
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(42.dp)
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Transparent, Color(0xCC0A1118), Color(0xF00A1118))
                    )
                )
        )

        // Top-Left Rarity Plaque (Mythic Heroes / AFK Arena Style)
        Surface(
            shape = RoundedCornerShape(bottomEnd = 6.dp),
            color = Color(0xFFC62828),
            border = BorderStroke(1.dp, NarutoGold),
            modifier = Modifier.align(Alignment.TopStart)
        ) {
            Text(
                text = rarity,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFFFFFDE7),
                    letterSpacing = 0.5.sp
                )
            )
        }

        // Top-Right Faction Crest
        Box(
            modifier = Modifier
                .padding(4.dp)
                .size(18.dp)
                .align(Alignment.TopEnd)
                .clip(CircleShape)
                .background(NightNavyDark.copy(alpha = 0.8f))
                .border(1.dp, NarutoGold, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            WillOfFireCrest(modifier = Modifier.size(12.dp))
        }

        // Bottom Content: Star Rating and Level Pill
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 3.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Gold Star Bar
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(stars) {
                    Text(
                        text = "★",
                        color = Color(0xFFFFD54F),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black,
                        modifier = Modifier.padding(horizontal = 0.5.dp)
                    )
                }
            }

            if (level != null) {
                Surface(
                    shape = RoundedCornerShape(3.dp),
                    color = Color(0xFF192839).copy(alpha = 0.9f),
                    border = BorderStroke(0.5.dp, NarutoGold),
                    modifier = Modifier.padding(top = 1.dp)
                ) {
                    Text(
                        text = "LV.$level",
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp),
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
}

// ==========================================
// 5. WEAPON & EQUIPMENT ITEM VECTOR ART
// ==========================================

@Composable
fun KunaiSetItemArt(modifier: Modifier = Modifier.size(36.dp)) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Steel Blade
        val blade = Path().apply {
            moveTo(w * 0.5f, h * 0.12f)
            lineTo(w * 0.68f, h * 0.52f)
            lineTo(w * 0.5f, h * 0.56f)
            lineTo(w * 0.32f, h * 0.52f)
            close()
        }
        drawPath(
            path = blade,
            brush = Brush.horizontalGradient(
                listOf(Color(0xFFECEFF1), Color(0xFF78909C), Color(0xFF37474F))
            )
        )

        // Handle with White Wrapping
        drawRect(
            color = Color(0xFFEEEEEE),
            topLeft = Offset(w * 0.44f, h * 0.56f),
            size = Size(w * 0.12f, h * 0.24f)
        )

        // Pommel Ring
        drawCircle(
            color = Color(0xFF455A64),
            radius = w * 0.09f,
            center = Offset(w * 0.5f, h * 0.88f),
            style = Stroke(w * 0.06f)
        )
    }
}

@Composable
fun ChuninVestItemArt(modifier: Modifier = Modifier.size(36.dp)) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Olive Green Tactical Vest
        val vest = Path().apply {
            moveTo(w * 0.22f, h * 0.20f)
            lineTo(w * 0.78f, h * 0.20f)
            lineTo(w * 0.88f, h * 0.82f)
            lineTo(w * 0.12f, h * 0.82f)
            close()
        }
        drawPath(
            path = vest,
            brush = Brush.verticalGradient(
                listOf(Color(0xFF558B2F), Color(0xFF33691E))
            )
        )

        // Scroll Pouches on Chest
        drawRect(color = Color(0xFF689F38), topLeft = Offset(w * 0.24f, h * 0.38f), size = Size(w * 0.20f, h * 0.28f))
        drawRect(color = Color(0xFF689F38), topLeft = Offset(w * 0.56f, h * 0.38f), size = Size(w * 0.20f, h * 0.28f))

        // Center Zipper
        drawLine(
            color = Color(0xFF2E7D32),
            start = Offset(w * 0.5f, h * 0.20f),
            end = Offset(w * 0.5f, h * 0.82f),
            strokeWidth = 3f
        )
    }
}

@Composable
fun SoldierPillsItemArt(modifier: Modifier = Modifier.size(36.dp)) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Ceramic Gourd Flask
        val flask = Path().apply {
            moveTo(w * 0.42f, h * 0.18f)
            lineTo(w * 0.58f, h * 0.18f)
            lineTo(w * 0.58f, h * 0.30f)
            cubicTo(w * 0.75f, h * 0.45f, w * 0.82f, h * 0.80f, w * 0.50f, h * 0.85f)
            cubicTo(w * 0.18f, h * 0.80f, w * 0.25f, h * 0.45f, w * 0.42f, h * 0.30f)
            close()
        }
        drawPath(
            path = flask,
            brush = Brush.radialGradient(
                listOf(Color(0xFFE0E0E0), Color(0xFF9E9E9E), Color(0xFF424242)),
                center = Offset(w * 0.45f, h * 0.50f)
            )
        )

        // Red Sealing Cord
        drawRect(
            color = Color(0xFFD32F2F),
            topLeft = Offset(w * 0.38f, h * 0.38f),
            size = Size(w * 0.24f, h * 0.08f)
        )

        // Red Pills next to flask
        drawCircle(color = Color(0xFFB71C1C), radius = w * 0.10f, center = Offset(w * 0.72f, h * 0.75f))
        drawCircle(color = Color(0xFFFF5252), radius = w * 0.04f, center = Offset(w * 0.70f, h * 0.73f))
    }
}

@Composable
fun SecretScrollItemArt(modifier: Modifier = Modifier.size(36.dp)) {
    RecruitmentSealScroll(modifier = modifier)
}
