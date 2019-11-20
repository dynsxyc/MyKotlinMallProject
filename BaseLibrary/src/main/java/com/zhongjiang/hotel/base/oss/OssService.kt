package com.zhongjiang.hotel.base.oss

import android.content.Context
import com.alibaba.sdk.android.oss.*
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback
import com.alibaba.sdk.android.oss.common.OSSLog
import com.alibaba.sdk.android.oss.common.auth.OSSCustomSignerCredentialProvider
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider
import com.alibaba.sdk.android.oss.model.*
import com.zhongjiang.youxuan.base.common.YouXuanNetInterfaceConstant.Companion.BASE_URL_DEVELOP_TEST
import com.zhongjiang.youxuan.base.ext.handlerThread
import com.zhongjiang.hotel.base.injection.module.sheduler.SchedulerProvider
import com.zhongjiang.youxuan.base.utils.ULogger
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableOnSubscribe
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.File
import java.io.IOException
import javax.inject.Inject


/**
 * Created by mOss on 2015/12/7 0007.
 * 支持普通上传，普通下载
 */
class OssService @Inject constructor(var context: Context, var schedulerProvider: com.zhongjiang.hotel.base.injection.module.sheduler.SchedulerProvider, val mBucketType: BucketType) {

    companion object {
        private const val GET_TOKEN_URL = "file/sts"

        private val mResumableObjectKey = "resumableObject"
    }
    private lateinit var mOss: OSS
    private var mCallbackAddress: String = mBucketType.callbackAddress
    private var mBucket: String = mBucketType.bucketName

    init {
        getDefaultOss(mBucketType.endpoint)
    }

    private fun getDefaultOss(endpoint: String) {
        //使用自己的获取STSToken的类
        val credentialProvider = com.zhongjiang.hotel.base.oss.MOSSAuthCredentialsProvider(BASE_URL_DEVELOP_TEST.plus(GET_TOKEN_URL))

        val conf = ClientConfiguration()
        conf.connectionTimeout = 15 * 1000 // 连接超时，默认15秒
        conf.socketTimeout = 15 * 1000 // socket超时，默认15秒
        conf.maxConcurrentRequest = 5 // 最大并发请求书，默认5个
        conf.maxErrorRetry = 2 // 失败后最大重试次数，默认2次
        Flowable.just(credentialProvider).flatMap {
            Flowable.just(OSSClient(context, endpoint, it, conf))
        }.handlerThread(schedulerProvider).subscribe {
            mOss = it
        }

    }

    fun asyncPutFile(upFile: UpFileBean): Flowable<UpFileBean> {
        return Flowable.create(FlowableOnSubscribe<UpFileBean> { flowableEmitter ->
            val file = File(upFile.filePath)
            if (upFile.filePath.isEmpty() || file.exists().not()) {
                upFile.upType = 3
                flowableEmitter.onNext(upFile)
                flowableEmitter.onComplete()
                return@FlowableOnSubscribe
            }
            val put = PutObjectRequest(mBucket, upFile.fileName, upFile.filePath)
            put.crC64 = OSSRequest.CRC64Config.YES
            put.progressCallback = OSSProgressCallback { request, currentSize, totalSize ->
                upFile.progress = (100 * currentSize / totalSize).toInt()
                upFile.upType = 1
                ULogger.i("progress = ${upFile.progress}")
                flowableEmitter.onNext(upFile)
            }
            mOss.asyncPutObject(put, object : OSSCompletedCallback<PutObjectRequest, PutObjectResult> {
                override fun onSuccess(request: PutObjectRequest, result: PutObjectResult?) {
                    upFile.upType = 2
                    ULogger.i("callbackAddress = $mCallbackAddress  mBucket = $mBucket")
                    upFile.upSuccessUrl = when (mBucketType) {
                        BucketType.BUCKET_CONFIT_TAG_PUBLIC -> mCallbackAddress.plus(request.objectKey)
                        BucketType.BUCKET_CONFIT_TAG_SECURITY -> mCallbackAddress.plus(request.objectKey).plus("-watermark")
                    }
                    flowableEmitter.onNext(upFile)
                    flowableEmitter.onComplete()
                }

                override fun onFailure(request: PutObjectRequest?, clientException: ClientException?, serviceException: ServiceException?) {
                    upFile.upType = 3
                    flowableEmitter.onNext(upFile)
                    flowableEmitter.onComplete()
                }
            })
        }, BackpressureStrategy.BUFFER)
                .handlerThread(schedulerProvider)
    }


