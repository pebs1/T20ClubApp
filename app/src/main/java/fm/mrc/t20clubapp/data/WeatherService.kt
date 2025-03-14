package fm.mrc.t20clubapp.data

import fm.mrc.t20clubapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface WeatherService {
    companion object {
        private const val BIKANER_LAT = 28.0229
        private const val BIKANER_LON = 73.3119
    }

    @GET("forecast")  // Using standard forecast endpoint with extended days
    suspend fun getWeatherForecast(
        @Query("lat") lat: Double = BIKANER_LAT,
        @Query("lon") lon: Double = BIKANER_LON,
        @Query("units") units: String = "metric",
        @Query("cnt") count: Int = 40,  // Get more data points to ensure we have enough for 7 days
        @Query("appid") apiKey: String
    ): Response<WeatherResponse>
}

object WeatherApi {
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"  // Using v2.5 API

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
        redactHeader("Authorization")
        redactHeader("Cookie")
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .build()

    private val retrofit = retrofit2.Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
        .build()

    private val weatherService: WeatherService = retrofit.create(WeatherService::class.java)

    suspend fun getWeatherForecast(): WeatherResponse {
        val response = weatherService.getWeatherForecast(apiKey = BuildConfig.WEATHER_API_KEY)
        if (!response.isSuccessful) {
            val errorBody = response.errorBody()?.string()
            throw Exception("API call failed with code ${response.code()}: $errorBody")
        }
        return response.body() ?: throw Exception("Response body is null")
    }
} 