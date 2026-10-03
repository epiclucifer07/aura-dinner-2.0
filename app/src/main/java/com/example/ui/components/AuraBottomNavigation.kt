package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.RestaurantMenu
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AuraCrimson
import com.example.ui.theme.AuraCrimsonSoft
import com.example.ui.theme.AuraSurfaceBorder
import com.example.ui.theme.AuraTextMuted
import com.example.ui.theme.AuraWhite
import com.example.ui.viewmodel.AppNavDestination

@Composable
fun AuraBottomNavigation(
    currentDestination: AppNavDestination,
    onNavigate: (AppNavDestination) -> Unit,
    cartCount: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .border(width = 0.5.dp, color = AuraSurfaceBorder)
            .navigationBarsPadding()
            .height(68.dp)
            .padding(horizontal = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavItem(
                destination = AppNavDestination.HOME,
                selected = currentDestination == AppNavDestination.HOME,
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home,
                onClick = { onNavigate(AppNavDestination.HOME) }
            )

            NavItem(
                destination = AppNavDestination.MENU,
                selected = currentDestination == AppNavDestination.MENU,
                selectedIcon = Icons.Filled.RestaurantMenu,
                unselectedIcon = Icons.Outlined.RestaurantMenu,
                onClick = { onNavigate(AppNavDestination.MENU) }
            )

            NavItem(
                destination = AppNavDestination.ORDER,
                selected = currentDestination == AppNavDestination.ORDER,
                selectedIcon = Icons.Filled.ShoppingBag,
                unselectedIcon = Icons.Outlined.ShoppingBag,
                badgeCount = cartCount,
                onClick = { onNavigate(AppNavDestination.ORDER) }
            )

            NavItem(
                destination = AppNavDestination.RESERVATIONS,
                selected = currentDestination == AppNavDestination.RESERVATIONS,
                selectedIcon = Icons.Filled.CalendarMonth,
                unselectedIcon = Icons.Outlined.CalendarMonth,
                onClick = { onNavigate(AppNavDestination.RESERVATIONS) }
            )
        }
    }
}

@Composable
private fun NavItem(
    destination: AppNavDestination,
    selected: Boolean,
    selectedIcon: ImageVector,
    unselectedIcon: ImageVector,
    badgeCount: Int = 0,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .testTag("nav_item_${destination.name.lowercase()}")
            .height(56.dp)
            .clip(CircleShape)
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(bounded = true, radius = 28.dp),
                onClick = onClick
            )
            .padding(horizontal = 14.dp, vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        BadgedBox(
            badge = {
                if (badgeCount > 0) {
                    Badge(
                        containerColor = AuraCrimson,
                        contentColor = AuraWhite
                    ) {
                        Text(text = badgeCount.toString(), fontSize = 10.sp)
                    }
                }
            }
        ) {
            Icon(
                imageVector = if (selected) selectedIcon else unselectedIcon,
                contentDescription = destination.label,
                tint = if (selected) AuraCrimson else AuraTextMuted,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = destination.label,
            fontSize = 11.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            color = if (selected) AuraCrimson else AuraTextMuted
        )
    }
}
