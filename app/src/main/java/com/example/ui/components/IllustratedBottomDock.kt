package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R

/**
 * Reusable Four-Destination Illustrated Bottom Dock matching the authentic Naruto RPG visual baseline:
 * 1. Home: Illustrated golden Leaf emblem, readable ivory label, no underline when unselected.
 * 2. Squad: Three shinobi busts with Kakashi in the center, warm gold label and radiant gold underline when selected.
 * 3. Campaign: Folded parchment tactical map with red route trail.
 * 4. Recruit: Rolled ninja summoning scroll with red wax seal.
 *
 * Design Details:
 * - Deep teal background with vertical gradient (#092934 to #031218).
 * - Thin 1.2dp warm brass top border with subtle shimmer.
 * - Four equal-width tab destinations (>= 48dp touch targets).
 * - Illustrated icons rendered with Image and NO colorFilter tint to preserve full painted colors and face details.
 * - Native 12sp Text labels and accessible Tab semantics.
 * - Respects system gesture navigation insets via navigationBarsPadding().
 */
@Composable
fun IllustratedBottomDock(
    modifier: Modifier = Modifier,
    selectedTab: String = "Squad",
    onTabSelected: (String) -> Unit = {}
) {
    val tabs = remember {
        listOf(
            DockTabItem(
                id = "Home",
                title = "Home",
                iconRes = R.drawable.ic_dock_home,
                testTag = "tab_home"
            ),
            DockTabItem(
                id = "Squad",
                title = "Squad",
                iconRes = R.drawable.ic_dock_squad,
                testTag = "tab_squad"
            ),
            DockTabItem(
                id = "Campaign",
                title = "Campaign",
                iconRes = R.drawable.ic_dock_campaign,
                testTag = "tab_campaign"
            ),
            DockTabItem(
                id = "Recruit",
                title = "Recruit",
                iconRes = R.drawable.ic_dock_recruit,
                testTag = "tab_recruit"
            )
        )
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF092934),
                        Color(0xFF051921),
                        Color(0xFF031218)
                    )
                )
            )
            .navigationBarsPadding()
    ) {
        // Thin warm brass top border (1.2 dp)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.2.dp)
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0x66C5A059),
                            Color(0xFFD8B26A),
                            Color(0xFFE9C57D),
                            Color(0xFFD8B26A),
                            Color(0x66C5A059)
                        )
                    )
                )
                .align(Alignment.TopCenter)
        )

        // Row of 4 equally-spaced tabs with subtle vertical dividers
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            tabs.forEachIndexed { index, tab ->
                val isSelected = tab.title.equals(selectedTab, ignoreCase = true) ||
                        tab.id.equals(selectedTab, ignoreCase = true)

                DockTab(
                    item = tab,
                    isSelected = isSelected,
                    onClick = { onTabSelected(tab.id) },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                )

                // Subtle vertical divider between tabs (matching wide navigation reference)
                if (index < tabs.lastIndex) {
                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(34.dp)
                            .background(Color(0x184A788A))
                    )
                }
            }
        }
    }
}

private data class DockTabItem(
    val id: String,
    val title: String,
    val iconRes: Int,
    val testTag: String
)

@Composable
private fun DockTab(
    item: DockTabItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = Color(0xFFFFD54F))
            ) { onClick() }
            .testTag(item.testTag)
            .semantics {
                contentDescription = if (isSelected) "${item.title} Tab (Selected)" else "${item.title} Tab"
                role = Role.Tab
                selected = isSelected
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Illustrated Icon (Image WITHOUT ColorFilter tint to preserve natural anime colors & face details)
        Image(
            painter = painterResource(id = item.iconRes),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .height(34.dp)
                .padding(horizontal = 2.dp)
        )

        Spacer(modifier = Modifier.height(2.5.dp))

        // Native Tab Label
        Text(
            text = item.title,
            style = androidx.compose.ui.text.TextStyle(
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) Color(0xFFFFD54F) else Color(0xFFFFF2D7),
                letterSpacing = 0.2.sp
            )
        )

        Spacer(modifier = Modifier.height(3.dp))

        // Warm Gold Underline indicator (Selected tab only)
        if (isSelected) {
            Box(
                modifier = Modifier
                    .width(34.dp)
                    .height(2.5.dp)
                    .clip(RoundedCornerShape(1.2.dp))
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFFD69A30),
                                Color(0xFFFFE082),
                                Color(0xFFFFD54F),
                                Color(0xFFD69A30)
                            )
                        )
                    )
            )
        } else {
            // Invisible placeholder of identical height ensures uniform icon/text baseline
            Spacer(modifier = Modifier.height(2.5.dp))
        }
    }
}
