package com.zhongjiang.kotlin.base.common

import com.zhongjiang.kotlin.base.oss.UpFileBean

/**
* @param maxSelectNum 最大选择数
 * @param isEnableCrop 是否裁剪
 * @param circleDimmedLayer 是否圆形裁剪
 * @param aspect_ratio_x 裁剪宽高比  宽
 * @param aspect_ratio_y 裁剪宽高比  高
 * @param isCompress 是否压缩
 * */
data class PictureSelectorConfig(var filemoduleType: UpFileBean.Companion.FileModuleType,var maxSelectNum:Int,var isCompress:Boolean,var isEnableCrop:Boolean,var circleDimmedLayer:Boolean,var aspect_ratio_x:Int,var aspect_ratio_y:Int)