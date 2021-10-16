package com.melody.splashscreen.utils

import android.app.Activity
import android.os.Process

fun Activity.killAppAndRemoveTask(){
    finishAndRemoveTask()
    Process.killProcess(Process.myPid())
}