package com.example.ui.screens.campaign

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.R
import com.example.ui.components.*
import com.example.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

data class Combatant(
    val id: String,
    val name: String,
    val level: Int,
    val maxHp: Int,
    var currentHp: Int,
    var currentChakra: Float, // 0f to 1f
    val isAlly: Boolean,
    val drawableRes: Int? = null
)

data class FloatingDamage(
    val id: Long,
    val text: String,
    val isCrit: Boolean,
    val isHeal: Boolean,
    val xOffset: Float,
    val yOffset: Float
)

/**
 * Modern Anime Idle RPG 5v5 Live Auto-Combat Campaign Stage:
 * Features 5 allied shinobi vs 5 enemy rogue ninja, animated combat lunges,
 * glowing chakra VFX, live floating damage numbers, and battle speed controls.
 */
@Composable
fun CampaignScreen(
    modifier: Modifier = Modifier
) {
    var isAutoSkill by remember { mutableStateOf(true) }
    var isDoubleSpeed by remember { mutableStateOf(true) }
    var battleRound by remember { mutableStateOf(1) }
    var activeAttackerId by remember { mutableStateOf<String?>("naruto") }
    var floatingDamages by remember { mutableStateOf(listOf<FloatingDamage>()) }
    var damageCounter by remember { mutableStateOf(100L) }
    var bossModalOpen by remember { mutableStateOf(false) }

    // Breathing pulse for Boss Encounter CTA
    val infiniteTransition = rememberInfiniteTransition(label = "campaign_pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.06f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "boss_pulse"
    )

    // Allied Squad
    val allies = remember {
        mutableStateListOf(
            Combatant("naruto", "Naruto", 10, 24500, 21800, 0.85f, true, R.drawable.portrait_naruto),
            Combatant("sasuke", "Sasuke", 10, 22100, 19400, 0.65f, true, R.drawable.portrait_sasuke),
            Combatant("sakura", "Sakura", 10, 19800, 19800, 1.0f, true, R.drawable.portrait_sakura),
            Combatant("kakashi", "Kakashi", 10, 26000, 24200, 0.40f, true, R.drawable.portrait_kakashi),
            Combatant("tenten", "Tenten", 10, 18400, 16500, 0.90f, true, R.drawable.portrait_tenten)
        )
    }

    // Enemy Rogues
    val enemies = remember {
        mutableStateListOf(
            Combatant("boss_chunin", "Rogue Chunin", 12, 38000, 26400, 0.70f, false),
            Combatant("mist_assassin", "Mist Hunter", 10, 19500, 12200, 0.30f, false),
            Combatant("sound_genin", "Sound Striker", 10, 18000, 14000, 0.50f, false),
            Combatant("curse_warrior", "Curse Bruiser", 11, 24000, 17500, 0.20f, false),
            Combatant("sand_mercenary", "Sand Scout", 10, 17000, 9500, 0.80f, false)
        )
    }

    // Live Idle Combat Simulation Coroutine Loop
    LaunchedEffect(isDoubleSpeed) {
        val delayTime = if (isDoubleSpeed) 1000L else 1800L
        while (isActive) {
            delay(delayTime)
            battleRound++

            // Alternate attackers
            val attacker = if (battleRound % 2 == 1) {
                allies.random()
            } else {
                enemies.random()
            }
            activeAttackerId = attacker.id

            // Generate floating damage popups
            val isCrit = (battleRound % 3 == 0)
            val isHeal = (attacker.id == "sakura" && battleRound % 4 == 0)
            val damageValue = if (isHeal) "+1,450 HEAL" else if (isCrit) "${2400 + (100..800).random()} CRIT!" else "${1100 + (50..400).random()}"

            val newDmg = FloatingDamage(
                id = damageCounter++,
                text = damageValue,
                isCrit = isCrit,
                isHeal = isHeal,
                xOffset = (-30..30).random().toFloat(),
                yOffset = (-20..20).random().toFloat()
            )

            floatingDamages = (floatingDamages + newDmg).takeLast(4)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(ModernSlateDark)
    ) {
        // BACKGROUND: Battle Panorama with Atmospheric Dark Vignette
        Image(
            painter = painterResource(id = R.drawable.bg_konoha_village),
            contentDescription = "Campaign Battle Arena",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Dark Combat Atmosphere Scrim
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xEE080D14),
                            Color(0xCC0D141F),
                            Color(0xF0080D14)
                        )
                    )
                )
        )

        // MAIN COMBAT VIEWPORT
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(start = 14.dp, end = 14.dp, top = 8.dp, bottom = 84.dp)
        ) {
            // TOP STAGE TELEMETRY BAR
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = ModernGlassFill,
                border = BorderStroke(
                    1.5.dp,
                    Brush.linearGradient(
                        listOf(
                            MetallicGoldBright.copy(alpha = 0.7f),
                            Color(0x33FFFFFF),
                            MetallicGoldDark.copy(alpha = 0.4f)
                        )
                    )
                ),
                shadowElevation = 10.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(ModernNeonAmber)
                            )
                            Text(
                                text = "STAGE 2-4",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Black,
                                    color = MetallicGoldBright,
                                    letterSpacing = 0.5.sp
                                )
                            )
                        }
                        Text(
                            text = "The Broken Supply Road • Wave 2/3",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 10.sp,
                                color = ShinobiSteel,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }

                    // Battle Controls (2X Speed & Auto Ultimate)
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // 2X Speed Toggle
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isDoubleSpeed) ModernSlateElevated else ModernSlateDark,
                            border = BorderStroke(
                                1.dp,
                                if (isDoubleSpeed) ModernNeonAmber else Color(0x33FFFFFF)
                            ),
                            modifier = Modifier.clickable { isDoubleSpeed = !isDoubleSpeed }
                        ) {
                            Text(
                                text = if (isDoubleSpeed) "2X SPEED" else "1X SPEED",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Black,
                                    color = if (isDoubleSpeed) ModernNeonAmber else ShinobiSteel
                                )
                            )
                        }

                        // AUTO Skill Toggle
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isAutoSkill) ModernSlateElevated else ModernSlateDark,
                            border = BorderStroke(
                                1.dp,
                                if (isAutoSkill) LeafGreen else Color(0x33FFFFFF)
                            ),
                            modifier = Modifier.clickable { isAutoSkill = !isAutoSkill }
                        ) {
                            Text(
                                text = "AUTO",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Black,
                                    color = if (isAutoSkill) Color(0xFF69F0AE) else ShinobiSteel
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // LIVE 5v5 COMBAT ARENA
            Surface(
                shape = RoundedCornerShape(18.dp),
                color = ModernSlateDark.copy(alpha = 0.85f),
                border = BorderStroke(
                    1.dp,
                    Brush.linearGradient(
                        listOf(
                            MetallicGoldBright.copy(alpha = 0.4f),
                            Color(0x22FFFFFF),
                            MetallicGoldDark.copy(alpha = 0.2f)
                        )
                    )
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 10.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // ALLIED SQUAD COLUMN (LEFT)
                        Column(
                            verticalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.fillMaxHeight()
                        ) {
                            allies.forEach { combatant ->
                                val isAttacking = activeAttackerId == combatant.id
                                CombatantBattleRow(
                                    combatant = combatant,
                                    isAttacking = isAttacking,
                                    isAlly = true
                                )
                            }
                        }

                        // ENEMY SQUAD COLUMN (RIGHT)
                        Column(
                            verticalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.fillMaxHeight(),
                            horizontalAlignment = Alignment.End
                        ) {
                            enemies.forEach { combatant ->
                                val isAttacking = activeAttackerId == combatant.id
                                CombatantBattleRow(
                                    combatant = combatant,
                                    isAttacking = isAttacking,
                                    isAlly = false
                                )
                            }
                        }
                    }

                    // DYNAMIC FLOATING DAMAGE POPUPS (CENTER OF ARENA)
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(160.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        floatingDamages.forEach { dmg ->
                            key(dmg.id) {
                                FloatingDamageText(damage = dmg)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // BOTTOM BATTLE PROGRESSION CTA (CHALLENGE BOSS)
            Surface(
                shape = RoundedCornerShape(18.dp),
                color = ModernGlassFill,
                border = BorderStroke(
                    2.dp,
                    Brush.linearGradient(listOf(MetallicGoldBright, NarutoOrange))
                ),
                shadowElevation = 14.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .scale(pulseScale)
                    .clickable { bossModalOpen = true }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.radialGradient(listOf(NarutoOrangeLight, NarutoOrangeDark))
                                )
                                .border(1.5.dp, MetallicGoldBright, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Engage Boss",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Column {
                            Text(
                                text = "CHALLENGE BOSS ENCOUNTER",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Black,
                                    color = MetallicGoldBright,
                                    fontSize = 12.sp,
                                    letterSpacing = 0.5.sp
                                )
                            )
                            Text(
                                text = "Clear Stage 2-4 to unlock Chunnin Exams",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 9.5.sp,
                                    color = ShinobiSteel
                                )
                            )
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = NarutoOrangeDark
                    ) {
                        Text(
                            text = "BATTLE",
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White
                            )
                        )
                    }
                }
            }
        }

        // BOSS CHALLENGE CONFIRMATION MODAL
        if (bossModalOpen) {
            ConfirmationDialog(
                title = "BOSS ENCOUNTER: STAGE 2-4",
                message = "Engage the Rogue Chunin Commander!\n\n• Recommended CP: 95,000\n• Your Squad CP: 128,450\n\nVictory Rewards:\n+1,200 Ryo • +3,400 EXP • 2x Ninja Scrolls",
                confirmText = "Commence Battle",
                cancelText = "Retreat",
                onDismiss = { bossModalOpen = false }
            )
        }
    }
}

