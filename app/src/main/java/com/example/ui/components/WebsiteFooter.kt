package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
fun WebsiteFooter(
    onNavigate: (AppNavDestination) -> Unit,
    onOpenLocations: () -> Unit,
    modifier: Modifier = Modifier
) {
    var newsletterEmail by remember { mutableStateOf("") }
    var subscribed by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .border(width = 1.dp, color = AuraSurfaceBorder)
            .padding(24.dp)
    ) {
        // Logo & Tagline
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(AuraCrimson),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "A",
                    color = AuraWhite,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = "AURA DINING",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.5.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "FINE CUISINE • ROYAL INDIAN • ARTISANAL CELLAR",
                    style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.sp),
                    color = AuraCrimson
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "Aura Dining is an international fine dining institution celebrated for its sensory gastronomy, Royal Awadhi dum culinary traditions, and uncompromising allergen safety standards across global metropolitan centers.",
            style = MaterialTheme.typography.bodySmall,
            color = AuraTextSecondary,
            lineHeight = 18.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Quick Navigation Links
        Text(
            text = "WEBSITE DIRECTORY",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.2.sp,
            color = AuraCrimson
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                FooterLink(text = "Home Overview", onClick = { onNavigate(AppNavDestination.HOME) })
                FooterLink(text = "Dynamic Tasting Menu", onClick = { onNavigate(AppNavDestination.MENU) })
                FooterLink(text = "Royal Indian Specialties", onClick = { onNavigate(AppNavDestination.MENU) })
            }
            Column(modifier = Modifier.weight(1f)) {
                FooterLink(text = "Table Reservations", onClick = { onNavigate(AppNavDestination.RESERVATIONS) })
                FooterLink(text = "Online Takeaway & Delivery", onClick = { onNavigate(AppNavDestination.ORDER) })
                FooterLink(text = "Global Cities & Branches", onClick = onOpenLocations)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Global Flagship Directory Chips
        Text(
            text = "GLOBAL METROPOLITAN RESIDENCIES",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.2.sp,
            color = AuraCrimson
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "• New York (Manhattan Flagship)  • Mumbai (Bandra Kurla Complex)\n• London (Mayfair)  • Paris (Triangle d'Or)  • Dubai (Downtown)\n• New Delhi (Chanakyapuri)  • Bengaluru (UB City)  • Tokyo (Ginza)\n• Singapore (Marina Bay)  • Toronto (Yorkville)  • Sydney (Circular Quay)",
            fontSize = 11.sp,
            color = AuraTextSecondary,
            lineHeight = 18.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        // VIP Newsletter
        Text(
            text = "VIP DINING NEWSLETTER",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.2.sp,
            color = AuraCrimson
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Receive invitations to seasonal tasting menus, sommelier cellar releases, and chef collaborations.",
            fontSize = 11.sp,
            color = AuraTextSecondary
        )
        Spacer(modifier = Modifier.height(8.dp))

        if (subscribed) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(AuraCrimsonSoft)
                    .padding(10.dp)
            ) {
                Text(
                    text = "Thank you for subscribing to the Aura Private Dining Journal.",
                    fontSize = 12.sp,
                    color = AuraCrimsonDark,
                    fontWeight = FontWeight.Bold
                )
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = newsletterEmail,
                    onValueChange = { newsletterEmail = it },
                    placeholder = { Text("Enter your email address...", fontSize = 12.sp) },
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = AuraCrimson),
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        if (newsletterEmail.contains("@")) subscribed = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AuraCrimson,
                        contentColor = AuraWhite
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.height(48.dp)
                ) {
                    Text("Join", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        HorizontalDivider(color = AuraSurfaceBorder)
        Spacer(modifier = Modifier.height(14.dp))

        // Allergen & Dietary Commitment Notice
        Text(
            text = "Allergen Transparency Guarantee: Our kitchens adhere to stringent sterilization protocols to minimize allergen cross-contact. Detailed allergen charts and full ingredient transparency are available for every creation.",
            fontSize = 10.sp,
            color = AuraTextMuted,
            lineHeight = 15.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "© 2026 Aura Dining International. All rights reserved.",
                fontSize = 10.sp,
                color = AuraTextMuted
            )
            Text(
                text = "Privacy • Terms • Allergen Safety",
                fontSize = 10.sp,
                color = AuraCrimson
            )
        }
    }
}

@Composable
private fun FooterLink(text: String, onClick: () -> Unit) {
    Text(
        text = "› $text",
        fontSize = 12.sp,
        color = AuraTextSecondary,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp)
    )
}
