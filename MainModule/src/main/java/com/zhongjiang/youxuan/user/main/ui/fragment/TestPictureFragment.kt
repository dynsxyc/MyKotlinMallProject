package com.zhongjiang.kotlin.splash.ui.fragment

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding2.view.RxView
import com.zhongjiang.kotlin.splash.presenter.loginfragment.TestFragmentContract
import com.zhongjiang.kotlin.splash.presenter.loginfragment.TestFragmentPresenter
import com.zhongjiang.youxuan.base.busevent.ActivityRequestCode
import com.zhongjiang.youxuan.base.ext.shieldDoubleClick
import com.zhongjiang.youxuan.base.imageloader.YXImageView
import com.zhongjiang.youxuan.base.oss.UpFileBean
import com.zhongjiang.youxuan.base.ui.fragment.BaseSelectorImgFragment
import com.zhongjiang.youxuan.user.main.R
import kotlinx.android.synthetic.main.fragment_testpicture.*

class TestPictureFragment : BaseSelectorImgFragment<TestFragmentPresenter, TestFragmentContract.View, TestFragmentContract.Model>(), TestFragmentContract.View {

    override fun getRequestSelectorImgCode(): Int {
        return ActivityRequestCode.REQUEST_IMAGESELECTOR_CODE.requestCode
    }

    override fun getDefaultMaxSelectNum(): Int {
        return 9
    }

    override fun getDefaultFileModuleType(): UpFileBean.Companion.FileModuleType {
        return UpFileBean.Companion.FileModuleType.ANDROID
    }

    override fun onFileUpIng(it: UpFileBean) {
        com.orhanobut.logger.Logger.i("文件名 = ${it.fileName},上传状态= ${it.upType},上传进度= ${it.progress},图片路径 = ${it.filePath},上传返回路径=${it.upSuccessUrl}")
    }

    override fun initView() {

        RxView.clicks(btCamera).shieldDoubleClick {
            openMedia(true)
        }
        RxView.clicks(btAlbum).shieldDoubleClick {
            mPresenter.startLocation(_mActivity)
        }
        testRecyclerView.layoutManager = GridLayoutManager(_mActivity,2,GridLayoutManager.HORIZONTAL,false)
        var itemDate= ItemDate("测试行业","https://img-ads.csdn.net/2019/201903131400184107.png")
        var list = arrayListOf<ItemDate>(itemDate,itemDate,itemDate,itemDate,itemDate,itemDate,
                                                               itemDate,itemDate,itemDate,itemDate,itemDate,itemDate,
                                                                itemDate,itemDate,itemDate,itemDate,itemDate,itemDate)
        var adapter = Adapter(_mActivity,list)
        testRecyclerView.adapter = adapter
        testRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                //显示区域的高度。
                var extent = testRecyclerView.computeHorizontalScrollExtent();
                //整体的高度，注意是整体，包括在显示区域之外的。
                var range = testRecyclerView.computeHorizontalScrollRange();
                //已经向下滚动的距离，为0时表示已处于顶部。
                var offset = testRecyclerView.computeHorizontalScrollOffset();
                Log.i("dx------","$range **** $extent **** $offset");
                //此处获取seekbar的getThumb，就是可以滑动的小的滚动游标
                var gradientDrawable = slide_indicator_point.thumb
                var width = extent/(list.size/2)
                //根据列表的个数，动态设置游标的大小，设置游标的时候，progress进度的颜色设置为和seekbar的颜色设置的一样的，所以就不显示进度了。
                (gradientDrawable as GradientDrawable).setSize(width,5);
                slide_indicator_point.thumbOffset = 0
                //设置可滚动区域T
                slide_indicator_point.max = range-extent;
                if (dx==0){
                    slide_indicator_point.setProgress(0);
                }else if (dx>0){
//                    int ss = (int)(tt/2.3f);
                    Log.i("dx------","右滑");
                    slide_indicator_point.setProgress(offset);
                }else if (dx<0){
                    Log.i("dx------","左滑");
                    slide_indicator_point.setProgress(offset);
                }
            }
        })

    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_testpicture
    }

     class Adapter constructor(var context: Context,var list: List<ItemDate>) : RecyclerView.Adapter<MViewHolder>() {

         override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : MViewHolder {
            return MViewHolder(LayoutInflater.from(context).inflate(R.layout.item_test_trade,parent,false))
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: MViewHolder, position: Int) {
            holder.title.text = list[position].title
            holder.yxImageView.setImageUrl(list[position].img)
        }

    }

    class MViewHolder constructor(view: View) : RecyclerView.ViewHolder(view) {
           var yxImageView = view.findViewById<YXImageView>(R.id.testImg)
           var title = view.findViewById<TextView>(R.id.testTitle)


    }

    data class ItemDate(var title:String,var img:String)

}
