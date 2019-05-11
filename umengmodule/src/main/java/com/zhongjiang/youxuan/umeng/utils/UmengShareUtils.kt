package com.zhongjiang.youxuan.umeng.utils

import android.app.Activity
import android.graphics.BitmapFactory
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMImage
import com.umeng.socialize.media.UMWeb


class UmengShareUtils {
    companion object {
        var onstart: (SHARE_MEDIA?) -> Unit = {}
        var onresult: (SHARE_MEDIA?) -> Unit = {}
        var oncancel: (SHARE_MEDIA?) -> Unit = {}
        var onerror: (SHARE_MEDIA?) -> Unit = {}
        val onUMShareListener: UMShareListener by lazy {
            object : UMShareListener {
                override fun onResult(p0: SHARE_MEDIA?) {
                    onresult
                }

                override fun onCancel(p0: SHARE_MEDIA?) {
                    oncancel
                }

                override fun onError(p0: SHARE_MEDIA?, p1: Throwable?) {
                    onerror
                }

                override fun onStart(p0: SHARE_MEDIA?) {
                    onstart
                }

            }
        }

        fun share(context: Activity, title: String, content: String?, contentUrl: String?, imgUrl: Array<String>?, vararg shareMedias: SHARE_MEDIA) {
            //SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN
            val bitmap = BitmapFactory.decodeResource(context.resources,6666)
            var image = UMImage(context, bitmap)

            var shareAction = ShareAction(context).withText(title)
                    .setCallback(onUMShareListener)
            var mImages: ArrayList<UMImage> = arrayListOf()
            if (imgUrl != null) {
                imgUrl.filter {
                    it.isNotEmpty()
                }.map {
                    mImages.add(UMImage(context,it))
                }
            }

            if (!contentUrl.isNullOrEmpty()) {
                var webImg = image
                if (mImages.isNotEmpty()) {
                    webImg = mImages[0]
                }
                val web = UMWeb(contentUrl, title, content, webImg)
                shareAction.withMedia(web)
            } else if (mImages.isNotEmpty()) {
                shareAction.withMedias(*mImages.toTypedArray())
            }


            if (shareMedias.size > 1) {
                shareAction.setDisplayList(*shareMedias).open();
            } else {
                shareAction.setPlatform(shareMedias[0]).share()
            }
        }
    }
}