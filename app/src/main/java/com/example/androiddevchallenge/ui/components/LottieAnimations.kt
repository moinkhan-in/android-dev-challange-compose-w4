package com.example.androiddevchallenge.ui.components

import android.widget.ImageView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieAnimationSpec
import com.airbnb.lottie.compose.LottieAnimationState
import com.airbnb.lottie.compose.rememberLottieAnimationState
import com.example.androiddevchallenge.R


@Composable
fun WeatherTypeAnimation(
    modifier: Modifier
) {
//    val animationSpec = remember { LottieAnimationSpec.RawRes(R.raw.snow_5) }
//    val animationState = LottieAnimationState
//        rememberLottieAnimationState(autoPlay = true, repeatCount = Integer.MAX_VALUE)
//    LottieAnimation(
//        modifier = Modifier.fillMaxSize(),
//        spec = animationSpec,
//        animationState = animationState
//    )

    val context = LocalContext.current

    val view = remember { LottieAnimationView(context) }
    val view2 = remember { LottieAnimationView(context) }

    Box(modifier = Modifier.fillMaxSize()) {


//        AndroidView({ view }, modifier = Modifier.fillMaxSize()) {
//            with(view) {
//                setAnimation(R.raw.raining)
//                playAnimation()
//                repeatCount = LottieDrawable.INFINITE
//                scaleType = ImageView.ScaleType.CENTER_CROP
//            }
//        }
//
//        AndroidView({ view2 }, modifier = Modifier.fillMaxSize()) {
//            with(view2) {
//                setAnimation(R.raw.snow_fall)
//                playAnimation()
//                repeatCount = LottieDrawable.INFINITE
//                scaleType = ImageView.ScaleType.CENTER_CROP
//            }
//        }
    }
}
