package com.example.androiddevchallenge.data.fake

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.androiddevchallenge.data.WeatherInfo
import com.example.androiddevchallenge.data.Wind

class FakeProvider: PreviewParameterProvider<WeatherInfo> {
    override val values = fakeItems.asSequence()
    override val count: Int = values.count()
}

val fakeItems = arrayListOf(
    WeatherInfo(32,32,32, Wind(25, 67)),
    WeatherInfo(32,32,32, Wind(25, 67)),
    WeatherInfo(32,32,32, Wind(25, 67)),
    WeatherInfo(32,32,32, Wind(25, 67)),
    WeatherInfo(32,32,32, Wind(25, 67)),
    WeatherInfo(32,32,32, Wind(25, 67))
)