package com.example.ui.screens.shop

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.sample.ItemCategory
import com.example.data.sample.SampleRepository
import com.example.data.sample.SampleShopItem
import com.example.ui.components.*
import com.example.ui.theme.*

/**
 * UI-054 Ninja Tool Shop (Shop interior & entrance):
 * Timber shop interior with Tenten's weapons counter, category cloth tabs
 * (Supplies, Equipment, Appearance), 3-column catalog grid with ItemTiles,
 * and persistent item inspection detail sheet with purchase CTA.
 */
@Composable
fun NinjaToolShopScreen(
    onBackToVillage: () -> Unit,
    modifier: Modifier = Modifier
) {
    val catalog: List<SampleShopItem> = SampleRepository.sampleShopItems
    val userCurrency = SampleRepository.sampleCurrency
    var selectedCategoryIndex by remember { mutableIntStateOf(0) }
    val categories = listOf("Supplies", "Equipment", "Appearance")

    val currentCategory = when (selectedCategoryIndex) {
        0 -> ItemCategory.SUPPLIES
        1 -> ItemCategory.EQUIPMENT
        else -> ItemCategory.APPEARANCE
    }

    val filteredItems: List<SampleShopItem> = remember(selectedCategoryIndex) {
        catalog.filter { it.category == currentCategory }
    }

    var selectedItem by remember {
        mutableStateOf<SampleShopItem?>(filteredItems.firstOrNull())
    }

    // Keep selectedItem in sync if category changes
    LaunchedEffect(currentCategory) {
        selectedItem = filteredItems.firstOrNull()
    }

    Box(modifier = modifier.fillMaxSize()) {
        // Layer 1: Illustrated Timber Tool Shop Interior Background
        Image(
            painter = painterResource(id = R.drawable.bg_ninja_tool_shop),
            contentDescription = "Ninja Tool Shop Interior Background",
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
                            NightNavyDark.copy(alpha = 0.90f),
                            NightNavyDark.copy(alpha = 0.70f),
                            NightNavyDark.copy(alpha = 0.95f)
                        )
                    )
                )
        )

        // Layer 2: Shop Layout
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // Header
            DestinationHeader(
                title = "NINJA TOOL SHOP",
                subtitle = "Tenten's Arsenal & Provisions",
                onBackClick = onBackToVillage
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Resource Bar (Ryo)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                ResourceChip(label = "${userCurrency.ryo}", iconEmoji = "🪙")
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Merchant Greeting Banner (Tenten)
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                color = NightNavySurface.copy(alpha = 0.95f),
                border = BorderStroke(1.dp, WoodBorder)
            ) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .border(1.5.dp, NarutoGold, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        ShinobiFacePortrait(
                            shinobiId = "tenten_01",
                            modifier = Modifier.size(44.dp)
                        )
                    }

                    Column {
                        Text(
                            text = "Tenten (Shopkeeper)",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = NarutoGold
                            )
                        )
                        Text(
                            text = "“Stock up on ninja tools before embarking on Chapter 2 missions!”",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = ParchmentBg,
                                fontSize = 11.sp,
                                lineHeight = 15.sp
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Ornate Timber Drop Box Picker (AFK Arena / Mythic Heroes Marketplace Selector)
            TimberDropDownPicker(
                label = "Category Filter",
                options = categories,
                selectedIndex = selectedCategoryIndex,
                onSelectIndex = { selectedCategoryIndex = it },
                icon = { ToolShopAnvilCrest(modifier = Modifier.size(24.dp)) }
            )

            Spacer(modifier = Modifier.height(10.dp))

            // 3-Column Catalog Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 8.dp)
            ) {
                items(filteredItems) { item ->
                    ItemTile(
                        name = item.name,
                        iconEmoji = item.iconEmoji,
                        priceText = "${item.price} Ryo",
                        quantity = item.quantity,
                        isSelected = selectedItem?.id == item.id,
                        isSoldOut = item.isSoldOut,
                        isOwned = item.isOwned,
                        onClick = { selectedItem = item }
                    )
                }
            }

            // Bottom Item Detail Tray
            selectedItem?.let { item ->
                ItemDetailSheet(
                    item = item,
                    userRyo = userCurrency.ryo,
                    onDismiss = { selectedItem = null }
                )
            }
        }
    }
}
