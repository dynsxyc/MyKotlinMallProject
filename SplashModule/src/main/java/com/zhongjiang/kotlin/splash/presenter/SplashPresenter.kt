package com.zhongjiang.kotlin.splash.presenter

import android.util.Log
import com.zhongjiang.kotlin.base.data.db.UserDb
import com.zhongjiang.kotlin.base.presenter.BasePresenter
import com.zhongjiang.kotlin.base.rx.BaseMaybeObserver
import com.zhongjiang.kotlin.splash.data.SplashAdBean
import com.zhongjiang.kotlin.splash.presenter.contract.SplashContract
import io.objectbox.BoxStore
import javax.inject.Inject

/**
 * Created by dyn on 2018/7/25.
 */
class SplashPresenter @Inject constructor(view: SplashContract.View,model: SplashContract.Model) : BasePresenter<SplashContract.View,SplashContract.Model>(view,model),SplashContract.Presenter {

    @Inject
    lateinit var boxStore:BoxStore
    override fun requestUserInfo(name: String) {
        mModel.requestUserInfo(name).`as`(bindLifecycle()).subscribe(object :BaseMaybeObserver<List<SplashAdBean>>(mView){
            override fun onSuccess(t: List<SplashAdBean>) {
                mView.onGetUserInfo(t.size.toString())
            }
        })
        var box = boxStore.boxFor(UserDb::class.java)
        box.put(UserDb(1,"你好呀"))
        box.put(UserDb(2,"我很好"))
        var first = box.get(1)
        first.name = "666"
        box.put(first)

        var list = box.all
        if (!list.isEmpty()){
            list.forEach {
                Log.i("test1","all == "+it.name+"------"+it.id)
            }
        }
        var list1 = box.all2
        if (!list1.isEmpty()){
            list1.forEach {
                Log.i("test1","all2 == "+it.name+"------"+it.id)
            }
        }
    }

}