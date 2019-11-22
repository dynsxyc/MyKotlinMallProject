package com.zhongjiang.hotel.base.common

import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.os.Looper
import android.widget.Toast
import com.zhongjiang.hotel.base.utils.ULogger
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


class CrashHandler constructor(val mContext: BaseApplication): Thread.UncaughtExceptionHandler {

    val TAG = "TEST"

    // 系统默认的 UncaughtException 处理类
    private var mDefaultHandler: Thread.UncaughtExceptionHandler? = null

    // 用来存储设备信息和异常信息
    private val infos = HashMap<String, String>()

    // 用来显示Toast中的信息
    private var error = "程序错误，额，不对，我应该说，服务器正在维护中，请稍后再试"

    private val regexMap = HashMap<String, String>()

    // 用于格式化日期,作为日志文件名的一部分
    private val formatter = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss",
            Locale.CHINA)

    /** 获取 CrashHandler 实例 ,单例模式  */
    init {
        initMap()

        // 获取系统默认的 UncaughtException 处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()

        // 设置该 CrashHandler 为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this)
        ULogger.d( "Crash:init")
    }

    /**
     * 当 UncaughtException 发生时会转入该函数来处理
     */
    override fun uncaughtException(thread: Thread, ex: Throwable) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler!!.uncaughtException(thread, ex)
            ULogger.d( "crash handler is default")
        } else {
            try {
                Thread.sleep(3000)
            } catch (e: InterruptedException) {
                ULogger.e(ex, "error : ", thread)
            }

            // 退出程序
            android.os.Process.killProcess(android.os.Process.myPid())
            // mDefaultHandler.uncaughtException(thread, ex);
            System.exit(1)
        }
    }

    /**
     * 自定义错误处理，收集错误信息，发送错误报告等操作均在此完成
     *
     * @param ex
     * @return true：如果处理了该异常信息；否则返回 false
     */
    private fun handleException(ex: Throwable): Boolean {
        // 收集设备参数信息
        // collectDeviceInfo(mContext);
        // 保存日志文件
        saveCrashInfo2File(ex)
        // 使用 Toast 来显示异常信息
        object : Thread() {
            override fun run() {
                Looper.prepare()
                Toast.makeText(mContext, error, Toast.LENGTH_LONG).show()
                Looper.loop()
            }
        }.start()
        return true
    }

    /**
     * 收集设备参数信息
     *
     */
    fun collectDeviceInfo() {
        try {
            val pm = mContext.packageManager
            val pi = pm.getPackageInfo(mContext.packageName,
                    PackageManager.GET_ACTIVITIES)

                val versionName = if (pi.versionName == null)
                    "null"
                else
                    pi!!.versionName
                val versionCode = pi.versionCode.toString()
                infos["versionName"] = versionName
                infos["versionCode"] = versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            ULogger.e( "an error occured when collect package info", e)
        }

        val fields = Build::class.java.declaredFields
        for (field in fields) {
            try {
                field.isAccessible = true
                infos[field.name] = field.get(null).toString()
                ULogger.d(TAG, field.name + " : " + field.get(null))
            } catch (e: Exception) {
                ULogger.e( "an error occured when collect crash info", e)
            }

        }
    }

    /**
     * 保存错误信息到文件中 *
     *
     * @param ex
     * @return 返回文件名称,便于将文件传送到服务器
     */
    private fun saveCrashInfo2File(ex: Throwable): String? {
        val sb = getTraceInfo(ex)
        val writer = StringWriter()
        val printWriter = PrintWriter(writer)
        ex.printStackTrace(printWriter)
        var cause: Throwable? = ex.cause
        while (cause != null) {
            cause.printStackTrace(printWriter)
            cause = cause.cause
        }
        printWriter.close()

        val result = writer.toString()
        sb.append(result)
        try {
            val timestamp = System.currentTimeMillis()
            val time = formatter.format(Date())
            val fileName = "crash-$time-$timestamp.log"

            if (Environment.getExternalStorageState().equals(
                            Environment.MEDIA_MOUNTED)) {
                val path = Environment.getExternalStorageDirectory().absolutePath + "/crash/"
                val dir = File(path)
                if (!dir.exists()) {
                    dir.mkdirs()
                }
                val fos = FileOutputStream(path + fileName)
                fos.write(sb.toString().toByteArray())
                fos.close()
            }

            return fileName
        } catch (e: Exception) {
            ULogger.e( "an error occured while writing file...", e)
        }

        return null
    }

    /**
     * 整理异常信息
     * @param e
     * @return
     */
    private fun getTraceInfo(e: Throwable): StringBuffer {
        val sb = StringBuffer()

        val ex = if (e.cause == null) e else e.cause
        ex?.let {
            val stacks = ex.stackTrace
            for ((index:Int,item:StackTraceElement) in stacks.withIndex()){
                if (index == 0) {
                    setError(ex.toString())
                }
                sb.append("class: ").append(item.className)
                        .append("; method: ").append(item.methodName)
                        .append("; line: ").append(item.lineNumber)
                        .append(";  Exception: ").append(ex.toString() + "\n")
            }
        }

        ULogger.d(TAG, sb.toString())
        return sb
    }

    /**
     * 设置错误的提示语
     * @param e
     */
    private fun setError(e: String) {
        var pattern: Pattern
        var matcher: Matcher
        for ((key, value) in regexMap) {
            ULogger.d(TAG, e + "key:" + key + "; value:" + value)
            pattern = Pattern.compile(key)
            matcher = pattern.matcher(e)
            if (matcher.matches()) {
                error = value
                break
            }
        }
    }

    /**
     * 初始化错误的提示语
     */
    private fun initMap() {
        // Java.lang.NullPointerException
        // java.lang.ClassNotFoundException
        // java.lang.ArithmeticException
        // java.lang.ArrayIndexOutOfBoundsException
        // java.lang.IllegalArgumentException
        // java.lang.IllegalAccessException
        // SecturityException
        // NumberFormatException
        // OutOfMemoryError
        // StackOverflowError
        // RuntimeException
        regexMap[".*NullPointerException.*"] = "嘿，无中生有~Boom!"
        regexMap[".*ClassNotFoundException.*"] = "你确定你能找得到它？"
        regexMap[".*ArithmeticException.*"] = "我猜你的数学是体育老师教的，对吧？"
        regexMap[".*ArrayIndexOutOfBoundsException.*"] = "恩，无下限=无节操，请不要跟我搭话"
        regexMap[".*IllegalArgumentException.*"] = "你的出生就是一场错误。"
        regexMap[".*IllegalAccessException.*"] = "很遗憾，你的信用卡账号被冻结了，无权支付"
        regexMap[".*SecturityException.*"] = "死神马上降临"
        regexMap[".*NumberFormatException.*"] = "想要改变一下自己形象？去泰国吧，包你满意"
        regexMap[".*OutOfMemoryError.*"] = "或许你该减减肥了"
        regexMap[".*StackOverflowError.*"] = "啊，啊，憋不住了！"
        regexMap[".*RuntimeException.*"] = "你的人生走错了方向，重来吧"

    }


}