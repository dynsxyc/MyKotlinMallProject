package com.zhongjiang.hotel.base.oss

import io.reactivex.disposables.Disposable
import java.text.SimpleDateFormat
import java.util.*

class UpFileBean constructor(var filemoduleType: FileModuleType, var filePath: String) {
    companion object {
        /**
         *
         * @param isSecurity 是否私有库 图片加水印 使用
         * */
        enum class FileModuleType constructor(internal var content: String, internal var isSecurity: Boolean) {
            COMMUNITY("community", false),
            HEAD("head", false),
            ANDROID("android", false),
            EVALUATE("evaluate", false),
            FEEDBACK("feedback", false),
            ORDER("order", false),
            ORDER_SECURITY("order", true),
            ORDER_MP3("order", false)
        }
    }

    var fileName = getOssUUIDFileName(filemoduleType)
    /**
     * 上传进度
     * */
    var progress: Int = 0
    /**
     * 记录上传状态
     * 1 上传进度
     * 2 上传成功
     * 3 上传失败
     * */
    var upType: Int = 1
    var upSuccessUrl: String? = null

    var disposable: Disposable?= null

    /**
     * @param type  1 语音文件mp3  默认传图片
     */
    private fun getOssUUIDFileName(moduleType: FileModuleType): String {
        return if (moduleType == FileModuleType.ORDER_MP3) {
            getModulePath(moduleType) + "/" + formatDate(Date(), "") + "/" + UUID.randomUUID().toString() + "And" + ".mp3"
        } else {
            getModulePath(moduleType) + "/" + formatDate(Date(), "") + "/" + UUID.randomUUID().toString() + "And" + ".png"

        }
    }

    fun formatDate(date: Date, splitChar: String): String {
        val sfdate = SimpleDateFormat(
                "yyyy" + splitChar + "MM" + splitChar + "dd")
        return sfdate.format(date)
    }

    fun getModulePath(moduleType: FileModuleType): String {
        return "user/" + moduleType.content
    }
}