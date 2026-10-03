package com.example.data.model

data class RestaurantBranch(
    val id: String,
    val cityName: String,
    val country: String,
    val stateOrRegion: String,
    val branchName: String,
    val address: String,
    val phone: String,
    val hours: String,
    val currencySymbol: String = "$"
)

object GlobalLocations {

    // Aura's Global Flagship Restaurant Branches
    val flagshipBranches: List<RestaurantBranch> = listOf(
        RestaurantBranch(
            id = "nyc",
            cityName = "New York",
            country = "United States",
            stateOrRegion = "New York",
            branchName = "Aura Manhattan Flagship",
            address = "450 Lexington Avenue, Midtown Manhattan, NY 10017",
            phone = "+1 (212) 555-0188",
            hours = "Dinner: 5:30 PM – 11:00 PM • Lunch: 12:00 PM – 2:30 PM",
            currencySymbol = "$"
        ),
        RestaurantBranch(
            id = "mumbai",
            cityName = "Mumbai",
            country = "India",
            stateOrRegion = "Maharashtra",
            branchName = "Aura Mumbai BKC",
            address = "G Block, Bandra Kurla Complex, Bandra East, Mumbai 400051",
            phone = "+91 22 6123 4567",
            hours = "Lunch: 12:30 PM – 3:30 PM • Dinner: 7:00 PM – 12:00 AM",
            currencySymbol = "₹"
        ),
        RestaurantBranch(
            id = "delhi",
            cityName = "New Delhi",
            country = "India",
            stateOrRegion = "Delhi NCT",
            branchName = "Aura Chanakyapuri",
            address = "Shantipath Diplomatic Enclave, Chanakyapuri, New Delhi 110021",
            phone = "+91 11 4987 6543",
            hours = "Lunch: 12:30 PM – 3:30 PM • Dinner: 7:00 PM – 11:30 PM",
            currencySymbol = "₹"
        ),
        RestaurantBranch(
            id = "bengaluru",
            cityName = "Bengaluru",
            country = "India",
            stateOrRegion = "Karnataka",
            branchName = "Aura UB City",
            address = "Level 2, The Collection, UB City, Vittal Mallya Rd, Bengaluru 560001",
            phone = "+91 80 4111 2233",
            hours = "Lunch: 12:00 PM – 3:30 PM • Dinner: 7:00 PM – 11:30 PM",
            currencySymbol = "₹"
        ),
        RestaurantBranch(
            id = "london",
            cityName = "London",
            country = "United Kingdom",
            stateOrRegion = "Greater London",
            branchName = "Aura Mayfair",
            address = "18 Berkeley Square, Mayfair, London W1J 6BQ",
            phone = "+44 20 7946 0912",
            hours = "Lunch: 12:00 PM – 2:30 PM • Dinner: 6:00 PM – 11:00 PM",
            currencySymbol = "£"
        ),
        RestaurantBranch(
            id = "paris",
            cityName = "Paris",
            country = "France",
            stateOrRegion = "Île-de-France",
            branchName = "Aura Triangle d'Or",
            address = "32 Avenue Montaigne, 8th Arrondissement, 75008 Paris",
            phone = "+33 1 42 68 55 00",
            hours = "Lunch: 12:30 PM – 2:30 PM • Dinner: 7:30 PM – 11:00 PM",
            currencySymbol = "€"
        ),
        RestaurantBranch(
            id = "dubai",
            cityName = "Dubai",
            country = "United Arab Emirates",
            stateOrRegion = "Dubai",
            branchName = "Aura Downtown Dubai",
            address = "Sheikh Mohammed bin Rashid Blvd, Downtown Dubai",
            phone = "+971 4 362 7500",
            hours = "Lunch: 1:00 PM – 4:00 PM • Dinner: 7:00 PM – 1:00 AM",
            currencySymbol = "AED "
        ),
        RestaurantBranch(
            id = "tokyo",
            cityName = "Tokyo",
            country = "Japan",
            stateOrRegion = "Tokyo",
            branchName = "Aura Ginza",
            address = "6-10-1 Ginza, Chuo-ku, Tokyo 104-0061",
            phone = "+81 3 5537 2000",
            hours = "Lunch: 11:30 AM – 2:30 PM • Dinner: 5:30 PM – 10:30 PM",
            currencySymbol = "¥"
        ),
        RestaurantBranch(
            id = "singapore",
            cityName = "Singapore",
            country = "Singapore",
            stateOrRegion = "Central Singapore",
            branchName = "Aura Marina Bay",
            address = "2 Bayfront Avenue, Marina Bay Sands, Singapore 018972",
            phone = "+65 6688 8868",
            hours = "Lunch: 12:00 PM – 2:30 PM • Dinner: 6:30 PM – 11:00 PM",
            currencySymbol = "S$"
        ),
        RestaurantBranch(
            id = "toronto",
            cityName = "Toronto",
            country = "Canada",
            stateOrRegion = "Ontario",
            branchName = "Aura Yorkville",
            address = "130 Bloor Street West, Yorkville, Toronto, ON M5S 1N5",
            phone = "+1 (416) 922-8811",
            hours = "Dinner: 5:00 PM – 11:00 PM • Lunch: 12:00 PM – 2:30 PM",
            currencySymbol = "C$"
        ),
        RestaurantBranch(
            id = "sydney",
            cityName = "Sydney",
            country = "Australia",
            stateOrRegion = "New South Wales",
            branchName = "Aura Circular Quay",
            address = "7 Macquarie Street, Circular Quay, Sydney NSW 2000",
            phone = "+61 2 9247 1800",
            hours = "Lunch: 12:00 PM – 3:00 PM • Dinner: 6:00 PM – 10:30 PM",
            currencySymbol = "A$"
        )
    )

