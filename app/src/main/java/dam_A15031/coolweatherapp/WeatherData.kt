package dam_A15031.coolweatherapp

data class WeatherData (
    var latitude: Double,
    var longitude: Double,
    var timezone: String,
    var current_weather: CurrentWeather,
    var hourly: Hourly
)

data class CurrentWeather (
    var temperature: Float,
    var windspeed: Float,
    var winddirection: Int,
    var weathercode: Int,
    var time: String
)

data class Hourly (
    var time: ArrayList<String>,
    var winddirection_10m: ArrayList<Int>,
    var windspeed_10m: ArrayList<Float>,
    var temperature_2m: ArrayList<Float>,
    var weathercode: ArrayList<Int>,
    var pressure_msl: ArrayList<Double>
)

enum class WMO_WeatherCode(var code: Int, var image: String) {
    CLEAR_SKY(0,"clear_"),
    MAINLY_CLEAR(1, "mostly_clear_"),
    PARTLY_CLOUDY(2,"partly_cloudy_"),
    OVERCAST(3,"cloudy_"),
    FOG(45,"fog_"),
    DEPOSITING_RIME_FOG(48,"fog_"),
    DRIZZLE_LIGHT(51, "drizzle_"),
    DRIZZLE_MODERATE(53, "drizzle_"),
    DRIZZLE_DENSE(55,"drizzle_"),
    FREEZING_DRIZZLE_LIGHT(56,"freezing_drizzle_"),
    FREEZING_DRIZZLE_DENSE(57,"freezing_drizzle_"),
    RAIN_SLIGHT(61,"rain_light_"),
    RAIN_MODERATE(63,"rain_"),
    RAIN_HEAVY(65,"rain_heavy_"),
    FREEZING_RAIN_LIGHT(66,"freezing_rain_light_"),
    FREEZING_RAIN_HEAVY(67,"freezing_rain_heavy_"),
    SNOW_FALL_SLIGHT(71,"snow_light_"),
    SNOW_FALL_MODERATE(73,"snow_"),
    SNOW_FALL_HEAVY(75,"snow_heavy_"),
    SNOW_GRAINS(77, "snow_"),
    RAIN_SHOWERS_SLIGHT(80, "rain_light_"),
    RAIN_SHOWERS_MODERATE(81, "rain_"),
    RAIN_SHOWERS_VIOLENT(82, "rain_heavy_"),
    SNOW_SHOWERS_SLIGHT(85, "snow_light_"),
    SNOW_SHOWERS_HEAVY(86, "snow_heavy_"),
    THUNDERSTORM_SLIGHT_MODERATE(95, "tstorm_"),
    THUNDERSTORM_HAIL_SLIGHT(96,"tstorm_"),
    THUNDERSTORM_HAIL_HEAVY(99,"tstorm_")
}
fun getWeatherCodeMap() : Map<Int,WMO_WeatherCode> {
    var weatherMap = HashMap<Int,WMO_WeatherCode>()
    WMO_WeatherCode.values().forEach {
        weatherMap.put(it.code ,it)
    }
    return weatherMap
}