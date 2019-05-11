package com.zhongjiang.youxuan.base.rx

import android.util.Log
import com.google.gson.JsonParseException
import io.reactivex.exceptions.CompositeException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import javax.net.ssl.SSLHandshakeException

class ExceptionHandle {
    companion object {
        val  UNAUTHORIZED = 401
         val  FORBIDDEN = 403
         val  NOT_FOUND = 404
         val  REQUEST_TIMEOUT = 408
         val  INTERNAL_SERVER_ERROR = 500
         val  BAD_GATEWAY = 502
         val  SERVICE_UNAVAILABLE = 503
         val  GATEWAY_TIMEOUT = 504

        fun  handleException( roote : Throwable) :ResponeThrowable{
             var ex:ResponeThrowable
            Log.i("tag", "e.toString = " + roote.toString());
            var e : Throwable;
            if(roote is CompositeException) {
                e = roote.exceptions[0]
            }else{
                e = roote;
            }

            if (e is HttpException) {
                ex = ResponeThrowable(e, ERROR.HTTP_ERROR,"网络错误");
                return ex;
            }else if (e is JsonParseException
                    || e is JSONException
            /*|| e is ParseException*/) {
                ex = ResponeThrowable(e, ERROR.PARSE_ERROR,"解析错误")
                return ex;
            } else if (e is ConnectException) {
                ex =  ResponeThrowable(e, ERROR.NETWORD_ERROR,"连接失败")
                return ex;
            } else if (e is SSLHandshakeException) {
                ex =  ResponeThrowable(e, ERROR.SSL_ERROR,"证书验证失败");
                return ex;
            } else if (e is ResponeThrowable) {
                return e;
            } else {
                ex =  ResponeThrowable(e, ERROR.UNKNOWN,"未知错误");
                return ex;
            }
        }
    }




    /**
     * 约定异常
     */
    open class ERROR {
        companion object {

            /**
             * 未知错误
             */
            open val UNKNOWN = 1000;
            /**
             * 解析错误
             */
            open val PARSE_ERROR = 1001;
            /**
             * 网络错误
             */
            open val NETWORD_ERROR = 1002;
            /**
             * 协议出错
             */
            open val HTTP_ERROR = 1003;

            /**
             * 证书出错
             */
            open val SSL_ERROR = 1005
            /**
             * 业务错误
             */
            open val PROFESSIONAL_ERROR = 1006

        }
    }




}