    // Complete Global Countries List
    val allCountries: List<String> = listOf(
        "United States", "India", "United Kingdom", "Canada", "Australia",
        "United Arab Emirates", "France", "Germany", "Japan", "Singapore",
        "Italy", "Spain", "Switzerland", "Saudi Arabia", "Qatar",
        "Netherlands", "Sweden", "Norway", "Denmark", "Belgium",
        "Austria", "Ireland", "New Zealand", "South Africa", "Brazil",
        "Mexico", "Argentina", "Chile", "Colombia", "South Korea",
        "China", "Hong Kong", "Malaysia", "Thailand", "Indonesia",
        "Philippines", "Vietnam", "Turkey", "Egypt", "Greece",
        "Portugal", "Poland", "Czech Republic", "Hungary", "Finland",
        "Israel", "Kuwait", "Bahrain", "Oman", "Morocco",
        "Kenya", "Nigeria", "Ghana", "Peru", "Costa Rica",
        "Iceland", "Luxembourg", "Monaco", "Croatia", "Cyprus"
    ).sorted()

    // All 50 US States
    val usStates: List<String> = listOf(
        "Alabama", "Alaska", "Arizona", "Arkansas", "California",
        "Colorado", "Connecticut", "Delaware", "Florida", "Georgia",
        "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa",
        "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland",
        "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri",
        "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey",
        "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio",
        "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina",
        "South Dakota", "Tennessee", "Texas", "Utah", "Vermont",
        "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming",
        "District of Columbia"
    ).sorted()

    // All 28 Indian States & 8 Union Territories
    val indianStates: List<String> = listOf(
        "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh",
        "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jharkhand",
        "Karnataka", "Kerala", "Madhya Pradesh", "Maharashtra", "Manipur",
        "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Punjab",
        "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana", "Tripura",
        "Uttar Pradesh", "Uttarakhand", "West Bengal",
        // Union Territories
        "Delhi NCT", "Jammu and Kashmir", "Ladakh", "Chandigarh",
        "Puducherry", "Andaman and Nicobar Islands",
        "Dadra and Nagar Haveli and Daman and Diu", "Lakshadweep"
    ).sorted()

    // Canadian Provinces & Territories
    val canadianProvinces: List<String> = listOf(
        "Alberta", "British Columbia", "Manitoba", "New Brunswick",
        "Newfoundland and Labrador", "Nova Scotia", "Ontario", "Prince Edward Island",
        "Quebec", "Saskatchewan", "Northwest Territories", "Nunavut", "Yukon"
    ).sorted()

    // Australian States & Territories
    val australianStates: List<String> = listOf(
        "New South Wales", "Victoria", "Queensland", "Western Australia",
        "South Australia", "Tasmania", "Australian Capital Territory", "Northern Territory"
    ).sorted()

