
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.util.SparseArray
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.annotation.NonNull
import androidx.annotation.StyleRes
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.PopupWindowCompat
import com.zhongjiang.youxuan.base.R
import java.lang.IllegalStateException

class AlertDialogManager {
     class Builder constructor(@NonNull private val context:Context, @StyleRes private val themeResId:Int){
        private var mView:View? = null
        private var mLayoutResId: Int = 0
         // 存放字体的修改
         var mTextArray = SparseArray<CharSequence>()
         // 存放点击事件
         var mClickArray = SparseArray<View.OnClickListener>()
        private var mAnimations:Int = 0
        private var mGravity:Int = Gravity.CENTER
        private var mWidth:Int = ViewGroup.LayoutParams.WRAP_CONTENT
        private var mHeight:Int = ViewGroup.LayoutParams.WRAP_CONTENT
         private var mOnDismissListener: DialogInterface.OnDismissListener? = null
         private var mOnCancelListener: DialogInterface.OnCancelListener? = null
         private var mOnKeyListener: DialogInterface.OnKeyListener? = null

        constructor(context: Context):this(context, R.style.dialog)

        fun setView(view:View):Builder{
            mView = view
            return this
        }
        fun setView(layoutResId:Int):Builder{
            mLayoutResId = layoutResId
            return this
        }
         fun setOnCancelListener(listener: DialogInterface.OnCancelListener) :Builder{
             mOnCancelListener = listener
             return this
         }
         fun setOnDismissListener( listener: DialogInterface.OnDismissListener) :Builder{
             mOnDismissListener = listener
             return this
         }
         fun setOnKeyListener( listener: DialogInterface.OnKeyListener) :Builder{
             mOnKeyListener = listener
             return this
         }
         fun setOnclickListener(viewId: Int, listener: View.OnClickListener) :Builder{
             mClickArray.put(viewId,listener)
             return this
         }
         /**
          *
          * 给对应viewId 的textView 设置文字内容
          * */
         fun setText(viewId: Int, text: CharSequence) :Builder{
             mTextArray.put(viewId,text)
             return this
         }

         /**
          * 添加默认动画
          * @return
          */
         fun addDefaultAnimation(): Builder {
             mAnimations = R.style.dialog_scale_anim
             return this
         }

         /**
          * 设置动画
          * @param styleAnimation
          * @return
          */
         fun setAnimations(styleAnimation: Int): Builder {
             mAnimations = styleAnimation
             return this
         }
         /**
          * 从底部弹出
          * @param isAnimation 是否有动画
          * @return
          */
         fun formBottom(isAnimation: Boolean): Builder {
             if (isAnimation) {
                 mAnimations = R.style.dialog_from_bottom_anim
             }
             mGravity = Gravity.BOTTOM
             return this
         }
         fun fullWidth(): Builder {
             mWidth = ViewGroup.LayoutParams.MATCH_PARENT
             return this
         }

         /**
          * 设置Dialog的宽高
          * @param width
          * @param height
          * @return
          */
         fun setWidthAndHeight(width: Int, height: Int): Builder {
             mWidth = width
             mHeight = height
             return this
         }

         fun setGrivaty(gravity : Int):Builder{
             mGravity = gravity
             return this
         }


        private fun create():AlertDialog{
            var builder = AlertDialog.Builder(context,themeResId)
            var mDialogViewHelper:DialogViewHelper? = null
            if (mLayoutResId != 0){
                mDialogViewHelper = DialogViewHelper(context,mLayoutResId)
            }
            mView?.let {
                mDialogViewHelper = DialogViewHelper()
                mDialogViewHelper?.contentView = mView
            }
            mDialogViewHelper?.let { helper ->
                mTextArray?.let {
                    for (i in 0 until it.size()){
                        try{
                            helper.setText(it.keyAt(i),it.valueAt(i))
                        }catch (e:IllegalStateException){
                            e.printStackTrace()
                        }
                    }
                }
                mClickArray?.let {
                    for (i in 0 until it.size()){
                        try{
                            helper.setOnclickListener(it.keyAt(i),it.valueAt(i))
                        }catch (e:IllegalStateException){
                            e.printStackTrace()
                        }
                    }
                }
                builder.setView(helper.contentView)
            }
            builder.setOnDismissListener(mOnDismissListener)
            builder.setOnCancelListener(mOnCancelListener)
            builder.setOnKeyListener(mOnKeyListener)
            builder.context
            return builder.create()
        }
         fun show(): AlertDialog {
             val dialog = create()
             dialog.show()
             val window = dialog.window
             //设置位置
             window.setGravity(mGravity)
             // 设置动画
             if (mAnimations != 0) {
                 window.setWindowAnimations(mAnimations)
             }
             // 设置宽高
             val params = window.attributes
             params.width = mWidth
             params.height = mHeight
             window.attributes = params
             return dialog
         }
    }
}