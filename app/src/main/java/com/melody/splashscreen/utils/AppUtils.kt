package com.melody.splashscreen.utils

import android.app.Activity
import android.content.res.Resources
import android.os.Process
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp

fun Activity.killAppAndRemoveTask(){
    finishAndRemoveTask()
    Process.killProcess(Process.myPid())
}

fun Resources.getStatusBarHeight():Int {
    var statusBarHeight = 0
    val resourceId = getIdentifier("status_bar_height", "dimen", "android")
    if(resourceId >0 ){
        statusBarHeight = getDimensionPixelSize(resourceId)
    }
    return statusBarHeight
}

@Composable
fun ProvidableCompositionLocal<Density>.px2dp(px:Int):Dp = current.run {
    px.toDp()
}

@Composable
fun ProvidableCompositionLocal<Density>.dp2px(dpValue:Dp):Int = current.run {
    dpValue.roundToPx()
}