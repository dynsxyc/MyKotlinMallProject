import com.zhongjiang.hotel.base.data.net.service.BaseServiceManager
import com.zhongjiang.hotel.base.injection.scope.ActivityScope
import javax.inject.Inject

@ActivityScope
class SplashServiceManager @Inject constructor(val splashService: SplashService): BaseServiceManager {
    override fun destroy() {

    }
}