package tsou.uxuan.user.wxapi

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.zhongjiang.kotlin.splash.R


class WXPayEntryActivity : Activity(), IWXAPIEventHandler {

    private var api: IWXAPI? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pay_result)

        api = WXAPIFactory.createWXAPI(this, "wx72a9d8637eb96ea1")
        api!!.handleIntent(intent, this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        api!!.handleIntent(intent, this)
    }

    override fun onReq(req: BaseReq) {}

    override fun onResp(resp: BaseResp) {
        Log.d("tag", "onPayFinish, errCode = " + resp.errCode)

        if (resp.getType() === ConstantsAPI.COMMAND_PAY_BY_WX) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("支付结果")
            builder.setMessage("状态码 = "+resp.errCode)
            builder.show()
            finish()
        }
    }

}