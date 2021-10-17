package com.melody.splashscreen.activity

import android.animation.Animator
import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.splashscreen.SplashScreenViewProvider
import androidx.core.view.postDelayed
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import com.melody.splashscreen.viewmodel.MainViewModel
import com.melody.splashscreen.screen.ExampleScreen
import com.melody.splashscreen.ui.theme.MyApplicationTheme
import com.melody.splashscreen.utils.killAppAndRemoveTask
import com.melody.splashscreen.screen.SplashAdScreen
import androidx.compose.ui.platform.ComposeView
import android.view.ViewGroup

/**
 * 全版本兼容SplashScreen
 */
class SplashScreenCompatActivity :ComponentActivity(),SplashScreen.OnExitAnimationListener{

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        setContent {
            MyApplicationTheme{
                ExampleScreen{
                    killAppAndRemoveTask()
                }
            }
        }
        splashScreen.setKeepVisibleCondition {
            //延迟2.5秒
            !mainViewModel.mockDataLoading()
        }
        splashScreen.setOnExitAnimationListener(this)
    }

    override fun onSplashScreenExit(splashScreenViewProvider: SplashScreenViewProvider) {
        //有需要显示广告的可以参考下面的写法:
        /*if(splashScreenViewProvider.view is ViewGroup){
            //显示一个广告或者启动页推广,自己实践玩耍吧,建议把mainViewModel.mockDataLoading()延时降低，然后测试
            val composeView = ComposeView(this@SplashScreenCompatActivity).apply {
                setContent {
                    SplashAdScreen {
                        splashScreenViewProvider.remove()
                    }
                }
            }
            (splashScreenViewProvider.view as ViewGroup).addView(composeView)
            return
        }*/

        //如果在themes.xml中配置了：静态背景, 改成true看效果
        val flag = false
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.R || flag){
            // 使用alpha透明度动画过渡
            val splashScreenView = splashScreenViewProvider.view
            val endAlpha = if(Build.VERSION.SDK_INT < Build.VERSION_CODES.R) 0F else -2F
            val alphaObjectAnimator = ObjectAnimator.ofFloat(splashScreenView, View.ALPHA, 1F, endAlpha)
            alphaObjectAnimator.duration = 500L
            alphaObjectAnimator.interpolator = FastOutLinearInInterpolator()
            alphaObjectAnimator.doOnEnd {
                splashScreenViewProvider.remove()
            }
            alphaObjectAnimator.start()
            return
        }

        //下面是所有使用动态背景的，我们让中心图标做一个动画然后离开
        val splashScreenView = splashScreenViewProvider.view
        val iconView = splashScreenViewProvider.iconView
        val isCompatVersion = Build.VERSION.SDK_INT < Build.VERSION_CODES.R
        val slideUp = ObjectAnimator.ofFloat(
            iconView,
            View.TRANSLATION_Y,
            0f,
            -splashScreenView.height.toFloat()
        )
        slideUp.interpolator = AnticipateInterpolator()
        slideUp.duration = if(isCompatVersion) 1000L else 200L
        slideUp.doOnEnd {
            splashScreenViewProvider.remove()
        }
        if (isCompatVersion) {
            //低版本的系统，我们让图标做完动画再关闭
            waitForAnimatedIconToFinish(splashScreenViewProvider, splashScreenView, slideUp)
        } else {
            slideUp.start()
        }
    }

    private fun waitForAnimatedIconToFinish(
        splashScreenViewProvider: SplashScreenViewProvider,
        view: View,
        animator: Animator
    ) {
        val delayMillis: Long = (
                splashScreenViewProvider.iconAnimationStartMillis +
                        splashScreenViewProvider.iconAnimationDurationMillis
                ) - System.currentTimeMillis()
        view.postDelayed(delayMillis) { animator.start() }
    }
}