package com.example.ui.screens.recruit

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.R
import com.example.ui.components.*
import com.example.ui.theme.*

/**
 * Modern Anime Idle RPG Will of Fire Summoning Shrine:
 * Features a glowing chakra vortex summoning gate, drop rate breakdown,
 * 1x and 10x summon CTAs, and a celebratory SSR summon reveal dialog.
 */
@Composable
fun RecruitScreen(
    modifier: Modifier = Modifier
) {
    var summonCount by remember { mutableStateOf(10) }
    var showSummonResult by remember { mutableStateOf(false) }
    var lastSummonIsMulti by remember { mutableStateOf(false) }

    // Rotating Chakra Vortex Animation
    val infiniteTransition = rememberInfiniteTransition(label = "recruit_vortex")
    val vortexRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(12000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "vortex_spin"
    )

    val gatePulse by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "gate_pulse"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(ModernSlateDark)
    ) {
        // Atmospheric Dark Indigo / Crimson Background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFF140D1C),
                            Color(0xFF0D121B),
                            Color(0xFF06090E)
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(start = 14.dp, end = 14.dp, top = 8.dp, bottom = 84.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // TOP RESOURCE & PITY STATUS
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = ModernGlassFill,
                border = BorderStroke(
                    1.5.dp,
                    Brush.linearGradient(
                        listOf(
                            MetallicGoldBright.copy(alpha = 0.75f),
                            Color(0x33FFFFFF),
                            MetallicGoldDark.copy(alpha = 0.4f)
                        )
                    )
                ),
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "WILL OF FIRE SUMMONS",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 8.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = ShinobiSteel,
                                letterSpacing = 0.5.sp
                            )
                        )
                        Text(
                            text = "SSR Guaranteed in 8 Summons",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black,
                                color = MetallicGoldBright
                            )
                        )
                    }

                    // Available Scrolls Pill
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        RecruitmentSealScroll(modifier = Modifier.size(18.dp))
                        Text(
                            text = "12 Scrolls",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // CENTERPIECE: GLOWING CHAKRA SUMMONING GATE
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = ModernGlassFill,
                border = BorderStroke(
                    1.5.dp,
                    Brush.linearGradient(
                        listOf(
                            Color(0xFFE040FB).copy(alpha = 0.5f),
                            MetallicGoldBright.copy(alpha = 0.7f),
                            Color(0xFF7C4DFF).copy(alpha = 0.5f)
                        )
                    )
                ),
                shadowElevation = 14.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    // Swirling Chakra Nebula
                    Box(
                        modifier = Modifier
                            .size(240.dp)
                            .rotate(vortexRotation)
                            .background(
                                Brush.radialGradient(
                                    listOf(
                                        Color(0x999C27B0),
                                        Color(0x44673AB7),
                                        Color(0x2200E5FF),
                                        Color.Transparent
                                    )
                                ),
                                CircleShape
                            )
                    )

                    // Summoning Altar Torii Frame & Seal
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(110.dp)
                                .scale(gatePulse)
                                .clip(CircleShape)
                                .background(
                                    Brush.radialGradient(
                                        listOf(
                                            NarutoOrange.copy(alpha = 0.35f),
                                            ModernSlateDark
                                        )
                                    )
                                )
                                .border(
                                    2.5.dp,
                                    Brush.linearGradient(
                                        listOf(MetallicGoldBright, NarutoOrangeDark, MetallicGoldBright)
                                    ),
                                    CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            WillOfFireCrest(modifier = Modifier.size(54.dp))
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "COVENANT OF THE HOKAGE",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Black,
                                color = MetallicGoldBright,
                                letterSpacing = 1.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        // Rate Breakdown Pill
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = ModernSlateDark.copy(alpha = 0.85f),
                            border = BorderStroke(1.dp, Color(0x33FFFFFF))
                        ) {
                            Text(
                                text = "SSR: 4.61% • SR: 43.70% • R: 51.69%",
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 8.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = ShinobiSteel
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // SUMMON CTAS (1X and 10X)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Summon 1x Button
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = ModernSlateElevated,
                    border = BorderStroke(1.5.dp, MetallicGoldBright.copy(alpha = 0.6f)),
                    shadowElevation = 6.dp,
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            lastSummonIsMulti = false
                            showSummonResult = true
                        }
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(vertical = 10.dp)
                    ) {
                        Text(
                            text = "SUMMON 1X",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White
                            )
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            RecruitmentSealScroll(modifier = Modifier.size(14.dp))
                            Text(
                                text = "1 Scroll",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = ModernNeonAmber
                                )
                            )
                        }
                    }
                }

                // Summon 10x Button (10% Discount)
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = NarutoOrangeDark,
                    border = BorderStroke(2.dp, MetallicGoldBright),
                    shadowElevation = 10.dp,
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            lastSummonIsMulti = true
                            showSummonResult = true
                        }
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(vertical = 10.dp)
                    ) {
                        Text(
                            text = "SUMMON 10X",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White
                            )
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            RecruitmentSealScroll(modifier = Modifier.size(14.dp))
                            Text(
                                text = "10 Scrolls (10% OFF)",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color(0xFFFFF9C4)
                                )
                            )
                        }
                    }
                }
            }
        }

        // CELEBRATORY SUMMON REVEAL MODAL
        if (showSummonResult) {
            Dialog(onDismissRequest = { showSummonResult = false }) {
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = ModernSlateDark,
                    border = BorderStroke(2.dp, Brush.linearGradient(listOf(MetallicGoldBright, NarutoOrange))),
                    shadowElevation = 20.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(18.dp)
                    ) {
                        Text(
                            text = "SUMMON COVENANT SEALED!",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Black,
                                color = MetallicGoldBright
                            )
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // High-Res SSR Splash Card Reveal
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = ModernSlateCard,
                            border = BorderStroke(2.dp, MetallicGoldBright),
                            shadowElevation = 10.dp,
                            modifier = Modifier
                                .width(140.dp)
                                .height(175.dp)
                        ) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                Image(
                                    painter = painterResource(id = R.drawable.portrait_kakashi),
                                    contentDescription = "Kakashi Hatake SSR",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                                Surface(
                                    shape = RoundedCornerShape(topStart = 10.dp, bottomEnd = 10.dp),
                                    color = NarutoOrangeDark,
                                    modifier = Modifier.align(Alignment.TopStart)
                                ) {
                                    Text(
                                        text = "SSR ★★★★★",
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontSize = 8.sp,
                                            fontWeight = FontWeight.Black,
                                            color = Color.White
                                        )
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Kakashi Hatake (SSR)",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White
                            )
                        )
                        Text(
                            text = if (lastSummonIsMulti) "+9 Duplicate Ascension Fragments Acquired" else "New Shinobi Added to Squad Roster",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 9.5.sp,
                                color = ShinobiSteel,
                                textAlign = TextAlign.Center
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = NarutoOrangeDark,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { showSummonResult = false }
                        ) {
                            Text(
                                text = "CLAIM SHINOBI",
                                modifier = Modifier.padding(vertical = 10.dp),
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color.White,
                                    textAlign = TextAlign.Center
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
