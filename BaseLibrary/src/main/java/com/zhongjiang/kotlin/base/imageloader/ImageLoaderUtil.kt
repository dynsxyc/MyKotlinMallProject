package com.zhongjiang.kotlin.base.imageloader;

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.text.TextUtils
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.zhongjiang.kotlin.base.R
import org.jetbrains.anko.dip


/**
 * 工具类，所有负责网络加载图片的，经由此类受理 该类是Universal Image Loader代理
 * 默认图改变的时候，请在registerOptionMap函数中注册
 *
 * @author zhangleilei
 */
class ImageLoaderUtil {
    enum class IMAGE_STYLE_TYPE {
        //dialog 弹框上部图片
        IMAGE_TYPE_ID_UXDIALOG_IMG,
        //5圆角的图片
        IMAGE_TYPE_ID_FILLET_5,
        //默认图片样式显示
        IMAGE_TYPE_DEFAULT,
        //用户头像显示
        IMAGE_TYPE_USER_ICON,
        //意见反馈不规则圆角
        IMAGE_TYPE_FEED_BACK_SEND,
        //20圆角的图片 社区列表
        IMAGE_TYPE_ID_FILLET_10,
        /**
         * 原图加载
         */
        IMAGE_TYPE_RESOURCE,
        /**
         * 店铺头像 圆角白色描边
         */
        IMAGE_TYPE_SHOP_HEADER,
        IMAGE_TYPE_BANNER
    }

    companion object {


        fun getLoadingImageRadiusResourcesId(context: Context, radius: FloatArray): Drawable {
            val drawable = GradientDrawable()
            val colors = context.resources.getStringArray(R.array.default_img_colors)
            val radom = java.util.Random().nextInt(8)
            drawable.shape = GradientDrawable.RECTANGLE
            drawable.cornerRadii = radius
            drawable.setColor(Color.parseColor(colors[radom]))
            return drawable
        }

        fun getImageRequestOption(context: Context, type: IMAGE_STYLE_TYPE): RequestOptions {
            val options = RequestOptions()
            options.fitCenter()
            //下面一行要加  兼容性问题 Software rendering doesn't support hardware bitmaps
            options.disallowHardwareConfig()
            var diskCacheStrategy = DiskCacheStrategy.ALL
            var radius = floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)
            val dp20 = context.dip(20)
            val dp12 = context.dip(12)
            val dp5 = context.dip(5)
            val dp10 = context.dip(10)
            when (type) {
                ImageLoaderUtil.IMAGE_STYLE_TYPE.IMAGE_TYPE_BANNER -> {
                    options.centerCrop()
                    diskCacheStrategy = DiskCacheStrategy.RESOURCE
                }
            //dialog 弹框上部图片
                ImageLoaderUtil.IMAGE_STYLE_TYPE.IMAGE_TYPE_ID_UXDIALOG_IMG -> radius = floatArrayOf(dp12.toFloat(), dp12.toFloat(), dp12.toFloat(), dp12.toFloat(), 0f, 0f, 0f, 0f)
            //5圆角的图片
                ImageLoaderUtil.IMAGE_STYLE_TYPE.IMAGE_TYPE_ID_FILLET_5 -> radius = floatArrayOf(dp5.toFloat(), dp5.toFloat(), dp5.toFloat(), dp5.toFloat(), dp5.toFloat(), dp5.toFloat(), dp5.toFloat(), dp5.toFloat())
            //默认图片样式显示
                ImageLoaderUtil.IMAGE_STYLE_TYPE.IMAGE_TYPE_DEFAULT -> options.centerCrop()
            //用户头像显示
                ImageLoaderUtil.IMAGE_STYLE_TYPE.IMAGE_TYPE_USER_ICON -> options.circleCrop()
            //意见反馈不规则圆角
                ImageLoaderUtil.IMAGE_STYLE_TYPE.IMAGE_TYPE_FEED_BACK_SEND -> radius = floatArrayOf(dp20.toFloat(), dp20.toFloat(), dp5.toFloat(), dp5.toFloat(), dp20.toFloat(), dp20.toFloat(), dp20.toFloat(), dp20.toFloat())
            //20圆角的图片 社区列表
                ImageLoaderUtil.IMAGE_STYLE_TYPE.IMAGE_TYPE_ID_FILLET_10 -> radius = floatArrayOf(dp10.toFloat(), dp10.toFloat(), dp10.toFloat(), dp10.toFloat(), dp10.toFloat(), dp10.toFloat(), dp10.toFloat(), dp10.toFloat())

            //5 店铺详情圆角边框
                ImageLoaderUtil.IMAGE_STYLE_TYPE.IMAGE_TYPE_SHOP_HEADER -> radius = floatArrayOf(dp5.toFloat(), dp5.toFloat(), dp5.toFloat(), dp5.toFloat(), dp5.toFloat(), dp5.toFloat(), dp5.toFloat(), dp5.toFloat())
                ImageLoaderUtil.IMAGE_STYLE_TYPE.IMAGE_TYPE_RESOURCE -> {
                    options.centerCrop()
                    diskCacheStrategy = DiskCacheStrategy.RESOURCE
                }
                else -> {
                }
            }
            var drawable = getLoadingImageRadiusResourcesId(context, radius)
            if (type == IMAGE_STYLE_TYPE.IMAGE_TYPE_USER_ICON) {
                drawable = context.getResources().getDrawable(R.drawable.default_user_icon)
            }
            options.placeholder(drawable).error(drawable).diskCacheStrategy(diskCacheStrategy)
            val transformations = getTransformations(context, type)
            if (transformations != null && transformations.size > 0) {
                options.transforms(*transformations)
            }
            return options
        }