    fun asyncPutFile(fileName: String, localFile: String, mDisplayer: UIDisplayer) {
        val upload_start = System.currentTimeMillis()
        OSSLog.logDebug("upload start")
        val file = File(localFile)
        if (!file.exists()) {
            ULogger.w("AsyncPutImage", "FileNotExist")
            ULogger.w("LocalFile", localFile)
            mDisplayer.uploadFail("文件不存在")
            return
        }
        // 构造上传请求
        OSSLog.logDebug("create PutObjectRequest ")
        val put = PutObjectRequest(mBucket, fileName, localFile)
        put.crC64 = OSSRequest.CRC64Config.YES

        // 异步上传时可以设置进度回调
        put.progressCallback = OSSProgressCallback { request, currentSize, totalSize ->
            ULogger.d("PutObject", "currentSize: $currentSize totalSize: $totalSize")
            val progress = (100 * currentSize / totalSize).toInt()
            mDisplayer.updateProgress(progress)
            mDisplayer.displayInfo("上传进度: $progress%")
        }
        OSSLog.logDebug(" asyncPutObject ")
        mOss.asyncPutObject(put, object : OSSCompletedCallback<PutObjectRequest, PutObjectResult> {
            override fun onSuccess(request: PutObjectRequest, result: PutObjectResult) {
                ULogger.d("PutObject", "UploadSuccess")

                ULogger.d("ETag", result.eTag)
                ULogger.d("RequestId", result.requestId)

                val upload_end = System.currentTimeMillis()
                OSSLog.logDebug("upload cost: " + (upload_end - upload_start) / 1000f)
                mDisplayer.uploadComplete()
                mDisplayer.displayInfo("Bucket: " + mBucket
                        + "\nObject: " + request.objectKey
                        + "\nETag: " + result.eTag
                        + "\nRequestId: " + result.requestId
                        + "\nCallback: " + result.serverCallbackReturnBody)
            }

            override fun onFailure(request: PutObjectRequest, clientExcepion: ClientException?, serviceException: ServiceException?) {
                var info = ""
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace()
                    info = clientExcepion.toString()
                }
                if (serviceException != null) {
                    // 服务异常
                    ULogger.e("ErrorCode", serviceException.errorCode)
                    ULogger.e("RequestId", serviceException.requestId)
                    ULogger.e("HostId", serviceException.hostId)
                    ULogger.e("RawMessage", serviceException.rawMessage)
                    info = serviceException.toString()
                }
                mDisplayer.uploadFail(info)
                mDisplayer.displayInfo(info)
            }
        })
    }

    // Downloads the files with specified prefix in the asynchronous way.
    fun asyncListObjectsWithBucketName(mDisplayer: UIDisplayer) {
        val listObjects = ListObjectsRequest(mBucket)
        // Sets the prefix
        listObjects.prefix = "android"
        listObjects.delimiter = "/"
        // Sets the success and failure callback. calls the Async API
        val task = mOss.asyncListObjects(listObjects, object : OSSCompletedCallback<ListObjectsRequest, ListObjectsResult> {
            override fun onSuccess(request: ListObjectsRequest, result: ListObjectsResult) {
                var info = ""
                OSSLog.logDebug("AyncListObjects", "Success!")
//                for (i in 0 until result.objectSummaries.size) {
//                    info += "\n" + String.format("object: %s %s %s", result.objectSummaries[i].key, result.objectSummaries[i].eTag, result.objectSummaries[i].lastModified.toString())
//                    OSSLog.logDebug("AyncListObjects", info)
//                }
                for (it in result.objectSummaries) {
                    info += "\n" + "${it.key}, ${it.eTag} ,${it.lastModified}"
                    OSSLog.logDebug("AyncListObjects", info)
                }
                mDisplayer.displayInfo(info)
            }

            override fun onFailure(request: ListObjectsRequest, clientExcepion: ClientException?, serviceException: ServiceException?) {
                // request exception
                clientExcepion?.printStackTrace()
                if (serviceException != null) {
                    // service side exception.
                    OSSLog.logError("ErrorCode", serviceException.errorCode)
                    OSSLog.logError("RequestId", serviceException.requestId)
                    OSSLog.logError("HostId", serviceException.hostId)
                    OSSLog.logError("RawMessage", serviceException.rawMessage)
                }
                mDisplayer.downloadFail("Failed!")
                mDisplayer.displayInfo(serviceException!!.toString())
            }
        })
    }

    // Gets file's metadata
    fun headObject(objectKey: String, mDisplayer: UIDisplayer) {
        // Creates a request to get the file's metadata
        val head = HeadObjectRequest(mBucket, objectKey)

        val task = mOss.asyncHeadObject(head, object : OSSCompletedCallback<HeadObjectRequest, HeadObjectResult> {
            override fun onSuccess(request: HeadObjectRequest, result: HeadObjectResult) {
                OSSLog.logDebug("headObject", "object Size: " + result.metadata.contentLength)
                OSSLog.logDebug("headObject", "object Content Type: " + result.metadata.contentType)

                mDisplayer.displayInfo(result.toString())
            }

            override fun onFailure(request: HeadObjectRequest, clientExcepion: ClientException?, serviceException: ServiceException?) {
                // request exception
                clientExcepion?.printStackTrace()
                if (serviceException != null) {
                    // service side exception
                    OSSLog.logError("ErrorCode", serviceException.errorCode)
                    OSSLog.logError("RequestId", serviceException.requestId)
                    OSSLog.logError("HostId", serviceException.hostId)
                    OSSLog.logError("RawMessage", serviceException.rawMessage)
                }
                mDisplayer.downloadFail("Failed!")
                mDisplayer.displayInfo(serviceException!!.toString())
            }
        })
    }

    fun asyncMultipartUpload(uploadKey: String, uploadFilePath: String, mDisplayer: UIDisplayer) {
        val request = MMultipartUploadRequest(mBucket, uploadKey,
                uploadFilePath)
        request.setCRC64(OSSRequest.CRC64Config.YES)
        request.setProgressCallback(OSSProgressCallback<MMultipartUploadRequest> { request, currentSize, totalSize -> OSSLog.logDebug("[testMultipartUpload] - $currentSize $totalSize", false) })
        mOss.asyncMultipartUpload(request, object : OSSCompletedCallback<MultipartUploadRequest<*>, CompleteMultipartUploadResult> {
            override fun onSuccess(request: MultipartUploadRequest<*>, result: CompleteMultipartUploadResult) {
                mDisplayer.uploadComplete()
                mDisplayer.displayInfo(request.toString())
            }

            override fun onFailure(request: MultipartUploadRequest<*>, clientException: ClientException?, serviceException: ServiceException?) {
                if (clientException != null) {
                    mDisplayer.displayInfo(clientException.toString())
                } else if (serviceException != null) {
                    mDisplayer.displayInfo(serviceException.toString())
                }

            }
        })
    }

    fun asyncResumableUpload(resumableFilePath: String, mDisplayer: UIDisplayer) {
        val request = ResumableUploadRequest(mBucket, mResumableObjectKey, resumableFilePath)
        request.progressCallback = OSSProgressCallback<ResumableUploadRequest> { request, currentSize, totalSize ->
            ULogger.d("GetObject", "currentSize: $currentSize totalSize: $totalSize")
            val progress = (100 * currentSize / totalSize).toInt()
            mDisplayer.updateProgress(progress)
            mDisplayer.displayInfo("上传进度: $progress%")
        }
        val task = mOss.asyncResumableUpload(request, object : OSSCompletedCallback<ResumableUploadRequest, ResumableUploadResult> {
            override fun onSuccess(request: ResumableUploadRequest, result: ResumableUploadResult) {
                mDisplayer.uploadComplete()
                mDisplayer.displayInfo(request.toString())
            }

            override fun onFailure(request: ResumableUploadRequest, clientException: ClientException?, serviceException: ServiceException?) {
                if (clientException != null) {
                    mDisplayer.displayInfo(clientException.toString())
                } else if (serviceException != null) {
                    mDisplayer.displayInfo(serviceException.toString())
                }
            }
        })
    }

    // If the bucket is private, the signed URL is required for the access.
    // Expiration time is specified in the signed URL.
    fun presignURLWithBucketAndKey(objectKey: String?, mDisplayer: UIDisplayer) {
        if (objectKey == null || objectKey === "") {
            mDisplayer.displayInfo("Please input objectKey!")
            return
        }
        Thread(Runnable {
            try {
                // Gets the signed url, the expiration time is 5 minute
                val url = mOss.presignConstrainedObjectURL(mBucket, objectKey, (5 * 60).toLong())
                OSSLog.logDebug("signContrainedURL", "get url: $url")
                // 访问该url
                val request = Request.Builder().url(url).build()
                var resp: Response? = null

                resp = OkHttpClient().newCall(request).execute()

                if (resp!!.code() == 200) {
                    OSSLog.logDebug("signContrainedURL", "object size: " + resp.body()!!.contentLength())
                    mDisplayer.displayInfo(resp.toString())
                } else {
                    OSSLog.logDebug("signContrainedURL", "get object failed, error code: " + resp.code()
                            + "error message: " + resp.message())
                    mDisplayer.displayInfo(resp.toString())
                }
            } catch (e: IOException) {
                e.printStackTrace()
                mDisplayer.displayInfo(e.toString())
            } catch (e: ClientException) {
                e.printStackTrace()
                mDisplayer.displayInfo(e.toString())
            }
        }).start()
    }

    /**
     * Delete a non-empty bucket.
     * Create a bucket, and add files into it.
     * Try to delete the bucket and failure is expected.
     * Then delete file and then delete bucket
     */
    fun deleteNotEmptyBucket(bucket: String, filePath: String, mDisplayer: UIDisplayer) {
        val createBucketRequest = CreateBucketRequest(bucket)
        // 创建bucket
        try {
            mOss.createBucket(createBucketRequest)
        } catch (clientException: ClientException) {
            clientException.printStackTrace()
            mDisplayer.displayInfo(clientException.toString())
        } catch (serviceException: ServiceException) {
            serviceException.printStackTrace()
            mDisplayer.displayInfo(serviceException.toString())
        }

        val putObjectRequest = PutObjectRequest(bucket, "test-file", filePath)
        try {
            mOss.putObject(putObjectRequest)
        } catch (clientException: ClientException) {
            clientException.printStackTrace()
        } catch (serviceException: ServiceException) {
            serviceException.printStackTrace()
        }

        val deleteBucketRequest = DeleteBucketRequest(bucket)
        val deleteBucketTask = mOss.asyncDeleteBucket(deleteBucketRequest, object : OSSCompletedCallback<DeleteBucketRequest, DeleteBucketResult> {
            override fun onSuccess(request: DeleteBucketRequest, result: DeleteBucketResult) {
                OSSLog.logDebug("DeleteBucket", "Success!")
                mDisplayer.displayInfo(result.toString())
            }

            override fun onFailure(request: DeleteBucketRequest, clientException: ClientException?, serviceException: ServiceException?) {
                // request exception
                if (clientException != null) {
                    // client side exception,  such as network exception
                    clientException.printStackTrace()
                    mDisplayer.displayInfo(clientException.toString())
                }
                if (serviceException != null) {
                    // The bucket to delete is not empty.
                    if (serviceException.statusCode == 409) {
                        // Delete files
                        val deleteObjectRequest = DeleteObjectRequest(bucket, "test-file")
                        try {
                            mOss.deleteObject(deleteObjectRequest)
                        } catch (clientexception: ClientException) {
                            clientexception.printStackTrace()
                        } catch (serviceexception: ServiceException) {
                            serviceexception.printStackTrace()
                        }

                        // Delete bucket again
                        val deleteBucketRequest1 = DeleteBucketRequest(bucket)
                        try {
                            mOss.deleteBucket(deleteBucketRequest1)
                        } catch (clientexception: ClientException) {
                            clientexception.printStackTrace()
                            mDisplayer.displayInfo(clientexception.toString())
                            return
                        } catch (serviceexception: ServiceException) {
                            serviceexception.printStackTrace()
                            mDisplayer.displayInfo(serviceexception.toString())
                            return
                        }

                        OSSLog.logDebug("DeleteBucket", "Success!")
                        mDisplayer.displayInfo("The Operation of Deleting Bucket is successed!")
                    }
                }
            }
        })
    }

    fun customSign(ctx: Context, objectKey: String, mDisplayer: UIDisplayer) {
        val provider = object : OSSCustomSignerCredentialProvider() {
            override fun signContent(content: String): String {

                // 此处本应该是客户端将contentString发送到自己的业务服务器,然后由业务服务器返回签名后的content。关于在业务服务器实现签名算法
                // 详情请查看http://help.aliyun.com/document_detail/oss/api-reference/access-control/signature-header.html。客户端
                // 的签名算法实现请参考OSSUtils.sign(accessKey,screctKey,content)

//                return OSSUtils.sign(Config.OSS_ACCESS_KEY_ID, Config.OSS_ACCESS_KEY_SECRET, content)
                return ""
            }
        }

        val get = GetObjectRequest(mBucket, objectKey)
        get.crC64 = OSSRequest.CRC64Config.YES
        get.setProgressListener { request, currentSize, totalSize ->
            ULogger.d("GetObject", "currentSize: $currentSize totalSize: $totalSize")
            val progress = (100 * currentSize / totalSize).toInt()
            mDisplayer.updateProgress(progress)
            mDisplayer.displayInfo("下载进度: $progress%")
        }
        val task = mOss.asyncGetObject(get, object : OSSCompletedCallback<GetObjectRequest, GetObjectResult> {
            override fun onSuccess(request: GetObjectRequest, result: GetObjectResult) {
                mDisplayer.displayInfo("使用自签名获取网络对象成功！")
            }

            override fun onFailure(request: GetObjectRequest, clientException: ClientException?, serviceException: ServiceException?) {
                if (clientException != null) {
                    mDisplayer.displayInfo(clientException.toString())
                } else if (serviceException != null) {
                    mDisplayer.displayInfo(serviceException.toString())
                }
            }
        })
    }

    fun triggerCallback(ctx: Context, endpoint: String, mDisplayer: UIDisplayer) {
        val provider = OSSPlainTextAKSKCredentialProvider("AK", "SK")
        val tClient = OSSClient(ctx, endpoint, provider)

        val callbackParams = object : HashMap<String, String>() {
            init {
                put("callbackURL", "callbackURL")
                put("callbackBody", "callbackBody")
            }
        }

        val callbackVars = object : HashMap<String, String>() {
            init {
                put("key1", "key1")
                put("key2", "key2")
            }
        }

        val request = TriggerCallbackRequest("bucketName", "objectKey", callbackParams, callbackVars)

        val task = tClient.asyncTriggerCallback(request, object : OSSCompletedCallback<TriggerCallbackRequest, TriggerCallbackResult> {
            override fun onSuccess(request: TriggerCallbackRequest, result: TriggerCallbackResult) {
                mDisplayer.displayInfo(result.serverCallbackReturnBody)
            }

            override fun onFailure(request: TriggerCallbackRequest, clientException: ClientException?, serviceException: ServiceException?) {
                if (clientException != null) {
                    mDisplayer.displayInfo(clientException.toString())
                } else if (serviceException != null) {
                    mDisplayer.displayInfo(serviceException.toString())
                }
            }
        })

    }

    fun imagePersist(fromBucket: String, fromObjectKey: String, toBucket: String, toObjectkey: String, action: String, mDisplayer: UIDisplayer) {

        val request = ImagePersistRequest(fromBucket, fromObjectKey, toBucket, toObjectkey, action)

        val task = mOss.asyncImagePersist(request, object : OSSCompletedCallback<ImagePersistRequest, ImagePersistResult> {
            override fun onSuccess(request: ImagePersistRequest, result: ImagePersistResult) {
                //                mDisplayer.displayInfo(result.getServerCallbackReturnBody());
            }

            override fun onFailure(request: ImagePersistRequest, clientException: ClientException?, serviceException: ServiceException?) {
                if (clientException != null) {
                    mDisplayer.displayInfo(clientException.toString())
                } else if (serviceException != null) {
                    mDisplayer.displayInfo(serviceException.toString())
                }
            }
        })
    }
}

