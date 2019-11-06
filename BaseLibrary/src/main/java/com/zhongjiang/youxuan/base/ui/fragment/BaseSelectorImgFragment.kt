package com.zhongjiang.youxuan.base.ui.fragment

import android.app.Activity
import com.luck.picture.lib.PictureSelectionModel
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.zhongjiang.youxuan.base.common.PictureSelectorConfig
import com.zhongjiang.youxuan.base.oss.UpFileBean
import com.zhongjiang.youxuan.base.ui.basemvp.BasePresenter
import com.zhongjiang.youxuan.base.ui.basemvp.IModel


abstract class BaseSelectorImgFragment<P: BasePresenter<BaseSelectorImgFragment<P, M>, M> ,M: IModel>: BaseMvpFragment<P ,M>() {
    var defaultPictureSelectorConfig = PictureSelectorConfig(getDefaultFileModuleType(), getDefaultMaxSelectNum(), true, false, false, 0, 0)
    private var upFileList: ArrayList<UpFileBean> = arrayListOf()
    override fun initData() {
        mPresenter.registerActivityResultEvent { activityResultEvent ->
            when (activityResultEvent.requestCoder) {
                getRequestSelectorImgCode().plus(PictureConfig.CHOOSE_REQUEST) -> {
                    if (activityResultEvent.resultCode == Activity.RESULT_OK) {
                        activityResultEvent.intentData?.let {
                            // 图片、视频、音频选择结果回调
                            val selectList = PictureSelector.obtainMultipleResult(it)
                            // 例如 LocalMedia 里面返回三种path
                            // 1.media.getPath(); 为原图path
                            // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                            // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                            // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                            var newUpFiles = arrayListOf<UpFileBean>()
                            selectList.map {
                                var filePath = it.path
                                if (it.isCompressed) {
                                    filePath = it.compressPath
                                } else if (it.isCut) {
                                    filePath = it.cutPath
                                }
                                var upFileBean = UpFileBean(defaultPictureSelectorConfig.filemoduleType,filePath)
                                upFileList.add(upFileBean)
                                newUpFiles.add(upFileBean)
                            }
                            upFile(newUpFiles)

                        }
                    }

                }

            }
        }
    }

    protected fun openMedia(isCamera: Boolean) {
        var pictureselector = PictureSelector.create(_mActivity)
        var pictureselectionmodel: PictureSelectionModel
        if (isCamera) {
            pictureselectionmodel = pictureselector.openCamera(PictureMimeType.ofImage())
        } else {
            pictureselectionmodel = pictureselector.openGallery(PictureMimeType.ofImage())
        }
        pictureselectionmodel.theme(com.zhongjiang.youxuan.base.R.style.picture_default_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .maxSelectNum(defaultPictureSelectorConfig.maxSelectNum)// 最大图片选择数量 int
                .imageSpanCount(5)// 每行显示个数 int
                .selectionMode(if (defaultPictureSelectorConfig.maxSelectNum > 1) PictureConfig.MULTIPLE else PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(false)// 是否可预览图片 true or false
                .previewVideo(true)// 是否可预览视频 true or false
                .enablePreviewAudio(true) // 是否可播放音频 true or false
                .isCamera(false)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.8f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                .enableCrop(defaultPictureSelectorConfig.isEnableCrop)// 是否裁剪 true or false
                .compress(defaultPictureSelectorConfig.isCompress)// 是否压缩 true or false
                .glideOverride(200, 200)// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(1, 0)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                .isGif(false)// 是否显示gif图片 true or false
//                .compressSavePath(getPath())//压缩图片保存地址
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(defaultPictureSelectorConfig.circleDimmedLayer)// 是否圆形裁剪 true or false
                .showCropFrame(defaultPictureSelectorConfig.circleDimmedLayer.not())// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(defaultPictureSelectorConfig.circleDimmedLayer.not())// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .openClickSound(true)// 是否开启点击声音 true or false
//                .selectionMedia()// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .cropCompressQuality(90)// 裁剪压缩质量 默认90 int
                .minimumCompressSize(300)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
//                .cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
//                .videoQuality()// 视频录制质量 0 or 1 int
//                .videoMaxSecond(15)// 显示多少秒以内的视频or音频也可适用 int
//                .videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int
//                .recordVideoSecond()//视频秒数录制 默认60s int
                .isDragFrame(true)// 是否可拖动裁剪框(固定)
                .forResult(PictureConfig.CHOOSE_REQUEST.plus(getRequestSelectorImgCode()));//结果回调onActivityResult code
    }

    /**
     * 请求ImageSelector 库 的code
     * */
    abstract fun getRequestSelectorImgCode(): Int

    /**默认最大选取数量*/
    abstract fun getDefaultMaxSelectNum(): Int

    /**默认上传空间类型*/
    abstract fun getDefaultFileModuleType(): UpFileBean.Companion.FileModuleType

    /**
     *
     * */
    abstract fun onFileUpIng(upFileBean: UpFileBean)

    protected fun upFile(upFileBeans: ArrayList<UpFileBean>) {
        mPresenter.upFiles(upFileBeans) {
            onFileUpIng(it)
        }
    }
}