        fun getTransformations(context: Context, type: IMAGE_STYLE_TYPE): Array<Transformation<Bitmap>> {
            var transformations: Array<Transformation<Bitmap>> = arrayOf()
            when (type) {
            //dialog 弹框上部图片
                ImageLoaderUtil.IMAGE_STYLE_TYPE.IMAGE_TYPE_ID_UXDIALOG_IMG -> {
                    transformations = arrayOf()
                    transformations[0] = CenterCrop()
                    transformations[1] = GlideRoundedCornersTransformation(context, 12, GlideRoundedCornersTransformation.CornerType.TOP)
                }
            //5圆角的图片
                ImageLoaderUtil.IMAGE_STYLE_TYPE.IMAGE_TYPE_ID_FILLET_5 -> {
                    transformations = arrayOf()
                    transformations[0] = CenterCrop()
                    transformations[1] = GlideRoundedCornersTransformation(context, 5)
                }
            //默认图片样式显示
                ImageLoaderUtil.IMAGE_STYLE_TYPE.IMAGE_TYPE_DEFAULT -> {
                }
            //用户头像显示
                ImageLoaderUtil.IMAGE_STYLE_TYPE.IMAGE_TYPE_USER_ICON -> {
                }
            //意见反馈不规则圆角
                ImageLoaderUtil.IMAGE_STYLE_TYPE.IMAGE_TYPE_FEED_BACK_SEND -> {
                    transformations = arrayOf()
                    transformations[0] = CenterCrop()
                    transformations[1] = GlideRoundedCornersTransformation(context, 20, 5, 20, 20)
                }
            //20圆角的图片 社区列表
                ImageLoaderUtil.IMAGE_STYLE_TYPE.IMAGE_TYPE_ID_FILLET_10 -> {
                    transformations = arrayOf()
                    transformations[0] = CenterCrop()
                    transformations[1] = GlideRoundedCornersTransformation(context, 10)
                }
                ImageLoaderUtil.IMAGE_STYLE_TYPE.IMAGE_TYPE_RESOURCE -> {
                }
                ImageLoaderUtil.IMAGE_STYLE_TYPE.IMAGE_TYPE_SHOP_HEADER -> {
                    transformations = arrayOf()
                    transformations[0] = CenterCrop()
                    transformations[1] = GlideRoundedCornersTransformation(context, 5, true, 2, context.resources.getColor(R.color.white), GlideRoundedCornersTransformation.CornerType.ALL)
                }
            }
            return transformations
        }

        /**
         * 最终都会调到这里
         */
        open fun displayImage(url: String, imageview: ImageView, type: IMAGE_STYLE_TYPE, listener: RequestListener<Drawable>, width: Int, height: Int) {
            var url = url
            if (TextUtils.isEmpty(url)) {
                url = ""
            }
            if (url.startsWith("http") && type != IMAGE_STYLE_TYPE.IMAGE_TYPE_RESOURCE && width > 0 && height > 0) {
                url = "$url?x-oss-process=image/resize,m_fill,h_$height,w_$width"
            }
            displayImageLoad(url, imageview, type, listener)
        }

        open fun displayImage(sourceId: Int, imageview: ImageView, type: IMAGE_STYLE_TYPE, listener: RequestListener<Drawable>) {
            displayImageLoad(sourceId, imageview, type, listener)
        }

        fun displayImageLoad(url: Any, imageview: ImageView, type: IMAGE_STYLE_TYPE, listener: RequestListener<Drawable>) {
            if (imageview.context is Activity) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    if ((imageview.context as Activity).isDestroyed || (imageview.context as Activity).isFinishing) {
                        return
                    }
                }
            }
            Glide.with(imageview.context).load(url).apply(getImageRequestOption(imageview.context, type)).listener(listener).into(imageview)
        }
    }


}