import com.zhongjiang.hotel.provider.db.SplashAdEntity
import com.zhongjiang.hotel.provider.db.UserInfoEntity
import io.reactivex.Maybe
import io.rx_cache2.Reply

/**
 * @author dyn
 * @date on 2019/11/5  12:03
 * @packagename com.zhongjiang.youxuan.user.splash.ui.fragment
 * @fileName ISplashModel
 * @org com.zhongjiang.youxuan
 * @describe 添加描述
 * @email 583454199@qq.com
 **/
interface ISplashModel{
        /**请求登录*/
        fun requestLogin(code:String,phoneStr:String,verificationCode:String): Maybe<UserInfoEntity>
        /**获取验证码*/
        fun requestVerificationCode(phoneStr:String): Maybe<VerificationCodeResuleInfo>
        fun requestAdInfo(name: String,update: Boolean): Maybe<Reply<List<SplashAdEntity>>>
}