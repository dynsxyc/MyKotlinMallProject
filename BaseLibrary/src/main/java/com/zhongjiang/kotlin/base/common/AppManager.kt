package com.zhongjiang.kotlin.base.common

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import java.util.*

/**
 * Created by dyn on 2018/7/17.
 */
class AppManager private constructor() {
    private val activityStack: Stack<Activity> = Stack()

    //    伴生对象
    companion object {
        val instance: AppManager by lazy {
            AppManager()
        }
    }

    /*
    * 入栈
    * */
    fun addActivity(activity: Activity) {
        activityStack.add(activity)
    }

    /*
    * 出栈
    * */
    fun finishActivity(activity: Activity) {
        activity.finish()
        activityStack.remove(activity)
    }

    /**获取当前栈顶*/
    fun currentActivity(): Activity {
        return activityStack.lastElement()
    }

    /*
    * 清理栈
    * */
    fun finishAllActivity() {
        for (activity in activityStack) {
            activity.finish()
        }
        activityStack.clear()
    }

    @SuppressLint("MissingPermission")
/*
    * 退出应用  强退
    * */
    fun exitApp(context: Context) {
        finishAllActivity()
        val activityManger = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManger.killBackgroundProcesses(context.packageName)
        System.exit(0)
    }
}