package com.zhongjiang.youxuan.base.oss

interface UIDisplayer {
    /**上传进度*/
    fun updateProgress(progress: Int){

    }
    fun displayInfo(s: String) {

    }
    /**下载完成*/
     fun downloadComplete(bm: Any){

    }
    /**下载失败*/
    fun downloadFail(info: String) {

    }
    /**上传成功*/
    fun uploadComplete(){

    }
    /**上传失败*/
    fun uploadFail(info: String){

    }
}