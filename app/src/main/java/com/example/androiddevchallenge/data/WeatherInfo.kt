package com.example.androiddevchallenge.data

import androidx.annotation.DrawableRes
import com.example.androiddevchallenge.R
import java.util.*
import kotlin.random.Random

data class WeatherInfo(
    val temp: Int,
    val min: Int,
    val max: Int,
    val windDirection: Wind,
    val timeStamp: Long = 0L,
    val type: WeatherType = WeatherType.Clear,
    val location: Location = Location("Bardoli", "Gujarat"),
    val allDayData: ArrayList<WeatherInfo> = arrayListOf(),
    val precipitation: Int = Random.nextInt(1, 25),
    var isParentItem: Boolean = true
) {

    companion object {
        fun getWithRandomValues(timeStamp: Long = Date().time, index: Int = 0): WeatherInfo {
            val temperature = Random.nextInt(-10, 50)
            val maxTemperature = Random.nextInt(-10, temperature + 1)
            val minTemperature = Random.nextInt(-10, maxTemperature + 1)

            return WeatherInfo(
                temp = temperature,
                min = minTemperature,
                max = maxTemperature,
                windDirection = Wind.getWithRandomValues(),
                timeStamp = timeStamp,
                type = WeatherType.getRandom(index)
            )
        }
    }

    fun displaySpeed(): String {
        return "%s km/h".format(windDirection.speed.toString())
    }

    fun displayPrecipitation(): String {
        return "$precipitation%"
    }
}

data class Location(
    val city: String,
    val state: String
)

data class Wind(
    val speed: Int,
    val deg: Int,
) {
    companion object {

        fun getWithRandomValues(): Wind {
            return Wind(
                speed = Random.nextInt(0, 30),
                deg = Random.nextInt(0, 360)
            )
        }
    }
}

sealed class TemperatureUnit {
    object C : TemperatureUnit()
    object F : TemperatureUnit()

    fun toConversion(value: Int): Int {
        return when (this) {
            C -> value
            F -> (value / 5 * 9) + 32
        }
    }

    fun getLabel(): Int {
        return when (this) {
            C -> R.string.celsius_unit
            F -> R.string.fahrenheit_unit
        }
    }
}

sealed class WeatherType {
    object Clear : WeatherType()
    object Cloudy : WeatherType()
    object Rainy : WeatherType()
    object CloudyRain : WeatherType()
    object SunCloudyRain : WeatherType()
    object SunCloudy : WeatherType()
    object ThunderStorm : WeatherType()
    object Windy : WeatherType()
    object Snowy : WeatherType()

    companion object {
        fun getRandom(index: Int): WeatherType {
            return when (index) {
                0 -> Cloudy
                1 -> Clear
                2 -> Rainy
                3 -> CloudyRain
                4 -> SunCloudyRain
                5 -> ThunderStorm
                6 -> Windy
                7 -> Snowy
                else -> Clear
            }
        }
    }

    fun getLabelResId(): Int {
        return when (this) {
            Clear -> R.string.w_clear
            Cloudy -> R.string.w_cloudy
            Rainy -> R.string.w_rainy
            SunCloudyRain -> R.string.w_cloud_day_rain
            CloudyRain -> R.string.w_cloudy_rain
            SunCloudy -> R.string.w_cloudy_day
            ThunderStorm -> R.string.w_thunderstorm
            Windy -> R.string.w_wind
            Snowy -> R.string.w_snowy
        }
    }

    @DrawableRes
    fun getIconRes(): Int {
        return when (this) {
            Clear -> R.drawable.ic_clear_day
            Cloudy -> R.drawable.ic_drizzle
            SunCloudyRain -> R.drawable.ic_partly_cloudy_day_rain
            CloudyRain -> R.drawable.ic_rain
            SunCloudy -> R.drawable.ic_partly_cloudy_day
            ThunderStorm -> R.drawable.ic_thunderstorms
            Windy -> R.drawable.ic_tornado
            Snowy -> R.drawable.ic_snow
            Rainy -> R.drawable.ic_droplet
        }
    }

    @DrawableRes
    fun getImageRes(): Int {
        return when (this) {
            Clear -> R.drawable.ic_clound_sun_rain_small
            Cloudy -> R.drawable.ic_large_zap_cloud
            CloudyRain -> R.drawable.ic_large_sun_with_clound_rain
            Rainy -> R.drawable.ic_large_big_rain_drops
            SunCloudy -> R.drawable.ic_large_sun_with_clound_rain
            SunCloudyRain -> R.drawable.ic_large_sun_with_clound_rain
            ThunderStorm -> R.drawable.ic_large_zap
            Windy -> R.drawable.ic_large_tornado
            Snowy -> R.drawable.ic_large_snow
        }
    }

    fun getGradientColor(): Pair<Int, Int> {
        return when (this) {
            Cloudy -> Pair(R.color.blue_top, R.color.blue_bottom)
            Clear -> Pair(R.color.orange_top, R.color.orange_bottom)
            CloudyRain -> Pair(R.color.gray_top, R.color.gray_bottom)
            Rainy -> Pair(R.color.sky_blue_top, R.color.sky_blue_bottom)
            SunCloudy -> Pair(R.color.sunny_top, R.color.sunny_bottom)
            else -> Pair(R.color.green_top, R.color.green_bottom)
        }
    }
}