import com.zhongjiang.hotel.base.common.YouXuanFieldConstant.Companion.API_DATA_FIELD_CODE
import com.zhongjiang.hotel.base.common.YouXuanFieldConstant.Companion.API_DATA_FIELD_REGCODE
import com.zhongjiang.hotel.base.common.YouXuanFieldConstant.Companion.API_DATA_FIELD_USERNAME
import com.zhongjiang.hotel.base.data.protocol.BaseList
import com.zhongjiang.hotel.base.data.protocol.PageReq
import com.zhongjiang.hotel.provider.db.SplashAdEntity
import com.zhongjiang.hotel.provider.db.UserInfoEntity
import com.zhongjiang.hotel.splash.service.protocol.AppStartStatisticsReq
import io.reactivex.Maybe
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by dyn on 2018/7/16.
 */
interface SplashService {
    /**启动页广告*/
    @POST("app/qt/appStartPage/queryList")
    fun loadAd(@Body pageReq: PageReq = PageReq(1,1)): Maybe<BaseResp<BaseList<List<SplashAdEntity>>>>

    /**APP打开次数*/
    @POST("app/other/openApp/insertOpenNumber")
    fun appOpenStatistics(@Body appStartStatisticsReq: AppStartStatisticsReq): Maybe<BaseResp<String>>

    @POST("app/appUser/user/sendRegCode")
    fun loginGetVerificationCode(@Query(API_DATA_FIELD_USERNAME) phoneNumber:String):Maybe<BaseResp<VerificationCodeResuleInfo>>

    @POST("app/appUser/user/loginRegCode")
    fun userLogin(@Query(API_DATA_FIELD_CODE) code:String, @Query(API_DATA_FIELD_REGCODE) regCode:String, @Query(API_DATA_FIELD_USERNAME) phoneNumber:String):Maybe<BaseResp<UserInfoEntity>>

}