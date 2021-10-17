package com.melody.splashscreen.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.melody.splashscreen.utils.getStatusBarHeight
import com.melody.splashscreen.utils.px2dp

@Composable
fun SplashAdScreen(onCloseAd:()->Unit){
    Box(modifier = Modifier.fillMaxSize()){
        //广告或者推广的背景图
        Image(
            painter = rememberImagePainter(data = "http://img.daimg.com/uploads/allimg/200607/1-20060G34P6.jpg",builder = {
                crossfade(true)
            }),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        //倒计时按钮，这里仅测试一下效果
        Button(onClick = {
            onCloseAd.invoke()
        },modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(end=16.dp,top = LocalDensity.px2dp(px = LocalContext.current.resources.getStatusBarHeight()))) {
            Text(text = "假装:倒计时")
        }
    }
}