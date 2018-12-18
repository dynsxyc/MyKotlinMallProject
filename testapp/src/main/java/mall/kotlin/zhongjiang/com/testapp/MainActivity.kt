package mall.kotlin.zhongjiang.com.testapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var car:Car;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DaggerCarComponent.builder().build().injectCar(this)
    }

    override fun onResume() {
        super.onResume()
        car.hello()
    }
}
