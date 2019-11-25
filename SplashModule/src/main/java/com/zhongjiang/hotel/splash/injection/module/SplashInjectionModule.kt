
import com.zhongjiang.hotel.base.injection.component.BaseActivityComponent
import com.zhongjiang.hotel.base.injection.component.BaseFragmentComponent
import com.zhongjiang.hotel.base.injection.scope.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by dyn on 2018/7/16.
 */
@Module(subcomponents = [BaseFragmentComponent::class, BaseActivityComponent::class],includes = [SplashServiceModule::class])
abstract class SplashInjectionModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [SplashViewModule::class])
    abstract fun contributeSplashFragmentInjector(): SplashFragment

    @ActivityScope
    @ContributesAndroidInjector(modules = [SplashViewModule::class])
    abstract fun contributeLoginFragmentInjector(): LoginFragment

    @ActivityScope
    @ContributesAndroidInjector(modules = [SplashViewModule::class])
    abstract fun contributeTestPictureFragmentInjector(): TestPictureFragment

    @ActivityScope
    @ContributesAndroidInjector(modules = [SplashViewModule::class])
    abstract fun contributeSplashActivityInjector(): SplashActivity

}