@Composable
private fun CombatantBattleRow(
    combatant: Combatant,
    isAttacking: Boolean,
    isAlly: Boolean
) {
    val attackOffset by animateFloatAsState(
        targetValue = if (isAttacking) (if (isAlly) 20f else -20f) else 0f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
        label = "combat_lunge"
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .offset(x = attackOffset.dp)
            .padding(vertical = 2.dp)
    ) {
        if (!isAlly) {
            // Bars on left for enemy
            CombatBars(combatant = combatant, isAlly = false)
        }

        // Combatant Avatar Box with Gold / Red Ring
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(ModernSlateDark)
                .border(
                    width = if (isAttacking) 2.dp else 1.dp,
                    brush = if (isAlly) {
                        Brush.linearGradient(listOf(MetallicGoldBright, MetallicGoldDark))
                    } else {
                        Brush.linearGradient(listOf(Color(0xFFFF5252), Color(0xFFB71C1C)))
                    },
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (combatant.drawableRes != null) {
                Image(
                    painter = painterResource(id = combatant.drawableRes),
                    contentDescription = combatant.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Text(
                    text = combatant.name.take(1),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Black,
                        color = if (isAlly) MetallicGoldBright else Color(0xFFFF5252)
                    )
                )
            }
        }

        if (isAlly) {
            // Bars on right for ally
            CombatBars(combatant = combatant, isAlly = true)
        }
    }
}

