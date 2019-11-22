package com.zhongjiang.hotel.base.oss

import com.zhongjiang.hotel.base.oss.OssConstant.Companion.HANGZHOU_ENDPOINT
import com.zhongjiang.hotel.base.oss.OssConstant.Companion.PUBLIC_BUCKET_NAME
import com.zhongjiang.hotel.base.oss.OssConstant.Companion.PUBLIC_URL_HOST
import com.zhongjiang.hotel.base.oss.OssConstant.Companion.SECURITY_BUCKET_NAME
import com.zhongjiang.hotel.base.oss.OssConstant.Companion.SECURITY_URL_HOST


enum class BucketType constructor(val bucketName: String, val callbackAddress: String, val endpoint: String) {
    /**公共文件存储类型 */
    BUCKET_CONFIT_TAG_PUBLIC(PUBLIC_BUCKET_NAME, PUBLIC_URL_HOST, HANGZHOU_ENDPOINT),
    /**水印图片文件存储类型 */
    BUCKET_CONFIT_TAG_SECURITY(SECURITY_BUCKET_NAME, SECURITY_URL_HOST, HANGZHOU_ENDPOINT);
}