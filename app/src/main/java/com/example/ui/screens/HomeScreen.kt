package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.GlobalLocations
import com.example.data.model.MenuItem
import com.example.data.model.RestaurantBranch
import com.example.ui.components.DishCard
import com.example.ui.theme.AuraCrimson
import com.example.ui.theme.AuraCrimsonDark
import com.example.ui.theme.AuraCrimsonSoft
import com.example.ui.theme.AuraSurfaceBorder
import com.example.ui.theme.AuraTextMuted
import com.example.ui.theme.AuraTextSecondary
import com.example.ui.theme.AuraWhite
import com.example.ui.viewmodel.AppNavDestination

@Composable
fun HomeScreen(
    featuredDishes: List<MenuItem>,
    branch: RestaurantBranch = GlobalLocations.flagshipBranches[0],
    onSwitchBranch: () -> Unit = {},
    onNavigate: (AppNavDestination) -> Unit,
    onDishClick: (MenuItem) -> Unit,
    onQuickAdd: (MenuItem) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // Hero Section with Welcome Text
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(380.dp)
            ) {
                // Background Ambiance Photo
                Image(
                    painter = painterResource(id = R.drawable.img_hero_restaurant),
                    contentDescription = "Aura Dining Interior",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Elegant Gradient Overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.35f),
                                    Color.Black.copy(alpha = 0.55f),
                                    AuraCrimsonDark.copy(alpha = 0.88f)
                                )
                            )
                        )
                )

                // Hero Content
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(AuraWhite.copy(alpha = 0.18f))
                            .border(0.5.dp, AuraWhite.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
                            .clickable(onClick = onSwitchBranch)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = AuraWhite,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${branch.cityName.uppercase()} • ${branch.country.uppercase()} (SWITCH)",
                            color = AuraWhite,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Welcome to Aura Dinners",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Normal,
                            fontFamily = FontFamily.Serif
                        ),
                        color = AuraWhite
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "An exquisite synthesis of Royal Indian gastronomy, French-Japanese culinary craft, and warm hospitality.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = AuraWhite.copy(alpha = 0.9f),
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Quick CTAs
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = { onNavigate(AppNavDestination.RESERVATIONS) },
                            modifier = Modifier
                                .testTag("home_reserve_table_button")
                                .weight(1f)
                                .height(46.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AuraWhite,
                                contentColor = AuraCrimsonDark
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CalendarMonth,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Reserve Table",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }

                        OutlinedButton(
                            onClick = { onNavigate(AppNavDestination.MENU) },
                            modifier = Modifier
                                .testTag("home_explore_menu_button")
                                .weight(1f)
                                .height(46.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = AuraWhite
                            ),
                            border = androidx.compose.foundation.BorderStroke(1.dp, AuraWhite),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.MenuBook,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Explore Menu",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }

        // Quick Highlights Bar
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .border(width = 0.5.dp, color = AuraSurfaceBorder)
                    .padding(vertical = 14.dp, horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                FeatureHighlight(
                    icon = Icons.Default.RestaurantMenu,
                    title = "Royal Indian",
                    subtitle = "Dum Biryani & Tandoor"
                )
                FeatureHighlight(
                    icon = Icons.Default.Shield,
                    title = "Allergy Certified",
                    subtitle = "Transparent Kitchen"
                )
                FeatureHighlight(
                    icon = Icons.Default.Public,
                    title = "Global Metros",
                    subtitle = "NYC, Mumbai, London"
                )
            }
        }

        // Chef's Signature Tasting Selection
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, start = 16.dp, end = 16.dp, bottom = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Chef's Signatures",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Bold
                            ),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Royal Indian & Continental tasting highlights",
                            style = MaterialTheme.typography.bodySmall,
                            color = AuraTextSecondary
                        )
                    }

                    Text(
                        text = "View All",
                        color = AuraCrimson,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clickable { onNavigate(AppNavDestination.MENU) }
                            .padding(4.dp)
                    )
                }
            }
        }

        // Dish Cards
        items(featuredDishes.take(4)) { dish ->
            Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                DishCard(
                    item = dish,
                    onDishClick = { onDishClick(dish) },
                    onQuickAdd = { onQuickAdd(dish) }
                )
            }
        }

        // Dining Experience & Hours Card (Live based on selected global branch)
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, AuraSurfaceBorder)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = branch.branchName,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontFamily = FontFamily.Serif,
                                    fontWeight = FontWeight.Bold
                                ),
                                color = AuraCrimson
                            )
                            Text(
                                text = "${branch.cityName}, ${branch.stateOrRegion}, ${branch.country}",
                                fontSize = 12.sp,
                                color = AuraTextSecondary
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(AuraCrimsonSoft)
                                .clickable(onClick = onSwitchBranch)
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "Switch City",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = AuraCrimsonDark
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Every course at Aura is designed as an intimate sensory narrative. Enjoy authentic Awadhi dum cooking, tandoori clay-fired delicacies, and rare cellar vintages.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = AuraTextSecondary,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Hours & Location Rows
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = null,
                            tint = AuraCrimson,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = branch.hours,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = AuraCrimson,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = branch.address,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Call,
                            contentDescription = null,
                            tint = AuraCrimson,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "${branch.phone} • Concierge & Valet Available",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }

        // Restaurant Website Footer
        item {
            Spacer(modifier = Modifier.height(24.dp))
            com.example.ui.components.WebsiteFooter(
                onNavigate = onNavigate,
                onOpenLocations = onSwitchBranch
            )
        }
    }
}

@Composable
private fun FeatureHighlight(
    icon: ImageVector,
    title: String,
    subtitle: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(AuraCrimsonSoft),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = AuraCrimson,
                modifier = Modifier.size(18.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = subtitle,
            fontSize = 10.sp,
            color = AuraTextMuted
        )
    }
}
