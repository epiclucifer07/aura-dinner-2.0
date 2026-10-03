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
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EventSeat
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.ReservationEntity
import com.example.data.model.GlobalLocations
import com.example.data.model.RestaurantBranch
import com.example.ui.theme.AllergenWarning
import com.example.ui.theme.AuraCrimson
import com.example.ui.theme.AuraCrimsonDark
import com.example.ui.theme.AuraCrimsonSoft
import com.example.ui.theme.AuraSurfaceBorder
import com.example.ui.theme.AuraTextMuted
import com.example.ui.theme.AuraTextSecondary
import com.example.ui.theme.AuraWhite
import com.example.ui.theme.DietaryGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationScreen(
    currentBranch: RestaurantBranch = GlobalLocations.flagshipBranches[0],
    onSelectBranch: (RestaurantBranch) -> Unit = {},
    onMakeReservation: (
        name: String,
        phone: String,
        email: String,
        partySize: Int,
        date: String,
        timeSlot: String,
        seatingArea: String,
        specialRequests: String
    ) -> Unit,
    confirmationDialog: ReservationEntity?,
    onDismissDialog: () -> Unit,
    reservationsList: List<ReservationEntity>,
    onCancelReservation: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    var activeTab by remember { mutableIntStateOf(0) } // 0: Book Table, 1: My Bookings

    // Reservation Form State
    var partySize by remember { mutableIntStateOf(2) }
    var selectedDate by remember { mutableStateOf("Tonight, Oct 3") }
    var selectedTimeSlot by remember { mutableStateOf("7:30 PM") }
    var selectedSeatingArea by remember { mutableStateOf("Main Dining Room") }
    var guestName by remember { mutableStateOf("") }
    var guestPhone by remember { mutableStateOf("") }
    var guestEmail by remember { mutableStateOf("") }
    var guestCountry by remember { mutableStateOf(currentBranch.country) }
    var guestState by remember { mutableStateOf(currentBranch.stateOrRegion) }
    var specialRequests by remember { mutableStateOf("") }

    var countryDropdownExpanded by remember { mutableStateOf(false) }
    var stateDropdownExpanded by remember { mutableStateOf(false) }

    val dateOptions = listOf(
        "Tonight, Oct 3",
        "Tomorrow, Oct 4",
        "Sun, Oct 5",
        "Tue, Oct 7",
        "Wed, Oct 8",
        "Thu, Oct 9"
    )

    val dinnerSlots = listOf(
        "5:30 PM", "6:00 PM", "6:30 PM", "7:00 PM",
        "7:30 PM", "8:00 PM", "8:30 PM", "9:00 PM"
    )

    val lunchSlots = listOf(
        "12:00 PM", "12:30 PM", "1:00 PM", "1:30 PM"
    )

    val seatingAreas = listOf(
        "Main Dining Room",
        "Veranda & Patio",
        "Chef's Counter",
        "Private Dining Salon"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Tab Row: Book / My Bookings
        TabRow(
            selectedTabIndex = activeTab,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = AuraCrimson,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[activeTab]),
                    color = AuraCrimson
                )
            }
        ) {
            Tab(
                selected = activeTab == 0,
                onClick = { activeTab = 0 },
                text = {
                    Text(
                        text = "Reserve a Table",
                        fontWeight = if (activeTab == 0) FontWeight.Bold else FontWeight.Medium
                    )
                }
            )
            Tab(
                selected = activeTab == 1,
                onClick = { activeTab = 1 },
                text = {
                    Text(
                        text = "My Bookings (${reservationsList.size})",
                        fontWeight = if (activeTab == 1) FontWeight.Bold else FontWeight.Medium
                    )
                }
            )
        }

        if (activeTab == 0) {
            // Reservation Booking Form
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 100.dp)
            ) {
                // Header
                item {
                    Text(
                        text = "Table Reservation",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Select your global branch city, dining space, and time",
                        style = MaterialTheme.typography.bodySmall,
                        color = AuraTextSecondary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // 0. Global Branch & City Selector
                item {
                    SectionHeader(icon = Icons.Default.LocationCity, title = "Aura Global City & Branch")
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(vertical = 2.dp)
                    ) {
                        items(GlobalLocations.flagshipBranches) { branch ->
                            val isSelected = branch.id == currentBranch.id
                            Box(
                                modifier = Modifier
                                    .testTag("res_branch_${branch.id}")
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(if (isSelected) AuraCrimson else MaterialTheme.colorScheme.surface)
                                    .border(
                                        1.dp,
                                        if (isSelected) AuraCrimson else AuraSurfaceBorder,
                                        RoundedCornerShape(10.dp)
                                    )
                                    .clickable { onSelectBranch(branch) }
                                    .padding(horizontal = 14.dp, vertical = 8.dp)
                            ) {
                                Column {
                                    Text(
                                        text = branch.cityName,
                                        fontSize = 13.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
                                        color = if (isSelected) AuraWhite else MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = branch.country,
                                        fontSize = 10.sp,
                                        color = if (isSelected) AuraWhite.copy(alpha = 0.85f) else AuraTextMuted
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Selected: ${currentBranch.branchName} (${currentBranch.address})",
                        fontSize = 11.sp,
                        color = AuraCrimson,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }

                // 1. Party Size
                item {
                    SectionHeader(icon = Icons.Default.People, title = "Number of Guests")
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(vertical = 2.dp)
                    ) {
                        items(listOf(1, 2, 3, 4, 5, 6, 8, 10)) { size ->
                            val isSelected = partySize == size
                            Box(
                                modifier = Modifier
                                    .testTag("party_size_chip_$size")
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(if (isSelected) AuraCrimson else MaterialTheme.colorScheme.surface)
                                    .border(
                                        1.dp,
                                        if (isSelected) AuraCrimson else AuraSurfaceBorder,
                                        RoundedCornerShape(10.dp)
                                    )
                                    .clickable { partySize = size }
                                    .padding(horizontal = 16.dp, vertical = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = if (size == 1) "1 Guest" else "$size Guests",
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) AuraWhite else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // 2. Date Selection
                item {
                    SectionHeader(icon = Icons.Default.CalendarMonth, title = "Select Date")
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(vertical = 2.dp)
                    ) {
                        items(dateOptions) { date ->
                            val isSelected = selectedDate == date
                            Box(
                                modifier = Modifier
                                    .testTag("date_chip_${date.take(4).lowercase()}")
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(if (isSelected) AuraCrimson else MaterialTheme.colorScheme.surface)
                                    .border(
                                        1.dp,
                                        if (isSelected) AuraCrimson else AuraSurfaceBorder,
                                        RoundedCornerShape(10.dp)
                                    )
                                    .clickable { selectedDate = date }
                                    .padding(horizontal = 14.dp, vertical = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = date,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) AuraWhite else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // 3. Time Slot Selection
                item {
                    SectionHeader(icon = Icons.Default.Schedule, title = "Dinner Seating")
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(vertical = 2.dp)
                    ) {
                        items(dinnerSlots) { slot ->
                            val isSelected = selectedTimeSlot == slot
                            Box(
                                modifier = Modifier
                                    .testTag("time_slot_${slot.replace(":", "").replace(" ", "").lowercase()}")
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSelected) AuraCrimson else MaterialTheme.colorScheme.surface)
                                    .border(
                                        1.dp,
                                        if (isSelected) AuraCrimson else AuraSurfaceBorder,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .clickable { selectedTimeSlot = slot }
                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = slot,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) AuraWhite else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Lunch Seating (Fri – Sun)",
                        fontSize = 12.sp,
                        color = AuraTextMuted,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(vertical = 2.dp)
                    ) {
                        items(lunchSlots) { slot ->
                            val isSelected = selectedTimeSlot == slot
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSelected) AuraCrimson else MaterialTheme.colorScheme.surface)
                                    .border(
                                        1.dp,
                                        if (isSelected) AuraCrimson else AuraSurfaceBorder,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .clickable { selectedTimeSlot = slot }
                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = slot,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) AuraWhite else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }

                // 4. Seating Area
                item {
                    SectionHeader(icon = Icons.Default.EventSeat, title = "Seating Ambience")
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        seatingAreas.forEach { area ->
                            val isSelected = selectedSeatingArea == area
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSelected) AuraCrimsonSoft else MaterialTheme.colorScheme.surface)
                                    .border(
                                        1.dp,
                                        if (isSelected) AuraCrimson else AuraSurfaceBorder,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .clickable { selectedSeatingArea = area }
                                    .padding(vertical = 8.dp, horizontal = 4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = area,
                                    fontSize = 10.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) AuraCrimson else MaterialTheme.colorScheme.onSurface,
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                    maxLines = 2
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // 5. Contact Details, Country & State selection, Special Requests
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        shape = RoundedCornerShape(14.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, AuraSurfaceBorder)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Guest Profile & Global Origin",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(10.dp))

                            OutlinedTextField(
                                value = guestName,
                                onValueChange = { guestName = it },
                                label = { Text("Primary Guest Name *") },
                                placeholder = { Text("e.g. Eleanor Vance") },
                                singleLine = true,
                                modifier = Modifier
                                    .testTag("res_guest_name_input")
                                    .fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = AuraCrimson)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = guestPhone,
                                onValueChange = { guestPhone = it },
                                label = { Text("Contact Phone *") },
                                placeholder = { Text("+1 (212) 555-0144 / +91 98765 43210") },
                                singleLine = true,
                                modifier = Modifier
                                    .testTag("res_guest_phone_input")
                                    .fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = AuraCrimson)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = guestEmail,
                                onValueChange = { guestEmail = it },
                                label = { Text("Email for Confirmation") },
                                placeholder = { Text("guest@example.com") },
                                singleLine = true,
                                modifier = Modifier
                                    .testTag("res_guest_email_input")
                                    .fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = AuraCrimson)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            // Country Dropdown
                            ExposedDropdownMenuBox(
                                expanded = countryDropdownExpanded,
                                onExpandedChange = { countryDropdownExpanded = !countryDropdownExpanded }
                            ) {
                                OutlinedTextField(
                                    value = guestCountry,
                                    onValueChange = {},
                                    readOnly = true,
                                    label = { Text("Country of Residence") },
                                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = countryDropdownExpanded) },
                                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = AuraCrimson),
                                    modifier = Modifier
                                        .menuAnchor()
                                        .fillMaxWidth()
                                )
                                ExposedDropdownMenu(
                                    expanded = countryDropdownExpanded,
                                    onDismissRequest = { countryDropdownExpanded = false }
                                ) {
                                    GlobalLocations.allCountries.forEach { country ->
                                        DropdownMenuItem(
                                            text = { Text(country) },
                                            onClick = {
                                                guestCountry = country
                                                guestState = GlobalLocations.getStatesForCountry(country).firstOrNull() ?: ""
                                                countryDropdownExpanded = false
                                            }
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // State / Province Dropdown
                            val statesList = GlobalLocations.getStatesForCountry(guestCountry)
                            ExposedDropdownMenuBox(
                                expanded = stateDropdownExpanded,
                                onExpandedChange = { stateDropdownExpanded = !stateDropdownExpanded }
                            ) {
                                OutlinedTextField(
                                    value = guestState,
                                    onValueChange = {},
                                    readOnly = true,
                                    label = { Text("State / Province / Region") },
                                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = stateDropdownExpanded) },
                                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = AuraCrimson),
                                    modifier = Modifier
                                        .menuAnchor()
                                        .fillMaxWidth()
                                )
                                ExposedDropdownMenu(
                                    expanded = stateDropdownExpanded,
                                    onDismissRequest = { stateDropdownExpanded = false }
                                ) {
                                    statesList.forEach { state ->
                                        DropdownMenuItem(
                                            text = { Text(state) },
                                            onClick = {
                                                guestState = state
                                                stateDropdownExpanded = false
                                            }
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = specialRequests,
                                onValueChange = { specialRequests = it },
                                label = { Text("Dietary restrictions, allergies, or special occasion") },
                                placeholder = { Text("e.g. Jain preparation, severe peanut allergy, birthday table") },
                                singleLine = false,
                                maxLines = 3,
                                modifier = Modifier
                                    .testTag("res_special_requests_input")
                                    .fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = AuraCrimson)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Reserve Button
                    Button(
                        onClick = {
                            val notesWithOrigin = if (specialRequests.isNotBlank()) {
                                "$specialRequests • Origin: $guestState, $guestCountry"
                            } else {
                                "Origin: $guestState, $guestCountry"
                            }
                            onMakeReservation(
                                guestName,
                                guestPhone,
                                guestEmail,
                                partySize,
                                selectedDate,
                                selectedTimeSlot,
                                selectedSeatingArea,
                                notesWithOrigin
                            )
                        },
                        modifier = Modifier
                            .testTag("confirm_reservation_button")
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AuraCrimson,
                            contentColor = AuraWhite
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Confirm Reservation (${currentBranch.cityName} • $partySize Guests)",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Cancellation policy: No cancellation fee up to 2 hours prior to reservation.",
                        fontSize = 11.sp,
                        color = AuraTextMuted,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        } else {
            // My Bookings
            if (reservationsList.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "No Reservations Yet",
                        style = MaterialTheme.typography.titleLarge.copy(fontFamily = FontFamily.Serif),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Your confirmed table bookings across global Aura branches will appear here.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = AuraTextSecondary
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(reservationsList) { res ->
                        ReservationItemCard(
                            reservation = res,
                            onCancel = { onCancelReservation(res.id) }
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }

    // Reservation Confirmation Dialog
    if (confirmationDialog != null) {
        AlertDialog(
            onDismissRequest = onDismissDialog,
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = DietaryGreen,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Table Confirmed!",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Confirmation Reference: ${confirmationDialog.confirmationCode}",
                        fontWeight = FontWeight.Bold,
                        color = AuraCrimson,
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "We look forward to welcoming you, ${confirmationDialog.guestName}.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .padding(12.dp)
                    ) {
                        Column {
                            Text(
                                text = "📅 ${confirmationDialog.dateText} at ${confirmationDialog.timeSlot}",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "👥 Party: ${confirmationDialog.partySize} Guests",
                                fontSize = 12.sp,
                                color = AuraTextSecondary
                            )
                            Text(
                                text = "📍 Seating: ${confirmationDialog.seatingArea}",
                                fontSize = 12.sp,
                                color = AuraTextSecondary
                            )
                            if (confirmationDialog.specialRequests.isNotBlank()) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "📝 Details: ${confirmationDialog.specialRequests}",
                                    fontSize = 11.sp,
                                    color = AuraCrimsonDark
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = onDismissDialog,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AuraCrimson,
                        contentColor = AuraWhite
                    )
                ) {
                    Text("Done")
                }
            },
            containerColor = MaterialTheme.colorScheme.surface
        )
    }
}

@Composable
private fun SectionHeader(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = AuraCrimson,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun ReservationItemCard(
    reservation: ReservationEntity,
    onCancel: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, AuraSurfaceBorder)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = reservation.confirmationCode,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = AuraCrimson
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (reservation.status == "CONFIRMED") AuraCrimsonSoft else MaterialTheme.colorScheme.surfaceVariant)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = reservation.status,
                        color = if (reservation.status == "CONFIRMED") AuraCrimsonDark else AuraTextMuted,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${reservation.dateText} • ${reservation.timeSlot}",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "${reservation.partySize} Guests • ${reservation.seatingArea}",
                fontSize = 12.sp,
                color = AuraTextSecondary
            )

            Text(
                text = "Guest: ${reservation.guestName} (${reservation.guestPhone})",
                fontSize = 11.sp,
                color = AuraTextMuted
            )

            if (reservation.specialRequests.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Request: ${reservation.specialRequests}",
                    fontSize = 11.sp,
                    color = AuraCrimson
                )
            }

            if (reservation.status == "CONFIRMED") {
                Spacer(modifier = Modifier.height(10.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    OutlinedButton(
                        onClick = onCancel,
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = AllergenWarning),
                        border = androidx.compose.foundation.BorderStroke(0.8.dp, AllergenWarning),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.height(32.dp)
                    ) {
                        Text("Cancel Booking", fontSize = 11.sp)
                    }
                }
            }
        }
    }
}
