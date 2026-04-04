# Assignment: CoolWeatherApp
Course : [Course Name]
Student (s): [Student Name]
Date : 2026-04-04
Repository URL : https://github.com/tomassantos2025/coolweatherapp
---
## 1. Introduction
The purpose of the CoolWeatherApp is to provide real-time weather information based on the user's geographical location. The primary objectives are to successfully interface with a public weather API (Open-Meteo), retrieve relevant weather metrics (temperature, wind speed, pressure, etc.), and present them in a user-friendly, responsive Android interface.

## 2. System Overview
CoolWeatherApp is an Android application that allows users to fetch current weather conditions either by automatically acquiring their device's GPS coordinates or through manual coordinate entry. 
**Main features:**
- Automatic location detection via Google Play Services (Fused Location).
- Real-time weather data fetching using the Open-Meteo API.
- Dynamic theme switching based on device orientation (Portrait/Landscape) and time of day (Day/Night).
- Mapping of World Meteorological Organization (WMO) codes to visually descriptive weather icons.

## 3. Architecture and Design
The project follows a straightforward implementation using native Android Views and Kotlin. 
- **Architecture**: The logic is primarily concentrated in a single Activity (`MainActivity`), which handles both the UI coordination and data fetching, leaning towards a simple MVC (Model-View-Controller) pattern where the Activity acts as the Controller.
- **Data Models**: Simple data classes (`WeatherData.kt`) are used to map the JSON response from the API, paired with Google's `Gson` library for deserialization.
- **Asynchronous Execution**: Network calls are offloaded from the Main UI thread using native Java `Thread`, and updates are posted back using `runOnUiThread`.
- **Location Services**: The `FusedLocationProviderClient` is utilized for battery-efficient and accurate location retrieval.

## 4. Implementation
**Main Components:**
- `MainActivity.kt`: The core controller. Manages UI interactions, requests location permissions at runtime, fetches device location, and makes HTTP requests to the Open-Meteo API.
- `WeatherData.kt`: Contains the Kotlin Data Classes (`WeatherData`, `CurrentWeather`, `Hourly`) matching the Open-Meteo JSON schema.
- `WMO_WeatherCode`: An Enumeration that connects Open-Meteo standard weather codes with local drawable asset names (e.g., `clear_day`, `cloudy_night`).

**Key Code Excerpt:**
The application fetches data via `Url(urlString).openStream()` within a background thread and parses the InputStream using `Gson`, demonstrating core Java networking paired with automatic JSON mapping.

## 5. Testing and Validation
- **Exception Handling**: The app wraps the network request in a `try-catch` block, showing a Toast message UI fallback if the connection fails or if invalid coordinates are manually inputted.
- **Permissions Validation**: Runtime checks are implemented for `ACCESS_FINE_LOCATION` and `ACCESS_COARSE_LOCATION`. If not granted, the app prompts the user for permissions.
- **Edge Cases Tested**: Missing or disabled location services, invalid input in manual coordinate fields, and orientation changes triggering appropriate theme styles (`Theme_Day`, `Theme_Night`).

## 6. Usage Instructions
**Requirements:** 
- Android Emulator or physical device running Android (API 24+ recommended).
- Internet access to fetch weather data.
- Location Services enabled on the device.

**Execution Steps:**
1. Clone the repository and open the project in Android Studio.
2. Build the project to resolve dependencies (Gson, Google Play Services Location).
3. Run the app (`Shift + F10`) on the target device.
4. Accept the location permissions prompt upon startup. 
5. The app will automatically display weather for your current location. To test other locations, input new Latitude/Longitude values and click "Update".

---
# Autonomous Software Engineering Sections - only for [AC OK , AI OK]
## 7. Prompting Strategy
As AI was only utilized to generate this README.md document, the prompting strategy was straightforward. I provided the AI with the project's completed codebase and structural requirements, asking it to format and generate the documentation accordingly.

## 8. Autonomous Agent Workflow
There was no AI workflow during the actual software development lifecycle. The AI was solely used at the very end of the project to draft the documentation based on the required template.

## 9. Verification of AI - Generated Artifacts
Verification involved carefully reading through the generated README.md to ensure all descriptions of the architecture, features, and implementation details correctly matched the actual Android application I built.

## 10. Human vs AI Contribution
- **AI Contribution**: Exclusively used to generate the formatting and text of this README.md file based on the provided template.
- **Human Contribution**: 100% of the project's conceptualization, architecture design, UI development, Kotlin logic implementation, and testing were performed entirely by me without AI assistance.

## 11. Ethical and Responsible Use
The AI was used responsibly as a documentation assistant to save time on formatting and drafting text. It did not contribute to any functional system logic or original code, ensuring that the learning outcomes and technical integrity of the project remained strictly human-driven.

---
# Development Process
## 12. Version Control and Commit History
During this assignment, version control was not used iteratively. As I am still learning how to balance building the project and documenting the progress simultaneously, I adopted an approach where I mostly built the project in large chunks and made singular big commits. In the future, I aim to improve my use of Git by breaking down the development process into smaller, atomic commits to better track the evolution of the codebase.

## 13. Difficulties and Lessons Learned
- **Challenge**: Passing data from a background thread to the UI thread.
- **Learning**: Gained an understanding of Android's single-thread UI model and how to utilize `runOnUiThread`.
- **Challenge**: Handling complex JSON responses with nested arrays.
- **Learning**: Learned to effectively leverage `Gson` and mapping Kotlin Data Classes to corresponding JSON structures to parse weather metrics cleanly.

## 14. Future Improvements
- Implelementing an MVVM (Model-View-ViewModel) architecture with Kotlin Coroutines and Retrofit for more robust and modern asynchronous data handling.
- Adding a "City Search" feature using a Geocoding API to easily find locations without typing raw coordinates.
- Caching weather data locally using Room to display the last known weather when offline.

---
## 15. AI Usage Disclosure ( Mandatory )
- **Tools Used**: Google Gemini Pro (Antigravity).
- **Purpose**: Used exclusively to generate the text and structure of this README.md file based on the completed code.
- **Confirmation**: I confirm that the AI tool was used responsibly as a documentation assistant and that I remain fully responsible for the design, implementation, correctness, and all content submitted within this repository.
