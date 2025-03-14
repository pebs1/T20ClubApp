package fm.mrc.t20clubapp.data

data class WeatherResponse(
    val list: List<DayForecast>
)

data class DayForecast(
    val dt: Long,  // Unix timestamp
    val main: Main,
    val wind: Wind,
    val pop: Double,  // Probability of precipitation
    val weather: List<Weather>
)

data class Main(
    val temp: Double,
    val temp_min: Double,
    val temp_max: Double
)

data class Wind(
    val speed: Double  // Wind speed in m/s
)

data class Weather(
    val main: String,
    val description: String,
    val icon: String
)

data class KiteFlyingScore(
    val date: String,
    val score: Int,
    val windSpeed: Double,
    val rainChance: Double,
    val weatherDescription: String = ""
) 