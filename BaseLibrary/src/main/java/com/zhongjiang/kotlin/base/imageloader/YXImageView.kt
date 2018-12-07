package com.zhongjiang.kotlin.base.imageloader

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class YXImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : android.support.v7.widget.AppCompatImageView(context, attrs, defStyle), RequestListener<Drawable> {
    init {
        scaleType = ImageView.ScaleType.CENTER_CROP
        // TODO Auto-generated constructor stub

    }

    /**默认图片显示 */
    fun setImageUrl(url: String) {
        loadImage(url, ImageLoaderUtil.IMAGE_STYLE_TYPE.IMAGE_TYPE_DEFAULT)
    }

    fun setImageUrl(url: String, defaultImageType: ImageLoaderUtil.IMAGE_STYLE_TYPE) {
        loadImage(url, defaultImageType)
    }

    fun setImageUserIconUrl(url: String) {
        loadImage(url, ImageLoaderUtil.IMAGE_STYLE_TYPE.IMAGE_TYPE_USER_ICON)
    }

    /**5圆角图片显示 */
    fun setImageRadius5Url(url: String) {
        loadImage(url, ImageLoaderUtil.IMAGE_STYLE_TYPE.IMAGE_TYPE_ID_FILLET_5)
    }

    /**源图样式显示 */
    fun setSourceImageUrl(url: String) {
        loadImage(url, ImageLoaderUtil.IMAGE_STYLE_TYPE.IMAGE_TYPE_RESOURCE)
    }

    /**意见反馈  发送端 图片显示 */
    fun setFeedBackRoundImageUrl(url: String) {
        loadImage(url, ImageLoaderUtil.IMAGE_STYLE_TYPE.IMAGE_TYPE_FEED_BACK_SEND)
    }

    /**店铺详情店铺头像 显示 */
    fun setHasBorderRadiusImageUrl(url: String) {
        loadImage(url, ImageLoaderUtil.IMAGE_STYLE_TYPE.IMAGE_TYPE_SHOP_HEADER)
    }

    /**
     * 社区首页图片加载
     *
     * @param url
     */
    fun setCommunityImageUrl(url: String) {
        loadImage(url, ImageLoaderUtil.IMAGE_STYLE_TYPE.IMAGE_TYPE_ID_FILLET_10)
    }

    private fun loadImage(url: String, defaultImageType: ImageLoaderUtil.IMAGE_STYLE_TYPE) {
        ImageLoaderUtil.displayImage(url, this, defaultImageType, this, right - left, bottom - top)
    }

    fun setSourId(sourId: Int) {
        loadImage(sourId)
    }

    private fun loadImage(url: Int) {
        ImageLoaderUtil.displayImage(url, this, ImageLoaderUtil.IMAGE_STYLE_TYPE.IMAGE_TYPE_DEFAULT, this)
    }


    override fun onDisplayHint(hint: Int) {
        super.onDisplayHint(hint)
    }
    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
        return false;
    }

    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: com.bumptech.glide.load.DataSource?, isFirstResource: Boolean): Boolean {
        return false;
    }
}
