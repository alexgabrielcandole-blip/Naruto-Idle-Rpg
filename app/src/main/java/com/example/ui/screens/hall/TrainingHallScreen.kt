package com.example.ui.screens.hall

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.data.sample.SampleRepository
import com.example.ui.components.*
import com.example.ui.theme.*

enum class TrainingHallVariant {
    AFFORDABLE,
    INSUFFICIENT_RESOURCES,
    CAPPED
}

/**
 * UI-023 Training Hall (Academy Yard):
 * Dedicated screen implementing the single-currency squad level investment model.
 * Renders the 5-shinobi squad in compact practice formation on training pads,
 * prominent Shared Level board, cost breakdown, and dominant Train CTA.
 */
@Composable
fun TrainingHallScreen(
    onBackToVillage: () -> Unit,
    modifier: Modifier = Modifier,
    variant: TrainingHallVariant = TrainingHallVariant.AFFORDABLE
) {
    val scrollState = rememberScrollState()
    val squadShinobi = SampleRepository.sampleShinobiList.filter { it.isDeployed }
    val currency = SampleRepository.sampleCurrency

    Box(modifier = modifier.fillMaxSize()) {
        // Layer 1: Illustrated Academy Training Yard Background
        Image(
            painter = painterResource(id = R.drawable.bg_academy_training_yard),
            contentDescription = "Academy Training Yard Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Tint overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            NightNavyDark.copy(alpha = 0.88f),
                            NightNavyDark.copy(alpha = 0.65f),
                            NightNavyDark.copy(alpha = 0.95f)
                        )
                    )
                )
        )

        // Layer 2: Main Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Destination Header
            DestinationHeader(
                title = "ACADEMY TRAINING HALL",
                subtitle = "Squad Resonance Chamber",
                onBackClick = onBackToVillage
            )

            // Resource Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ResourceChip(label = "${currency.ryo}", iconEmoji = "🪙")
                    ResourceChip(label = "${currency.shinobiExp}", iconEmoji = "⚡")
                }
            }

            // Shared Level Resonance Board
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(8.dp, RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                color = NightNavySurface.copy(alpha = 0.95f),
                border = BorderStroke(2.dp, NarutoGold)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "SQUAD RESONANCE LEVEL",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Black,
                            color = NarutoGold,
                            fontSize = 11.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = if (variant == TrainingHallVariant.CAPPED) "20" else "10",
                            style = MaterialTheme.typography.displayMedium.copy(
                                fontWeight = FontWeight.Black,
                                color = ParchmentBg,
                                fontSize = 48.sp
                            )
                        )
                        Text(
                            text = "/ 20 MAX",
                            modifier = Modifier.padding(bottom = 8.dp),
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = ShinobiSteel
                            )
                        )
                    }

                    Text(
                        text = "Single Level Pool • All 5 Active Shinobi Benefit Equally",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = ChakraCyan,
                            fontSize = 11.sp
                        )
                    )
                }
            }

            // Squad Formation on Practice Pads
            Text(
                text = "ACTIVE SQUAD IN FORMATION",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Black,
                    color = NarutoGold,
                    fontSize = 10.sp
                )
            )

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                color = NightNavyCard.copy(alpha = 0.9f),
                border = BorderStroke(1.dp, WoodBorder)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Front line (2 slots)
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        FormationSlotPad(
                            slotIndex = 0,
                            isFrontLine = true,
                            occupant = squadShinobi.getOrNull(0)
                        )
                        FormationSlotPad(
                            slotIndex = 1,
                            isFrontLine = true,
                            occupant = squadShinobi.getOrNull(1)
                        )
                    }

                    // Rear line (3 slots)
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        FormationSlotPad(
                            slotIndex = 2,
                            isFrontLine = false,
                            occupant = squadShinobi.getOrNull(2)
                        )
                        FormationSlotPad(
                            slotIndex = 3,
                            isFrontLine = false,
                            occupant = squadShinobi.getOrNull(3)
                        )
                        FormationSlotPad(
                            slotIndex = 4,
                            isFrontLine = false,
                            occupant = squadShinobi.getOrNull(4)
                        )
                    }
                }
            }

            // Training Order / Cost Breakdown Panel
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                color = NightNavySurface.copy(alpha = 0.95f),
                border = BorderStroke(1.dp, WoodBorder)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (variant == TrainingHallVariant.CAPPED) "MAX ACADEMY CAP REACHED" else "ADVANCEMENT TO LEVEL 11",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = if (variant == TrainingHallVariant.CAPPED) AmberYellow else ParchmentBg
                            )
                        )
                        Text(
                            text = if (variant == TrainingHallVariant.CAPPED) "Cap 20" else "+8% Squad ATK & HP",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = LeafGreen
                            )
                        )
                    }

                    if (variant != TrainingHallVariant.CAPPED) {
                        // EXP Progress Rail
                        val expCurrent = if (variant == TrainingHallVariant.INSUFFICIENT_RESOURCES) 1400L else 3400L
                        ProgressRail(
                            current = expCurrent,
                            max = 5000L,
                            fillColor = ChakraCyan,
                            label = "SHINOBI EXP",
                            valueText = "$expCurrent / 5,000"
                        )

                        // Ryo Progress Rail
                        val ryoCurrent = if (variant == TrainingHallVariant.INSUFFICIENT_RESOURCES) 450L else 2000L
                        ProgressRail(
                            current = ryoCurrent,
                            max = 2000L,
                            fillColor = NarutoGold,
                            label = "VILLAGE RYO",
                            valueText = "$ryoCurrent / 2,000"
                        )
                    } else {
                        Text(
                            text = "To unlock resonance beyond Level 20, complete the Chunin Exam Trial at the Hokage Office.",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = ShinobiSteel,
                                lineHeight = 16.sp
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Dominant Training CTA
                    val (ctaState, ctaText, lockedReason) = when (variant) {
                        TrainingHallVariant.AFFORDABLE -> Triple(
                            ButtonVisualState.NORMAL,
                            "Train Squad to Lv.11 (2,000 Ryo)",
                            null
                        )
                        TrainingHallVariant.INSUFFICIENT_RESOURCES -> Triple(
                            ButtonVisualState.DISABLED,
                            "Insufficient Resources",
                            "Requires 2,000 Ryo and 5,000 EXP"
                        )
                        TrainingHallVariant.CAPPED -> Triple(
                            ButtonVisualState.LOCKED,
                            "Academy Cap Reached",
                            "Complete Chunin Exams to raise squad level ceiling"
                        )
                    }

                    EmbossedNinjaCrestButton(
                        text = ctaText,
                        visualState = ctaState,
                        crest = { TrainingBladesCrest(modifier = Modifier.size(24.dp)) },
                        lockedReason = lockedReason,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // Glossary Tooltip
            GlossaryTooltip(
                title = "The Resonance Principle (Game Theory Bible §03)",
                explanation = "In this ninja RPG, leveling up is never done individually per character. Upgrading your training level elevates all 5 deployed team members simultaneously."
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
