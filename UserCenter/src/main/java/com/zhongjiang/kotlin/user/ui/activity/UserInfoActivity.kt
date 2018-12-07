package com.zhongjiang.kotlin.user.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import com.bigkoo.alertview.AlertView
import com.jph.takephoto.app.TakePhoto
import com.jph.takephoto.app.TakePhotoImpl
import com.jph.takephoto.compress.CompressConfig
import com.jph.takephoto.model.TResult
import com.kotlin.base.utils.AppPrefsUtils
import com.kotlin.base.utils.DateUtils
import com.kotlin.provider.common.ProviderConstant
import com.kotlin.user.utils.UserPrefsUtils
import com.qiniu.android.http.ResponseInfo
import com.qiniu.android.storage.UpCompletionHandler
import com.qiniu.android.storage.UploadManager
import com.zhongjiang.kotlin.base.common.BaseConstant.Companion.IMAGE_SERVER_ADDRESS
import com.zhongjiang.kotlin.base.ext.OnClick
import com.zhongjiang.kotlin.base.ui.activity.BaseMvpActivity
import com.zhongjiang.kotlin.user.R
import com.zhongjiang.kotlin.user.data.protocol.UserInfo
import com.zhongjiang.kotlin.user.injection.component.DaggerUserComponent
import com.zhongjiang.kotlin.user.presenter.UserInfoPresenter
import com.zhongjiang.kotlin.user.presenter.view.UserInfoView
import kotlinx.android.synthetic.main.activity_user_info.*
import org.jetbrains.anko.toast
import org.json.JSONObject
import java.io.File

class UserInfoActivity : BaseMvpActivity<UserInfoPresenter>(), UserInfoView, View.OnClickListener, TakePhoto.TakeResultListener {
    override fun getThisContentLayoutRes(): Int {
        return R.layout.activity_user_info
    }

    override fun initThisView() {
        mUserIconView.OnClick(this)
        mHeaderBar.getRightTv().OnClick(this)
    }


    private lateinit var mTakePhoto: TakePhoto
    private lateinit var mTempFile: File
    private var mLocalFileUrl: String?=null
    private var mRemoteFileUrl: String?=null

    private var mUserIcon:String? = null
    private var mUserName:String? = null
    private var mUserMobile:String? = null
    private var mUserGender:String? = null
    private var mUserSign:String? = null;

    private val uploadManager: UploadManager by lazy {
        UploadManager()
    }

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(activityComponent).build().inject(this)
        mPresenter.mView = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mTakePhoto = TakePhotoImpl(this, this)
        mTakePhoto.onCreate(savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mTakePhoto.onActivityResult(requestCode, resultCode, data)
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        mTakePhoto.onSaveInstanceState(outState)
    }

    /*
        初始化数据
     */
    override fun loadThisData() {
        mUserIcon = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_ICON)
        mUserName = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_NAME)
        mUserMobile = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_MOBILE)
        mUserGender = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_GENDER)
        mUserSign = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_SIGN)

        mRemoteFileUrl = mUserIcon
        if (mUserIcon != ""){
//            GlideUtils.loadUrlImage(this,mUserIcon!!,mUserIconIv)
        }
        mUserNameEt.setText(mUserName)
        mUserMobileTv.text = mUserMobile

        if (mUserGender == "0") {
            mGenderMaleRb.isChecked = true
        }
        else {
            mGenderFemaleRb.isChecked = true
        }

        mUserSignEt.setText(mUserSign)

    }
    override fun onClick(v: View) {
        when (v.id) {
            R.id.mUserIconView -> {
                AlertView.Builder().setContext(this)
                        .setStyle(AlertView.Style.ActionSheet)
                        .setTitle("选择图片")
                        .setMessage(null)
                        .setDestructive("拍照", "相册")
                        .setOthers(null)
                        .setCancelText("取消")
                        .setOnItemClickListener { _, position ->
                            mTakePhoto.onEnableCompress(CompressConfig.ofDefaultConfig(), false)
                            when (position) {
                                0 -> {
                                    createTempFile()
                                    mTakePhoto.onPickFromCapture(Uri.fromFile(mTempFile))
                                }
                                1 -> {
                                    mTakePhoto.onPickFromGallery()
                                }
                            }
                        }
                        .build().show()
            }
            R.id.mRightTv ->{
                mPresenter.editUser(mRemoteFileUrl!!,
                        mUserNameEt.text?.toString()?:"",
                        if(mGenderMaleRb.isChecked) "0" else "1",
                        mUserSignEt.text?.toString()?:""
                )
            }
        }
    }

    override fun takeSuccess(result: TResult?) {
        Log.d("test", "takePhoto 压缩地址" + result?.image?.compressPath)
        Log.d("test", "takePhoto 原始地址" + result?.image?.originalPath)
        mLocalFileUrl = result?.image?.compressPath
        mPresenter.getUploadToken()
    }

    override fun takeCancel() {
    }

    override fun takeFail(result: TResult?, msg: String?) {
    }

    fun createTempFile() {
        val tempFileName = "${DateUtils.curTime}.png"
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            this.mTempFile = File(Environment.getExternalStorageDirectory(), tempFileName)
            return
        }
        this.mTempFile = File(filesDir, tempFileName)
    }

    override fun onGetUploadTokenResult(result: String) {
        uploadManager.put(mLocalFileUrl,null,result,object :UpCompletionHandler{
            override fun complete(key: String?, info: ResponseInfo?, response: JSONObject?) {
                mRemoteFileUrl =  IMAGE_SERVER_ADDRESS+ response?.get("hash")
//                GlideUtils.loadUrlImage(this@UserInfoActivity, mRemoteFileUrl!!,mUserIconIv)
            }

        },null)
    }

    override fun onEditUserResult(result: UserInfo) {
        toast("修改成功")
        UserPrefsUtils.putUserInfo(result)
    }

}