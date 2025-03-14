package fm.mrc.t20clubapp.data

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object KiteFlyingCalculator {
    private const val IDEAL_WIND_SPEED_MIN = 13.0 // mph
    private const val IDEAL_WIND_SPEED_MAX = 17.0 // mph
    private const val WIND_SPEED_WEIGHT = 1.0 // Only using wind speed for score

    fun calculateScore(windSpeedMph: Double): Int {
        // Calculate wind speed score (0-100)
        val windScore = when {
            windSpeedMph < IDEAL_WIND_SPEED_MIN -> {
                // Below ideal: score decreases linearly from 100 to 0
                ((windSpeedMph / IDEAL_WIND_SPEED_MIN) * 100).toInt().coerceIn(0, 100)
            }
            windSpeedMph > IDEAL_WIND_SPEED_MAX -> {
                // Above ideal: score decreases as wind increases
                val excess = windSpeedMph - IDEAL_WIND_SPEED_MAX
                val maxExcess = 8.0 // Score reaches 0 at 25 mph
                (100 - (excess / maxExcess * 100)).toInt().coerceIn(0, 100)
            }
            else -> 100 // Perfect wind speed between 13-17 mph
        }

        return windScore
    }

    fun formatDate(timestamp: Long): String {
        val date = Date(timestamp * 1000)
        // Use a Calendar to ensure we're working with dates at midnight for consistency
        val calendar = java.util.Calendar.getInstance().apply {
            time = date
            // Reset time portion to midnight to ensure consistent date handling
            set(java.util.Calendar.HOUR_OF_DAY, 0)
            set(java.util.Calendar.MINUTE, 0)
            set(java.util.Calendar.SECOND, 0)
            set(java.util.Calendar.MILLISECOND, 0)
        }
        val formatter = SimpleDateFormat("EEE, MMM d", Locale.getDefault())
        return formatter.format(calendar.time)
    }

    fun getScoreDescription(score: Int): String {
        return when {
            score >= 90 -> "Perfect Conditions"
            score >= 75 -> "Very Good"
            score >= 60 -> "Good"
            score >= 45 -> "Fair"
            score >= 30 -> "Poor"
            else -> "Not Recommended"
        }
    }

    fun convertMpsToMph(mps: Double): Double {
        return mps * 2.23694 // Convert meters per second to miles per hour
    }
} 