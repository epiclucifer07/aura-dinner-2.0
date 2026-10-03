package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DietaryTag
import com.example.data.model.MenuCategory
import com.example.data.model.MenuItem
import com.example.ui.components.DishCard
import com.example.ui.theme.AuraCrimson
import com.example.ui.theme.AuraCrimsonSoft
import com.example.ui.theme.AuraSurfaceBorder
import com.example.ui.theme.AuraTextMuted
import com.example.ui.theme.AuraTextSecondary
import com.example.ui.theme.AuraWhite
import com.example.ui.viewmodel.MenuFilterState

@Composable
fun MenuScreen(
    allDishes: List<MenuItem>,
    filterState: MenuFilterState,
    onCategorySelected: (MenuCategory) -> Unit,
    onDietaryTagToggled: (DietaryTag) -> Unit,
    onClearDietaryFilters: () -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    onDishClick: (MenuItem) -> Unit,
    onQuickAdd: (MenuItem) -> Unit,
    modifier: Modifier = Modifier
) {
    // Dynamic Filter Calculation
    val filteredDishes = allDishes.filter { dish ->
        val matchesCategory = (filterState.category == MenuCategory.ALL) || (dish.category == filterState.category)
        val matchesSearch = filterState.searchQuery.isBlank() ||
                dish.name.contains(filterState.searchQuery, ignoreCase = true) ||
                dish.subtitle.contains(filterState.searchQuery, ignoreCase = true) ||
                dish.description.contains(filterState.searchQuery, ignoreCase = true) ||
                dish.ingredients.any { it.contains(filterState.searchQuery, ignoreCase = true) }

        val matchesDietary = filterState.selectedDietary.isEmpty() ||
                filterState.selectedDietary.all { requiredTag ->
                    dish.dietaryTags.contains(requiredTag)
                }

        matchesCategory && matchesSearch && matchesDietary
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // Page Title & Header
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)
            ) {
                Text(
                    text = "Aura Dining Menu",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Seasonal culinary creations with transparent allergen guidance",
                    style = MaterialTheme.typography.bodySmall,
                    color = AuraTextSecondary
                )
            }
        }

        // Search Bar
        item {
            Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                OutlinedTextField(
                    value = filterState.searchQuery,
                    onValueChange = onSearchQueryChanged,
                    placeholder = { Text("Search dishes, ingredients, or spices...", fontSize = 13.sp) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = AuraCrimson,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    trailingIcon = {
                        if (filterState.searchQuery.isNotEmpty()) {
                            IconButton(onClick = { onSearchQueryChanged("") }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Clear search",
                                    tint = AuraTextMuted,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AuraCrimson,
                        unfocusedBorderColor = AuraSurfaceBorder,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface
                    ),
                    singleLine = true,
                    modifier = Modifier
                        .testTag("menu_search_field")
                        .fillMaxWidth()
                )
            }
        }

        // Category Pills
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(MenuCategory.values()) { category ->
                    val isSelected = filterState.category == category
                    Box(
                        modifier = Modifier
                            .testTag("category_pill_${category.name.lowercase()}")
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (isSelected) AuraCrimson else MaterialTheme.colorScheme.surface)
                            .border(
                                1.dp,
                                if (isSelected) AuraCrimson else AuraSurfaceBorder,
                                RoundedCornerShape(20.dp)
                            )
                            .clickable { onCategorySelected(category) }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = category.displayName,
                            color = if (isSelected) AuraWhite else MaterialTheme.colorScheme.onSurface,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    }
                }
            }
        }

        // Dietary & Allergen Filter Chips
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.FilterList,
                            contentDescription = null,
                            tint = AuraCrimson,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Dietary & Allergen Filter",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    if (filterState.selectedDietary.isNotEmpty()) {
                        Text(
                            text = "Clear all",
                            color = AuraCrimson,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .testTag("clear_dietary_filters_button")
                                .clickable(onClick = onClearDietaryFilters)
                                .padding(4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    contentPadding = PaddingValues(vertical = 4.dp)
                ) {
                    items(DietaryTag.values()) { tag ->
                        val isChecked = filterState.selectedDietary.contains(tag)
                        Box(
                            modifier = Modifier
                                .testTag("dietary_filter_${tag.name.lowercase()}")
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isChecked) AuraCrimsonSoft else MaterialTheme.colorScheme.surface)
                                .border(
                                    1.dp,
                                    if (isChecked) AuraCrimson else AuraSurfaceBorder,
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable { onDietaryTagToggled(tag) }
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = tag.displayName,
                                fontSize = 11.sp,
                                fontWeight = if (isChecked) FontWeight.Bold else FontWeight.Normal,
                                color = if (isChecked) AuraCrimson else AuraTextSecondary
                            )
                        }
                    }
                }
            }
        }

        // Active Count Bar
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${filteredDishes.size} creations available",
                    fontSize = 11.sp,
                    color = AuraTextMuted
                )

                Text(
                    text = "Tap dish for full allergen list",
                    fontSize = 11.sp,
                    color = AuraCrimson
                )
            }
        }

        // Dish Cards
        if (filteredDishes.isEmpty()) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No dishes match your dietary filters",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Try adjusting your allergen or category selections.",
                        style = MaterialTheme.typography.bodySmall,
                        color = AuraTextSecondary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            onClearDietaryFilters()
                            onSearchQueryChanged("")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AuraCrimson,
                            contentColor = AuraWhite
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Reset All Filters")
                    }
                }
            }
        } else {
            items(filteredDishes) { dish ->
                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                    DishCard(
                        item = dish,
                        onDishClick = { onDishClick(dish) },
                        onQuickAdd = { onQuickAdd(dish) }
                    )
                }
            }
        }
    }
}
