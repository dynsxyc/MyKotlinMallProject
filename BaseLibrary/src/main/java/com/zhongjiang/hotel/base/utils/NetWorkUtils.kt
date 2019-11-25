package com.zhongjiang.hotel.base.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.text.TextUtils
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

/*
    网络工具
 */
object NetWorkUtils {

    @SuppressLint("MissingPermission")
/*
        判断网络是否可用
     */
    fun isNetWorkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    @SuppressLint("MissingPermission")
/*
        检测wifi是否连接
     */
    fun isWifiConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        return networkInfo != null && networkInfo.type == ConnectivityManager.TYPE_WIFI
    }

    @SuppressLint("MissingPermission")
/*
        检测3G是否连接
     */
    fun is3gConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        return networkInfo != null && networkInfo.type == ConnectivityManager.TYPE_MOBILE
    }

    /**
     * 接口参数签名
     *
     *
     * 1）将接口参数进行字典序排序
     *
     *
     * 2）将参数排序结果尾部追加“&key=秘钥”拼接成一个新字符串进行sha1加密
     *
     *
     * 3）服务器获得加密后的字符串可与signature对比，标识该请求来源合法性
     */
    @Throws(NoSuchAlgorithmException::class)
    fun getSign(map: Map<String, String>?, key: String): String {
        var mMap = map

        val list = ArrayList<String>()
        if (mMap == null) {
            mMap = HashMap()
        }
        for ((key, value) in mMap) {

            if (!TextUtils.isEmpty(value) && "signature" != key) {
                list.add("$key=$value&")
            }
        }

        val size = list.size

        Collections.sort(list)

        val sb = StringBuilder()

        for (i in 0 until size) {
            sb.append(list[i])
        }

        var result = sb.toString()

        result += "key=" + key

        // SHA1签名生成
        val md = MessageDigest.getInstance("SHA-1")

        md.update(result.toByteArray())

        val digest = md.digest()

        val hexstr = StringBuffer()

        var shaHex = ""

        for (i in digest.indices) {
            //https://blog.csdn.net/lablenet/article/details/78921803
            shaHex = Integer.toHexString(digest[i].toInt() and 0xFF)

            if (shaHex.length < 2) {

                hexstr.append(0)

            }

            hexstr.append(shaHex)

        }

        return hexstr.toString()

    }
}
