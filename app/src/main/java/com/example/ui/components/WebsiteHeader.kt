package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.example.ui.theme.AuraCrimson
import com.example.ui.theme.AuraCrimsonDark
import com.example.ui.theme.AuraCrimsonSoft
import com.example.ui.theme.AuraSurfaceBorder
import com.example.ui.theme.AuraTextMuted
import com.example.ui.theme.AuraTextSecondary
import com.example.ui.theme.AuraWhite
import com.example.ui.viewmodel.AppNavDestination

@Composable
fun WebsiteHeader(
    currentDestination: AppNavDestination,
    onNavigate: (AppNavDestination) -> Unit,
    currentCity: String,
    onLocationClick: () -> Unit,
    cartCount: Int,
    onCartClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .statusBarsPadding()
            .border(width = 0.5.dp, color = AuraSurfaceBorder)
    ) {
        // Top Announcement & Location Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(AuraCrimsonDark)
                .padding(horizontal = 14.dp, vertical = 6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "AURA GLOBAL DINING RESIDENCY • MICHELIN SELECTION",
                    color = AuraWhite.copy(alpha = 0.9f),
                    fontSize = 10.sp,
                    letterSpacing = 1.sp,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .clickable(onClick = onLocationClick)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = AuraWhite,
                        modifier = Modifier.size(11.dp)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = "Aura $currentCity",
                        color = AuraWhite,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        tint = AuraWhite,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }

        // Main Navigation Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Brand Logo
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onNavigate(AppNavDestination.HOME) }
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(AuraCrimson),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "A",
                        color = AuraWhite,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "AURA DINING",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.2.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "RESTAURANT & LOUNGE",
                        style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.sp),
                        color = AuraCrimson,
                        fontSize = 9.sp
                    )
                }
            }

            // Right Actions: Quick Reserve CTA & Cart
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (currentDestination != AppNavDestination.RESERVATIONS) {
                    Button(
                        onClick = { onNavigate(AppNavDestination.RESERVATIONS) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AuraCrimson,
                            contentColor = AuraWhite
                        ),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier
                            .height(34.dp)
                            .padding(end = 6.dp)
                    ) {
                        Text(
                            text = "Reserve",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                IconButton(
                    onClick = onCartClick,
                    modifier = Modifier
                        .testTag("website_header_cart_button")
                        .size(40.dp)
                ) {
                    BadgedBox(
                        badge = {
                            if (cartCount > 0) {
                                Badge(
                                    containerColor = AuraCrimson,
                                    contentColor = AuraWhite
                                ) {
                                    Text(
                                        text = cartCount.toString(),
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ShoppingBag,
                            contentDescription = "Cart",
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }
        }

        // Horizontal Website Links Bar (Home | Menu | Reservations | Order | Global Cities)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 14.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            WebNavLink(
                title = "Home",
                isSelected = currentDestination == AppNavDestination.HOME,
                onClick = { onNavigate(AppNavDestination.HOME) }
            )
            WebNavLink(
                title = "Menu & Allergens",
                isSelected = currentDestination == AppNavDestination.MENU,
                onClick = { onNavigate(AppNavDestination.MENU) }
            )
            WebNavLink(
                title = "Table Reservations",
                isSelected = currentDestination == AppNavDestination.RESERVATIONS,
                onClick = { onNavigate(AppNavDestination.RESERVATIONS) }
            )
            WebNavLink(
                title = "Online Ordering",
                isSelected = currentDestination == AppNavDestination.ORDER,
                onClick = { onNavigate(AppNavDestination.ORDER) }
            )
            WebNavLink(
                title = "Global Branches",
                isSelected = false,
                onClick = onLocationClick
            )
        }
    }
}

@Composable
private fun WebNavLink(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = title,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) AuraCrimson else MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(3.dp))
        Box(
            modifier = Modifier
                .width(if (isSelected) 24.dp else 0.dp)
                .height(2.dp)
                .background(if (isSelected) AuraCrimson else androidx.compose.ui.graphics.Color.Transparent)
        )
    }
}
