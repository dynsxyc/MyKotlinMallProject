package com.zhongjiang.kotlin.umeng

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.bean.SHARE_MEDIA
import com.zhongjiang.kotlin.base.pay.weixinpay.WXPayUtils
import com.zhongjiang.kotlin.umeng.utils.UmengShareUtils
import kotlinx.android.synthetic.main.activity_main.*
import main.debug.com.zhongjiang.kotlin.umeng.apshare.ShareExt


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        UmengShareUtils.onstart = {ShareExt.onShareStart(it)}
        shareBt.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                UmengShareUtils.share(this@MainActivity,"title","content","https://www.jianshu.com/p/d58d1cdc8e91", arrayOf("","","https://ss0.baidu.com/73F1bjeh1BF3odCf/it/u=4123548537,462365207&fm=85&s=07175F801DC39A093601E585030050CB"), SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.ALIPAY, SHARE_MEDIA.SINA)
            }
        })
        shareBtTv.setOnClickListener {
            UmengShareUtils.share(this@MainActivity,"title",null,null,null, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.ALIPAY, SHARE_MEDIA.SINA)
        }
        shareBtMorImg.setOnClickListener {
            UmengShareUtils.share(this@MainActivity,"多图片分享","","", arrayOf("https://ss0.baidu.com/73F1bjeh1BF3odCf/it/u=4123548537,462365207&fm=85&s=07175F801DC39A093601E585030050CB","https://ss0.baidu.com/73F1bjeh1BF3odCf/it/u=4123548537,462365207&fm=85&s=07175F801DC39A093601E585030050CB","https://ss0.baidu.com/73F1bjeh1BF3odCf/it/u=4123548537,462365207&fm=85&s=07175F801DC39A093601E585030050CB"), SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.ALIPAY, SHARE_MEDIA.SINA)
        }
        shareBtTvImg.setOnClickListener {
            UmengShareUtils.share(this@MainActivity,"title","content","", arrayOf("","","https://ss0.baidu.com/73F1bjeh1BF3odCf/it/u=4123548537,462365207&fm=85&s=07175F801DC39A093601E585030050CB"), SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.ALIPAY, SHARE_MEDIA.SINA)
        }
        shareBtTvImgContenturl.setOnClickListener {
            UmengShareUtils.share(this@MainActivity,"title","content","https://www.jianshu.com/p/d58d1cdc8e91", arrayOf("","https://ss0.baidu.com/73F1bjeh1BF3odCf/it/u=4123548537,462365207&fm=85&s=07175F801DC39A093601E585030050CB","https://ss0.baidu.com/73F1bjeh1BF3odCf/it/u=4123548537,462365207&fm=85&s=07175F801DC39A093601E585030050CB"), SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.ALIPAY, SHARE_MEDIA.SINA)
        }
        shareBtImg.setOnClickListener {
            UmengShareUtils.share(this@MainActivity,"","","", arrayOf("","","https://ss0.baidu.com/73F1bjeh1BF3odCf/it/u=4123548537,462365207&fm=85&s=07175F801DC39A093601E585030050CB"), SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.ALIPAY, SHARE_MEDIA.SINA)
        }
        WXPayBt.setOnClickListener{
            WXPayUtils.startPay(this,"wx72a9d8637eb96ea1","1341772801","wx22144353269325c6bd6f686a1156508260","Sign=WXPay","FGQCmCerRvYCARNo","1553237312","Hsq/tQPKHA2KxGj3sEjbg0rRAPDWspwhfBjM5zn7aSoAIt3+eIkergaVW2ozvcWN")
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //QQ与新浪不需要添加Activity，但需要在使用QQ分享或者授权的Activity中，添加：
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data)
    }

    fun request() {
        if (Build.VERSION.SDK_INT >= 23) {
            val mPermissionList = arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS)
            ActivityCompat.requestPermissions(this, mPermissionList, 123)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {

    }
}