@Composable
private fun CombatBars(
    combatant: Combatant,
    isAlly: Boolean
) {
    Column(
        horizontalAlignment = if (isAlly) Alignment.Start else Alignment.End,
        modifier = Modifier.width(68.dp)
    ) {
        Text(
            text = combatant.name,
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = if (isAlly) Color.White else Color(0xFFFFCDD2)
            ),
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(2.dp))

        // Health Bar (Green/Gold)
        val hpPercent = (combatant.currentHp.toFloat() / combatant.maxHp).coerceIn(0f, 1f)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(Color(0xFF263238))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(hpPercent)
                    .background(
                        Brush.horizontalGradient(
                            listOf(Color(0xFF69F0AE), LeafGreen)
                        )
                    )
            )
        }

        Spacer(modifier = Modifier.height(2.dp))

        // Chakra / Energy Bar (Electric Cyan)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(3.dp)
                .clip(RoundedCornerShape(1.5.dp))
                .background(Color(0xFF1A237E).copy(alpha = 0.5f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(combatant.currentChakra)
                    .background(
                        Brush.horizontalGradient(
                            listOf(ModernNeonCyan, Color(0xFF00B0FF))
                        )
                    )
            )
        }
    }
}

@Composable
private fun FloatingDamageText(damage: FloatingDamage) {
    var offsetY by remember { mutableStateOf(0f) }
    var alphaVal by remember { mutableStateOf(1f) }

    LaunchedEffect(damage.id) {
        animate(
            initialValue = 0f,
            targetValue = -35f,
            animationSpec = tween(700, easing = LinearOutSlowInEasing)
        ) { value, _ ->
            offsetY = value
            alphaVal = (1f - (value / -35f)).coerceIn(0f, 1f)
        }
    }

    Text(
        text = damage.text,
        modifier = Modifier
            .offset(x = damage.xOffset.dp, y = (damage.yOffset + offsetY).dp),
        style = MaterialTheme.typography.titleMedium.copy(
            fontSize = if (damage.isCrit) 14.sp else 12.sp,
            fontWeight = FontWeight.Black,
            color = when {
                damage.isHeal -> Color(0xFF69F0AE).copy(alpha = alphaVal)
                damage.isCrit -> ModernNeonAmber.copy(alpha = alphaVal)
                else -> Color.White.copy(alpha = alphaVal)
            },
            textAlign = TextAlign.Center
        )
    )
}