    // UK Regions & Home Nations
    val ukRegions: List<String> = listOf(
        "Greater London", "South East England", "North West England",
        "West Midlands", "South West England", "Yorkshire and the Humber",
        "East of England", "East Midlands", "North East England",
        "Scotland", "Wales", "Northern Ireland"
    ).sorted()

    // UAE Emirates
    val uaeEmirates: List<String> = listOf(
        "Dubai", "Abu Dhabi", "Sharjah", "Ajman",
        "Ras Al Khaimah", "Fujairah", "Umm Al Quwain"
    )

    // Helper: Get states for any country
    fun getStatesForCountry(country: String): List<String> {
        return when (country) {
            "United States" -> usStates
            "India" -> indianStates
            "Canada" -> canadianProvinces
            "Australia" -> australianStates
            "United Kingdom" -> ukRegions
            "United Arab Emirates" -> uaeEmirates
            "France" -> listOf("Île-de-France (Paris)", "Provence-Alpes-Côte d'Azur", "Auvergne-Rhône-Alpes", "Nouvelle-Aquitaine", "Occitanie", "Hauts-de-France", "Grand Est", "Normandy", "Brittany")
            "Germany" -> listOf("Bavaria (Munich)", "Berlin", "North Rhine-Westphalia", "Baden-Württemberg", "Hesse (Frankfurt)", "Hamburg", "Saxony")
            "Japan" -> listOf("Tokyo Prefecture", "Osaka Prefecture", "Kyoto Prefecture", "Kanagawa Prefecture", "Aichi Prefecture", "Hokkaido", "Fukuoka Prefecture")
            "Switzerland" -> listOf("Zurich", "Geneva", "Vaud (Lausanne)", "Bern", "Basel-City", "Lucerne", "Ticino")
            "Italy" -> listOf("Lombardy (Milan)", "Lazio (Rome)", "Veneto (Venice)", "Tuscany (Florence)", "Piedmont (Turin)", "Campania (Naples)")
            else -> listOf("Capital Metropole", "Central District", "Northern Province", "Southern Province", "Eastern Region", "Western Region")
        }
    }

    // Extensive Global Cities Directory
    val majorGlobalCities: List<String> = listOf(
        // North America
        "New York", "Los Angeles", "Chicago", "Houston", "San Francisco", "Miami",
        "Seattle", "Boston", "Washington D.C.", "Las Vegas", "Atlanta", "Dallas",
        "Toronto", "Vancouver", "Montreal", "Calgary", "Ottawa", "Mexico City", "Cancun",
        // India
        "Mumbai", "New Delhi", "Bengaluru", "Hyderabad", "Chennai", "Kolkata",
        "Pune", "Ahmedabad", "Jaipur", "Lucknow", "Chandigarh", "Kochi",
        "Goa", "Indore", "Surat", "Bhopal", "Varanasi", "Amritsar", "Udaipur", "Agra",
        // Europe
        "London", "Manchester", "Edinburgh", "Birmingham", "Paris", "Lyon",
        "Marseille", "Nice", "Berlin", "Munich", "Frankfurt", "Hamburg",
        "Rome", "Milan", "Venice", "Florence", "Madrid", "Barcelona",
        "Amsterdam", "Rotterdam", "Brussels", "Zurich", "Geneva", "Vienna",
        "Dublin", "Stockholm", "Oslo", "Copenhagen", "Helsinki", "Prague",
        "Warsaw", "Budapest", "Athens", "Lisbon", "Monaco",
        // Middle East
        "Dubai", "Abu Dhabi", "Doha", "Riyadh", "Jeddah", "Muscat", "Manama", "Kuwait City", "Tel Aviv", "Cairo",
        // Asia-Pacific
        "Tokyo", "Osaka", "Kyoto", "Singapore", "Hong Kong", "Seoul",
        "Sydney", "Melbourne", "Brisbane", "Perth", "Auckland", "Bangkok",
        "Kuala Lumpur", "Jakarta", "Manila", "Hanoi", "Ho Chi Minh City", "Taipei",
        // Latin America & Africa
        "São Paulo", "Rio de Janeiro", "Buenos Aires", "Santiago", "Bogotá", "Lima",
        "Johannesburg", "Cape Town", "Nairobi", "Lagos", "Casablanca"
    ).sorted()
}
