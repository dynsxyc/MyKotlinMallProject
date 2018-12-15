package mall.kotlin.zhongjiang.com.testapp

import dagger.Component

@Component(modules = arrayOf(CarModule::class))
interface CarComponent {
    fun injectCar(mainActivity: MainActivity)
}