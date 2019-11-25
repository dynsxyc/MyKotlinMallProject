import com.zhongjiang.hotel.base.common.BaseApplication
import com.zhongjiang.hotel.base.injection.module.AppModule
import com.zhongjiang.hotel.base.injection.module.CacheModule
import com.zhongjiang.hotel.base.injection.module.GlobalConfigModule
import com.zhongjiang.hotel.base.injection.module.HttpClientModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by dyn on 2018/7/16.
 */
@Singleton
@Component(modules = [AndroidInjectionModule ::class,
    AndroidSupportInjectionModule::class,
    GlobalConfigModule::class,
    AppModule::class,
    HttpClientModule::class,
    CacheModule::class,
    SplashInjectionModule::class])
interface SplashModuleComponent {
    fun inject(baseApplication: BaseApplication)
}