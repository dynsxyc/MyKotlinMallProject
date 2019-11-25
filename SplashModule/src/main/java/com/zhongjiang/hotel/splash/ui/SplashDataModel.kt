import com.zhongjiang.hotel.base.ext.convert
import com.zhongjiang.hotel.base.ext.convertList
import com.zhongjiang.hotel.base.ui.basemvp.BaseModel
import com.zhongjiang.hotel.provider.db.SplashAdEntity
import com.zhongjiang.hotel.provider.db.UserInfoEntity
import io.reactivex.Maybe
import io.rx_cache2.EvictProvider
import io.rx_cache2.Reply
import javax.inject.Inject


class SplashDataModel @Inject constructor(): BaseModel<SplashServiceManager>(), ISplashModel {
    override fun requestLogin(code:String,phoneStr: String, verificationCode: String): Maybe<UserInfoEntity> {
        return serviceManager.splashService.userLogin(code,verificationCode,phoneStr).convert().handlerThread(schedulers)
    }

    override fun requestVerificationCode(phoneStr: String): Maybe<VerificationCodeResuleInfo> {
        return serviceManager.splashService.loginGetVerificationCode(phoneStr).convert().handlerThread(schedulers)
    }

    @Inject
    lateinit var splashModuleRxCacheProviders: SplashModuleRxCacheProviders;
    override fun requestAdInfo(name: String,update: Boolean): Maybe<Reply<List<SplashAdEntity>>> {
        return splashModuleRxCacheProviders.getAdInfo(serviceManager.splashService.loadAd().convertList().handlerThread(schedulers), EvictProvider(update))
    }
}