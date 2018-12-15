package mall.kotlin.zhongjiang.com.testapp

import android.util.Log
import javax.inject.Inject

class Car @Inject constructor() {
    fun hello(){
        Log.i("hello","我去你大爷")
        print("hello-----------------------------------------------")
    }
}