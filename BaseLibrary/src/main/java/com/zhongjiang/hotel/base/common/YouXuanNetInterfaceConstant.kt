package com.zhongjiang.hotel.base.common

/**
 * Created by dyn on 2018/7/25.
 */
interface YouXuanNetInterfaceConstant {
    companion object {

        val BASE_URL_DEVELOP_TEST = "http://devapi.you-x.cn/"

        /**
         * APP打开次数
         */
        const val API_METHOD_APP_INSERT_OPEN_NUMBER = "app/other/openApp/insertOpenNumber"
        /**
         * 启动页广告
         */
        const val API_METHOD_APP_APLASH_AD = "app/qt/appStartPage/queryList"
        /***********************************社区模块* start ******************************************************/
        /**
         * 首页文章列表
         */
        const val API_METHOD_COMMUNITY_HOMEPAGE_LIST = "app/community/notice/selectPage" //"app/community/article/selectHomeArticleListByPage";

        /**
         * 首页查看用户权限状态和 留言未读数
         */
        const val API_METHOD_COMMUNITY_HOME_USERTYPE_AND_COMMENTNUMBER = "app/community/comment/selectCommentUnreadNumber"

        /**
         * 文章内容管理列表
         */
        const val API_METHOD_COMMUNITY_ARTICLE_CONTENTMANAGER_LIST = "app/community/article/selectArticleListByPage"
        /**
         * 发布文章接口
         * content	正文内容	string
         * homePicture	首页大图	string
         * keywords	关键字	string	可以有多个，用逗号分隔
         * shopAddress	店铺详细地址	string
         * shopCity	店铺所在城市	string
         * shopId	店铺ID	number
         * shopImg	店铺图片	string
         * shopName	店铺名称	string
         * shopPhone	店铺联系电话	string
         * shopScore	星级评分	number
         * shopType	店铺类型	number	1：原有的店铺，2：新的店铺
         * subtitle	副标题	string
         * title	标题	string
         */
        val API_METHOD_COMMUNITY_SENDARTICLE_INSERT = "app/community/article/insertArticleDetail"
        val API_METHOD_COMMUNITY_SENDARTICLE_UPDATE = "app/community/article/updateIntoCommunityArticle"
        /**
         * 文章分享统计接口
         * articleId	文章ID	number
         * shareType	分享路径，1.微信好友 2.微信朋友圈 3.QQ好友 4.QQ动态 5.微博 6.其他	number	不传 或6为其他
         */
        val API_METHOD_COMMUNITY_SHAREARTICLE_STATISTICS = "app/community/articleRecord/shareArticle"
        /**
         * 文章浏览统计接口
         * articleId	文章ID	number
         * osType	平台类型，android，ios，H5	string	限制10字
         */
        val API_METHOD_COMMUNITY_REDARTICLE_STATISTICS = "app/community/articleRecord/browseArticle"
        /**
         * 文章点赞
         * articleId	文章ID
         */
        val API_METHOD_COMMUNITY_LIKEARTICLE = "app/community/articleRecord/likeArticle"
        /**
         * 文章审核不通过获取最新一条审核记录
         * articleId	文章ID
         */
        val API_METHOD_COMMUNITY_RECENTLY_ARTICLE_AUDITRECORD = "app/community/articleAudit/selectRecentlyArticleAuditRecord"
        /**
         * 文章详情留言列表
         * articleId	文章ID
         * page
         * pageSize
         */
        val API_METHOD_COMMUNITY_ARTICLEDETAIL_COMMENT_LIST = "app/community/comment/selectArticleDetailCommentByPage"
        /**
         * 查看文章详情
         * articleId	文章ID
         */
        val API_METHOD_COMMUNITY_ARTICLEDETAIL = "app/community/article/selectArticleDetail"
        /**
         * 文章添加留言
         * articleId	文章ID	number
         * comments	留言内容
         */
        val API_METHOD_COMMUNITY_ARTICLE_ADD_COMMENT = "app/community/comment/insertComment"
        /**
         * 留言精选列表
         */
        val API_METHOD_COMMUNITY_COMMENT_MANAGER_LIST = "app/community/comment/selectCommentByPage"
        /**
         * 插入店铺 搜索列表
         * shopName 模糊查询
         */
        val API_METHOD_COMMUNITY_ARTICLE_SHOP_SEARCH_LIST = "server/shop/selectShopListByNameOrId"
        /**
         * 设定/取消精选留言
         * commentId	留言ID
         */
        val API_METHOD_COMMUNITY_UPDATE_COMMENT_GOOD_STATUS = "app/community/comment/updateCommentStatus"
        /**
         * 根据地区 获取社区显示状态
         */
        val API_METHOD_COMMUNITY_AREA_STATUS = "app/community/article/selectArticleListByAreaCode"
        /**
         * 查询市列表
         */
        val API_METHOD_SELECT_SYS_CITY_LIST = "app/other/sign/selectSysCityList"

        /**
         * 根据市code  校验市是否开通
         */
        val API_METHOD_CHECK_CITY_ISOPEN = "app/other/sign/checkCityIsOpen"
        /**
         * 根据市经纬度  查询首页地区位置
         */
        val API_METHOD_CHECK_GPS_TO_AREA = "app/other/sign/getCurrentAddressCity"
        /**
         * 报名
         */
        val API_METHOD_SIGN_APPLY = "app/other/sign/insertSignApply"
        /**
         * 添加意见反馈
         */
        val API_METHOD_INSERT_FEEDBACK = "app/other/SelectFeedback/insertQuestionContent"
        /**
         * 意见反馈列表
         */
        val API_METHOD_FEEDBACK_CONTENT_LIST = "app/other/SelectFeedback/selectFeedbackById"
        /**
         * 附近已开通城市列表
         */
        val API_METHOD_NEARBY_CITY_LIST = "app/other/sign/getNearbyCityList"
        /**
         * 包名成功详情
         */
        val API_METHOD_GET_CITYSIGNAPPLY_INFO = "app/other/sign/getCitySignApplyInfo"
        /**
         * 登录获取验证码
         */
        const val API_METHOD_LOGINSENDREGCODE = "app/appUser/user/sendRegCode"
        /**
         * 验证码登录
         */
        const val API_METHOD_LOGIN_REGCODE = "app/appUser/user/loginRegCode"
        /**
         * 查看角色详情
         */
        val API_METHOD_FEEDBACK_GET_RELO_INFO = "app/other/SelectFeedback/selectUserType"
        /**
         * 绑定用户邀请码
         */
        val API_METHOD_USERINVITED_CODE = "app/appUser/user/updateUserInvited"
        /***********************************社区模块* end ******************************************************/

        /********************************2.6版本******************************************************************/
        /**
         * 订单列表
         */
        val API_METHOS_ORDER_LIST = "app/order/orderUserController/findUserOrderList"

        /**
         * 清空购物车操作
         */
        val API_METHOD_APP_CLEAR_CART = "app/shoppingCart/shoppingCartController/cleanTbShoppingCart"

        /**
         * 插入、移除 购物车操作
         */
        val API_METHOD_APP_INSERT_OR_REMOVE_INCART = "app/shoppingCart/shoppingCartController/updateShoppingCart"
        /**
         * 定向下单页面初始化
         */
        val API_METHOD_DX_INIT_ORDERPAY = "app/order/orderUserController/intiOrderPay"
        /**
         * 定向下单功能
         */
        val API_METHOD_DX_DOWNORDER = "app/order/orderUserController/aplanDirectionalOrder"//"app/order/orderUserController/directionalOrder"3.3;
        /**
         * 发布预约单
         * OK
         */
        val API_METHOD_SEND_DEMAND_ORDER = "app/order/BespeakAppUserController/bespeakOrder"
        /**
         * 删除预约单
         * bespeakId
         */
        val API_METHOD_DELETE_DEMAND_ORDER = "app/order/BespeakAppUserController/delBespeak"
        /**
         * 取消预约单
         * bespeakId
         */
        val API_METHOD_CANCEL_DEMAND_ORDER = "app/order/BespeakAppUserController/cancelBespeak"
        /**
         * 预约单详情
         */
        val API_METHOD_DEMAND_ORDER_DETAIL = "app/order/BespeakAppUserController/getBespeakInfoUser"
        //退款
        /**
         * 取消退款申请
         * orderId
         */
        val API_METHOD_CANCEL_REFUND_ORDER = "app/order/refund/orderRefundeCancel"
        /**
         * 用户取消订单
         */
        val API_METHOD_CANCEL_ORDER = "app/order/refund/orderCancelByUser"
        /**
         * 用户申请退款
         */
        val API_METHOD_APPLY_REFUND = "app/order/refund/orderRefundApply"
        /**
         * 申请客服介入
         */
        val API_METHOD_APPLY_HELP = "app/order/refund/applyHelp"
        /**
         * 定向单详情
         */
        val API_METHOD_DX_ORDER_DETAIL = "app/order/orderUserController/orderDetail"
        /**
         * 定向下单选择技师
         */
        val API_METHOD_DX_DOWNORDER_SELECT_ARTIFICER = "app/order/orderUserController/findOrderArtificerListByShop"
        /**
         * 预约单选择技师
         */
        val API_METHOD_DEMAND_SELECT_ARTIFCER_DETAIL = "app/order/BespeakAppUserController/getBespeakOfferInfoUser"
        /**
         * 支付页面初始化
         */
        val API_METHOD_ORDER_PAY_INIT = "app/order/orderUserController/orderToPayInit"
        /**
         * 确认消费
         */
        val API_METHOD_ORDER_CONFIRMCLOS = "app/order/orderUserController/orderConfirmClose"
        /**
         * 订单支付
         */
        val API_METHOD_ORDER_PAY = "app/order/orderUserController/orderPay"
        /**
         * 选择技师
         */
        val API_METHOD_DEMAND_SELECT_ARTIFICER = "app/order/BespeakAppUserController/choiceArtificerByDemandId"
        /**
         * 技师详情
         */
        val API_METHOD_ARTIFICER_DETAIL = "app/qt/artificer/getArtificerDetailOnAccount"
        /**
         * 技师评价列表
         */
        val API_METHOD_ARTIFICER_DETAIL_COMMENT_LIST = "app/qt/artificer/getArtificerCommentListQuery"
        /**
         * 我的收藏 店铺
         */
        val API_METHOD_MY_COLLECT_SHOP = "app/qt/collect/newMyCollects"
        /**
         * 我的收藏 服务
         */
        val API_METHOD_MY_COLLECT_SERVER = "app/qt/collect/myCollectsByServer"
        /**
         * 服务搜索
         */
        val API_METHOD_GLOBAL_SEARCH = "app/elasticsearch/shopAndService"
        /**
         * 店铺搜索
         */
        val API_METHOD_SEARCH_SHOP = "app/qt/firstPageShop/listQueryNew"
        /**
         * 店铺优惠券
         */
        val API_METHOD_SHOP_COUPON = "app/qt/activity/queryList"
        /**
         * 我的邀请
         */
        val API_METHOD_MY_INVITE = "app/user/2.0/getMyInviteCode"
        /**
         * 提现账户列表
         */
        val API_METHOD_CARD_LIST = "app/account/1.0/listUserCardBinding"
        /**
         * 我的评论
         */
        val API_METHOD_MY_COMMENT_LIST = "app/qt/qtOrder/orderMyEvaluate"
        /**
         * 全品类列表
         */
        val API_METHOD_ALL_TRADE_SERVPROJ = "app/qt/servproj/treeTradeServprojAndRecommend"
        /**
         * 查看店铺服务列表
         */
        val API_METHOD_SHOP_ALL_SERVERLIST = "app/qt/servproj/getServerForShopNew"

        /**
         * 一些配置
         * 譬如客服电话  app_custom_service_phone
         */
        val API_METHOD_GETCONFIGBYKEY = "app/appcenter/getConfigByKey"
        /**
         * 钱包  账户信息
         */
        val API_METHOD_ACCOUNT_BLANCE_INFO = "app/ucoin/1.0/myUcoinAndAccount"
        /**
         * 消息分组列表
         */
        val API_METHOD_GET_MESSAGE_TAB = "app/qt/msg/business/group/getApiItemsByUers"
        /**
         * 分组  消息列表
         */
        val API_METHOD_GET_MESSAGE_LIST = "app/qt/msg/business/content/list"
        /**
         * 获取融云Token
         */
        val API_METHOD_GET_RONG_TOKEN = "app/im/appUserIm/getAppUserToken"
        /**
         * 获取商户信息
         */
        val API_METHOD_GET_RONG_SHOPUSERINFO = "app/im/appUserIm/getShopRecord"
        /**
         * 用户优惠信息列表
         */
        val API_METHOD_REDENVELOPELIST = "app/doubleHundred/coupon/listUserCoupon" //"app/activitys/coupontouser/getMyRedEnvelopeList" 3.3;
        /**
         * 首页积分领取
         */
        val API_METHOD_GETUSER_INTEGRAL = "app/integral/userIntegral/receiveUserIntegral"
        /**
         * 查看积分使用详情
         */
        val API_METHOD_GETUSER_INTEGRAL_DETAIL = "app/integral/userIntegral/appSelectIntegralRecord"
        /**
         * 积分规则弹窗
         */
        val API_METHOD_ISSHOW_INTEGRAL_REGULATION = "app/integral/userIntegral/selectIsPopup"
        /**
         * 修改积分规则弹窗条件
         */
        val API_METHOD_UPDATE_INTEGRAL_REGULATION = "app/integral/userIntegral/updateIntegralStatus"
        /**
         * 加载分裂红包弹窗判断
         */
        val API_METHOD_LOAD_READPACKAGE_STATUS = "app/reduction/division/findDivisionRedpaperAppLoadInfo"
        /**
         * 确认拆分红包
         */
        val API_METHOD_CONFIRM_REDPACKAGE = "app/reduction/division/confirmDivisionRedpaperFromApp"
        /**
         * 首页推荐商户 根据经纬度返回对应结果
         */
        val API_METHOD_RECOMMEND_SHOP_GETPOSITIONINFO = "app/community/notice/getPositionInfo"
        /**
         * 首页推荐商户 选择位置列表
         */
        val API_METHOD_RECOMMEND_SHOP_GETADDRESSLIST = "app/community/neighbor/listCommunityBindHistory"
        /**
         * 位置  用户选择后  保存给后台
         */
        val API_METHOD_RECOMMEND_SHOP_SAVE_USERPOSITION = "app/community/neighbor/updateCommunityBindHistory"
        /**
         * 首页推荐商户  查看更多
         */
        val API_METHOD_RECOMMEND_SHOP_GETMORE = "app/community/notice/selectMoreShopPage"
        /**
         * 判断普通用户是否在一周内发送过 内容
         */
        val API_METHOD_HAS_SENDPUBLISH = "app/community/notice/selectHasPublish"
        /**
         * 普通用户发布公告
         */
        val API_METHOD_SEND_ANNOUNCEMENT = "app/community/notice/publish"
        /**
         * 推荐商户入驻
         */
        val API_METHOD_RECOMMEND_SHOP_ENTER = "app/dataCenter/shopSign/shopSign"
        /**
         * 根据地区判断是否申请过合伙人
         */
        val API_METHOD_IS_APPLLY_PARTNER = "app/dataCenter/appPartnerEnlist/isEnlist"
        /**
         * 申请成为合伙人
         */
        val API_METHOD_APPLLY_PARTNER = "app/dataCenter/appPartnerEnlist/partnerEnlist"
        /**
         * 查看物流信息
         */
        val API_METHOD_EXPRESSDETAIL = "app/order/orderUserController/selectExpressDadaOrderInfo"
        /**
         * 邻居列表
         */
        val API_METHOD_SELECTNEIGHBORLIST = "app/community/neighbor/neighborController/selectNeighborList"
        /**
         * 修改个性签名
         */
        val API_METHOD_UPDATEUSERAUTOGRAPH = "app/community/neighbor/neighborController/updateUserAutograph"
        /**
         * 邻居详情
         */
        val API_METHOD_SELECTNEIGHBORRECORD = "app/community/neighbor/neighborController/selectNeighborRecord"
        /**
         * 邻居按钮消息数
         */
        val API_METHOD_UNREADNEIGHBORNUMBER = "app/community/neighbor/neighborController/unreadNeighborNumber"
        /**
         * 收藏当前小区地址
         */
        val API_METHOD_UPDATECOMMUNITYFAVORITE = "app/community/neighbor/updateCommunityFavorite"
        /**
         * 收藏当前小区地址
         */
        val API_METHOD_SAVEUSERBINDCOMMUNITY = "app/community/neighbor/saveUserBindCommunity"
        /**
         * 寻找儿时伙伴  分享链接
         */
        val API_METHOD_NEIGHBORGETSHAREURL = "app/community/neighbor/neighborController/getShareUrl"
        /**
         * 退出邻居
         */
        val API_METHOD_EXITNEIGHBOR = "app/community/neighbor/neighborController/userLogout"
        /**
         * 查看用户是否可以串门
         */
        val API_METHOD_SELECTNEIGHBORISQUIT = "app/community/neighbor/neighborController/selectNeighborIsQuit"
        /**
         * 添加串门信息
         */
        val API_METHOD_INSERTNEIGHBORDETAIL = "app/community/neighbor/neighborController/insertNeighborDetail"
        /**
         * 社区未读消息数
         */
        val API_METHOD_COMMUNITYNOREADNUMBER = "app/community/notice/getNoReadNumberCount"
        /**
         * 3.3 接口 查看应用未读消息数
         */
        val API_METHOD_APPUNREADNUMBER = "app/msg/msgCenter/searchMessageNumInfo"


        /**ServersPort 修改  start*/

        /**
         * 退出 get
         * 11用户  12 商户
         */
        val API_METHOD_LOGOUT = "app/user/2.0/logout"
        /**
         * 修改密码 post
         */
        val API_METHOD_UPDATEPWD = "app/user/2.0/updatePwd"
        /**
         * 发送验证码 get username：手机号 regType：注册类型0：手机
         * sendPoint：选填，10手机号必须已注册过(快捷登录，找回密码 *
         * ),20手机号必须是自己已绑定的(提现发送验证码)，30手机号未被注册才能发送验证码(手机注册)，为空则不做规则校验直接发送验证码
         * clientType：请求来源类型：10用户版，20服务商版，30PC端(目前注册时候不用传)
         */
        val API_METHOD_SENDREGCODE = "app/user/2.0/sendRegCode"
        /**
         * 修改支付密码时校验验证码
         */
        val API_METHOD_CHECKPAYPASSWORDREGCODE = "app/PayPassword/checkRegCode"
        /**
         * 校验验证码 username：手机号 regCode：用户填写的验证码 post
         */
        val API_METHOD_CHECKREGCODE = "app/user/2.0/checkRegCode"
        /**
         * 新增地址 post
         *
         *
         * areaCode：选择的省市区最末级地区编码 street：所属街道，如地址未精确到街道，该值为空 address：详细街道楼宇地址
         * linkman：联系人 cellphone：联系地址 gpsX：gps经度 gpsY：gps纬度 postcode：邮编
         * isdefault：是否默认，0：非默认地址，1：默认地址
         */
        val API_METHOD_ADDUSERADDRESS = "app/address/addUserAddress"
        /**
         * 地址列表 get
         */
        val API_METHOD_GETUSERADDRESSLIST = "app/address/getUserAddressList"
        /**
         * 修改地址 post id：地址ID
         * addressType：地址类型，如记录该地址属于哪一类业务：10上门服务地址，20快递地址，如项目未区分地址类型该值传空值
         *
         *
         * areaCode：选择的省市区最末级地区编码 street：所属街道，如地址未精确到街道，该值为空 address：详细街道楼宇地址
         * linkman：联系人 cellphone：联系地址 gpsX：gps经度 gpsY：gps纬度 postcode：邮编
         * isdefault：是否默认，0：非默认地址，1：默认地址
         */
        val API_METHOD_UPUSERADDRESS = "app/address/upUserAddress"
        /**
         * 错误日志上传
         */
        val API_METHOD_LOGAPPDEBUG = "app/log/logAppDebug"

        /**
         * 店铺资质列表
         */
        val API_METHOD_GETSHOPAPTITUDELIST = "app/qt/firstPageShop/getShopAptitudeList"
        /**
         * 未读消息的条数
         */
        val API_METHOD_GETALLUNREADMSGCOUNTBYUERS = "app/qt/msg/business/group/getAllUnreadMsgCountByUers"
        /**
         * 绑定设备
         */
        val API_METHOD_BINDDEVICE = "msg/push/bindDevice"


        /**
         * 删除地址 get
         */
        val API_METHOD_DELUSERADDRESS = "app/address/delUserAddress"
        /**
         * 设置默认地址 post
         */
        val API_METHOD_SETDEFAULTADDRESS = "app/address/setDefaultAddress"
        /**
         * 获取默认地址 get
         */
        val API_METHOD_GETDEFAULTADDRESS = "app/address/getDefaultAddress"
        /**
         * 获取个人资料 get
         */
        val API_METHOD_GETUSERINFO = "app/user/2.0/getUserInfo"
        /**
         * 修改个人资料 post
         */
        val API_METHOD_EDITUSERINFO = "app/user/2.0/editUserInfo"
        /**
         * 地图获取附近店铺
         */
        val API_METHOD_GETNEARBYSHOP = "app/qt/shop/list"
        /**
         * post 根据项目ID获取标签列表 id
         */
        val API_METHOD_LISTTAGBYSERVPROJIDANDAREACODE = "app/qt/tag/listTagByServprojIdAndAreaCode"
        /**
         * post 根据项目ID获取服务方式 id
         */
        val API_METHOD_GETSERVPROJSERVTYPEBYID = "app/qt/servproj/getServprojServTypeById"
        /**
         * 提交订单评价 get 返回待评价列表
         *
         *
         * orderId：订单ID score：评分（1-5） content：评价内容详情 isAnonymous：是否匿名，0实名，1匿名
         * picture：图片文件流数组，保留字段暂未启用
         */
        val API_METHOD_ORDEREVALUATESUBMIT = "app/qt/qtOrder/orderEvaluateSubmit"
        /**
         * 修改手机号 post phone：新手机号（修改手机号时必填） email：新邮箱号（修改邮箱时必填） regCode：短信或邮箱验证码
         * regType：绑定类型（0：手机号，1：邮箱）
         */
        val API_METHOD_REBIND = "app/user/2.0/reBind"
        /********************************* YOU选2.0版本网络请求接口 *********************************/

        /**
         * sortType 排序类型 10 推荐（加权） 20人气最高 30 评分 （没有加权）40 销量最高 50价格最低 60 距离最近
         * areaCode 区域code
         * gpsX 经度
         * gpsY 纬度
         * searchKey 搜索关键字
         * servType 服务方式 0到店 1到家
         * servprojId 服务项目ID
         * tagId 标签ID
         * tradeId 行业类别ID
         */
        val API_METHOD_LISTQUERYNEW = "app/qt360/shopServer/listShopServer"//"app/qt/firstPageServer/listQueryNew" 3.3修改;
        /**
         * 新版首页获取服务内容列表
         */
        val API_METHOD_LISTLAYOUTQUERYNEW = "app/qt/firstPageServer/listLayoutQueryNew"
        /**
         * //语音搜索服务项目 keyWords
         */
        val API_METHOD_GETAUDIOSEARCHFORSERVPROJ = "app/qt/search/audioSearchForServproj"
        /**
         * 首页获取服务详情
         */
        val API_METHOD_GETSERVERDETAILS = "app/qt/firstPageServer/getServerById"
        /**
         * 首页行业服务列表
         */
        val API_METHOD_GETSERVPROJ_LISTSERVPROJ = "app/qt/servproj/listServproj"
        /**
         * 首页查看服务商详情
         */
        val API_METHOD_GETSHOPINFOBYID = "app/qt/firstPageShop/getShopInfoById"
        /**
         * collectType:收藏类型 10服务商 20服务内容 收藏 keyId：收藏的服务商或者服务内容的ID
         */
        val API_METHOD_GETSHOPSAVECOLLECT = "app/qt/collect/saveCollect"
        /**
         * 取消收藏
         */
        val API_METHOD_GETSHOPCANCELCOLLECT = "app/qt/collect/cancelCollect"
        /**
         * 订单支付
         */
        val API_METHOD_GETORDERPAY = "app/qt/qtOrder/orderPay"
        /**
         * 我的钱包
         */
        val API_METHOD_MYACCOUNTADDUP = "app/account/1.0/myAccountAddUp"
        /**
         * 账户冻结详情
         */
        val API_METHOD_SELECTACCOUNTFREEZEINFO = "app/account/1.0/selectAccountFreezeInfo"
        /**
         * 收支明细
         * //"app/account/1.0/myAccountExpenditure";2018-01-11 资金模块调整
         */
        val API_METHOD_GETMYACCOUNTEXPENDITURE = "app/account/1.0/listAccountRecordNew"

        /**
         * 验证支付密码
         */
        val API_METHOD_GETCHACKPAYPASSWORD = "app/PayPassword/chackPayPassword"
        /**
         * 提现
         */
        val API_METHOD_GETACCOUNTEXTRACTMONEY = "app/account/1.0/accountExtractMoney"
        /**
         * 设置支付密码
         */
        val API_METHOD_GETSAVEPAYPASSWORD = "app/PayPassword/savePayPassword"
        /**
         * 原始支付密码 修改支付密码
         */
        val API_METHOD_GETUPDATEPAYPASSWORD = "app/PayPassword/updatePayPassword"
        /**
         * 验证码 修改支付密码
         */
        val API_METHOD_SETPAYPWBYCODE = "app/PayPassword/setPaypwByCode"
        /**
         * 账户绑定
         */
        val API_METHOD_GETBANKCARDBINDING = "app/account/1.0/bankCardBinding"
        /**
         * 银行卡 支付宝账户列表
         */
        val API_METHOD_GETBANKCARDBINDINGLIST = "app/account/1.0/listUserCardBinding"
        /**
         * 可提现金额
         */
        val API_METHOD_ACCOUNTEXTRACTMONEYINFO = "app/account/1.0/accountExtractMoneyInfo"
        /**
         * 三方支付支付宝调用后台
         */
        val API_METHOD_PAYZFBAPPPAYSUBMIT = "pay/alipay/appPaySubmit"
        /**
         * 三方支付微信调用后台
         */
        val API_METHOD_PAYWXAPPPAYSUBMIT = "pay/weixin/appPaySubmit"
        /**
         * 删除银行卡 或 支付宝账户 post
         */
        val API_METHOD_BANKCARDCANCEL = "app/account/1.0/bankCardCancel"
        /***
         * 服务客户评价
         */
        val API_METHOD_GETSERVERCOMMENT = "app/qt/firstPageServer/getServerCommentList"
        /***
         * 店铺客户评价
         */
        val API_METHOD_GETSHOPCOMMENT = "app/qt/firstPageShop/getShopCommentList"
        /***
         * 语音搜索热门搜索关键词
         */
        val API_METHOD_GETSEACHERHOTSTR = "app/qt/search/getHotSearchKeys/2.0"
        /**
         * 店铺热门搜索关键词 post
         */
        val API_METHOD_GETHOTSEARCHKEYS = "app/qt/firstPageServer/getHotSearchKeys"
        /**
         * 获取银行卡列表
         */
        val API_METHOD_GETBANKLIST = "app/account/1.0/bankList"

        /**
         * 地区是否开通服务查询
         */
        val API_METHOD_AREAISOPENSERVICE = "app/areaGrade/areaIsOpenService"
        /**
         * 版本检测
         */
        val API_METHOD_APPVERSIONINFO = "app/appcenter/appVersionInfo"
        /**
         * 校验第三方登录信息是否已经绑定
         */
        val API_METHOD_CHECKTHREELOGININFO = "app/user/2.0/checkThreeLoginInfo"
        /**
         * 已经登录账号 进行第三方账号绑定
         */
        val API_METHOD_BINDTHREEUSER = "app/user/2.0/bindThreeUser"
        /**
         * 获取第三方账号绑定列表
         */
        val API_METHOD_BINDTHREEUSERLIST = "app/user/2.0/bindThreeUserList"

        /**
         * 解绑第三方账号
         */
        val API_METHOD_UNBINDTHREEUSER = "app/user/2.0/unBindThreeUser"
        /**
         * 校验手机号码是否已经存在
         */
        val API_METHOD_CHECKTHREELOGINMOBILE = "app/user/2.0/checkThreeLoginMobile"
        /**
         * 全部分类
         */
        val API_METHOD_TREETRADESERVPROJANDRECOMMEND = "app/qt/servproj/treeTradeServprojAndRecommend"
        /**
         * 全部分类
         */
        val API_METHOD_NEWTREETRADESERVPROJANDRECOMMEND = "app/qt/servproj/newtreeTradeServprojAndRecommend"

        /**
         * 启动页广告
         */
        val API_METHOD_GETSPLASHADSHOW = "app/qt/appStartPage/queryList"
        /**
         * 检查用户活动资格
         */
        val API_METHOD_CHECKQUALIFICATION = "app/activitys/redPaper/checkQualification"
        /**
         * 评价完成跳转抽奖活动
         */
        val API_METHOD_CHECKORDER = "app/activitys/lottery/checkOrder"
        /**
         * 消费金明细
         */
        val API_METHOD_GETCMONEYDETAIL = "app/cmoneyaccountdetail/getcmoneydetail"
        /**
         * 消费金详情
         */
        val API_METHOD_GETCONSUMPTIONFUNDDETAIL = "app/cmoneyaccount/getcmoneydetail"
        /**
         * 分享统计
         */
        val API_METHOD_POSTSHARESTATISTICS = "app/activitys/share/add"
        /**
         * 下单选择优惠信息
         */
        val API_METHOD_POSTDOWNORDERGETPREFERENTIALINFO = "app/doubleHundred/coupon/listUserReceivedCoupon"//"app/activitys/coupontouser/getAllRedListByOrder" 3.3;
        /**
         * 提现记录
         */
        val API_METHOD_LISTACCOUNTEXTRACTMONEYWITHSTATISTICS = "app/account/1.0/listAccountExtractMoneyWithStatistics"
        /**
         * 订单可用店铺优惠券数量和红包数量  new 2017-11-7
         */
        val API_METHOD_DOWNORDERAVAILABLECOUPON = "app/doubleHundred/coupon/listUserReceivedCouponNumber"//app/doubleHundred/coupon/listUserReceivedCouponNumber3.3
        /**
         * 首页行业数据和其它热门推荐数据
         */
        val API_METHOD_GETHOMELAYOUTDATA = "app/home/getLayoutData/3.0"
        /**
         * 弹框红包列表
         */
        val API_METHOD_GETEFFECTIVERECENTLISTBYUSERID = "app/activitys/coupontouser/getEffectiveRecentListByUserId"

        /**ServersPort 修改  end*/
//3.3 版本接口 start
        /**
         * 查看首页是否推送店铺优惠券
         */
        val API_METHOD_CHECKPUSHSHOPCOUPON = "app/doubleHundred/shopcoupon/selectShopCouponIsPopup"
        /**
         * 查看用户可领取的优惠券列表
         */
        val API_METHOD_HOMEPUSHSHOPCOUPON = "app/doubleHundred/shopcoupon/availableForCollection"
        /**
         * 一键领取优惠券
         */
        val API_METHOD_ONEKEYGETSHOPCOUPON = "app/doubleHundred/shopcoupon/oneKeyCollection"
        /**
         * 领取店铺优惠券
         */
        val API_METHOD_GETSHOPCOUPON = "app/doubleHundred/shopcoupon/receiveShopCoupon"
        /**
         * 查看店铺优惠券详情
         */
        val API_METHOD_SHOPCOUPONDETAIL = "app/doubleHundred/shopcoupon/getShopCouponforShop"
        /**
         * 首页用户红包入口判断 是否弹窗
         */
        val API_METHOD_GETISOPENNEWUSERREDPACKAGE = "app/doubleHundred/redpacket/getIsOpenRedpacketFrame"
        /**
         * 首页用户红包获取数据
         */
        val API_METHOD_GETHOMENEWUSERREDPACKAGEDATA = "app/doubleHundred/redpacket/listRedpacketActivityCoupon"
        /**
         * 关闭新用户红包弹框
         */
        val API_METHOD_CLOSENEWUSERREDPACKAGE = "app/doubleHundred/redpacket/updateCloseReceivesFrameApp"
        /**
         * 获取行业详情标签
         */
        val API_METHOD_GETTRADELABLE = "app/redEnvelope/labelWarehouse/underWay"
//3.3 版本接口 end
    }
}