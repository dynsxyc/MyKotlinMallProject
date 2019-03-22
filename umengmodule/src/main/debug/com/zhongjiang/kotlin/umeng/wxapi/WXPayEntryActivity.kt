package main.debug.com.zhongjiang.kotlin.umeng.wxapi


import android.app.Activity
import android.content.Intent
import android.os.Bundle

import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler


class WXPayEntryActivity : Activity(), IWXAPIEventHandler {

    private val api: IWXAPI? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //    	api = WXAPIFactory.createWXAPI(this, StaticConstant.WX_APP_ID);
        api!!.handleIntent(intent, this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        api!!.handleIntent(intent, this)
    }

    override fun onReq(req: BaseReq) {}

    override fun onResp(resp: BaseResp) {
        //		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
        //			Intent intent=new Intent(StaticConstant.BROADCASTRECEIVER_TAG_WEIXINPAY);
        //			//0 成功 -1 错误 -2 用户取消
        //			if(resp.errCode==0){
        //				intent.putExtra(IntentExtra.DATA, 0);
        //			}else {
        //				if (resp.errCode == -2){
        //					Toast.makeText(this, R.string.usercancelpay, Toast.LENGTH_SHORT)
        //							.show();
        //				}
        //				intent.putExtra(IntentExtra.DATA, -1);
        //			}
        //			sendBroadcast(intent);
        //			finish();
    }
}