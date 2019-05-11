package com.zhongjiang.youxuan.base.oss

import com.alibaba.sdk.android.oss.model.MultipartUploadRequest

class MMultipartUploadRequest(bucketName: String, objectKey: String, uploadFilePath: String) : MultipartUploadRequest<MMultipartUploadRequest>(bucketName,objectKey,uploadFilePath) {
}