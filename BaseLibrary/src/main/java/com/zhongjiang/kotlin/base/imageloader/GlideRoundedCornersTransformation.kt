package com.zhongjiang.kotlin.base.imageloader

import android.content.Context
import android.graphics.*
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapResource
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import org.jetbrains.anko.dip
import java.security.MessageDigest

/**
 * Created by dyn on 2018/12/4.
 */
class GlideRoundedCornersTransformation() : BitmapTransformation() {

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {

    }

    enum class CornerType {
        /**全部圆角 */
        ALL,
        /**左上圆角 */
        TOP_LEFT,
        /**右上圆角 */
        TOP_RIGHT,
        /**左下圆角 */
        BOTTOM_LEFT,
        /**右下圆角 */
        BOTTOM_RIGHT,
        /**上部分圆角 */
        TOP,
        /**下部分圆角 */
        BOTTOM,
        /**左部分圆角 */
        LEFT,
        /**上部分圆角 */
        RIGHT,
        /**除左上角外其余都是圆角 */
        OTHER_TOP_LEFT,
        /**除右上角外其余都是圆角 */
        OTHER_TOP_RIGHT,
        /**除左下角外其余都是圆角 */
        OTHER_BOTTOM_LEFT,
        /**除右下角外其余都是圆角 */
        OTHER_BOTTOM_RIGHT,
        /**左上、右下对角圆角 */
        DIAGONAL_FROM_TOP_LEFT,
        /**左下、右上对角圆角 */
        DIAGONAL_FROM_TOP_RIGHT,
        /**不同圆角大小实现 */
        DIFFERENT_SIZE
    }

    private val radiusArray = floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)
    private var mTopLeftRadius: Int = 0
    private var mTopRightRadius: Int = 0
    private var mBottomLeftRadius: Int = 0
    private var mBottomRightRadius: Int = 0
    /**
     * 圆角半径
     */
    private var mRadius: Int = 0
    /**
     * 圆角直径
     */
    private var mDiameter: Int = 0
    private lateinit var mCornerType: CornerType
    private var hasBorder: Boolean = false
    private var borderWidth: Int = 0
    private var borderColor: Int = 0
    private var mBorderPaint = Paint()

    /**默认的圆角构造 */
    constructor(context: Context, radius: Int) : this(context, radius, CornerType.ALL) {

    }

    constructor(context: Context, radius: Int, cornerType: CornerType) : this() {
        mRadius = context.dip(radius)
        mDiameter = mRadius * 2
        mCornerType = cornerType
    }

    /**
     * 有边框的构造
     */
    constructor(context: Context, radius: Int, hasBorder: Boolean, borderWidth: Int, borderColor: Int,
                cornerType: CornerType) : this(context, radius, cornerType) {

       var mBorderWidth = context.dip(borderWidth)
        this.hasBorder = hasBorder
        this.borderWidth = mBorderWidth
        this.borderColor = borderColor

        mBorderPaint.isAntiAlias = true
        //设置填充样式为描边
        mBorderPaint.style = Paint.Style.STROKE
        //设置颜色为黑色
        mBorderPaint.color = borderColor
        //设置笔触宽度为1像素
        mBorderPaint.strokeWidth = mBorderWidth.toFloat()
    }

    /**
     * 特殊圆角 意见反馈
     * 没边框、自定义圆角大小的构造
     */
    constructor(context: Context, mTopLeftRadius: Int, mTopRightRadius: Int, mBottomLeftRadius: Int, mBottomRightRadius: Int) : this(context, 0, CornerType.DIFFERENT_SIZE) {

        var topLeftRadius = context.dip(mTopLeftRadius)
        var topRightRadius = context.dip(mTopRightRadius)
        var bottomLeftRadius = context.dip(mBottomLeftRadius)
        var bottomRightRadius = context.dip(mBottomRightRadius)

        this.mTopLeftRadius = topLeftRadius
        this.mTopRightRadius = topRightRadius
        this.mBottomLeftRadius = bottomLeftRadius
        this.mBottomRightRadius = bottomRightRadius

    }

    override fun transform(pool: BitmapPool, source: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        val width = source.width
        val height = source.height
        var bitmap: Bitmap
        try {
            bitmap = pool.get(width, height, Bitmap.Config.ARGB_8888)
//            if (bitmap == null) {
//                bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
//            }

            val canvas = Canvas(bitmap)
            val paint = Paint()
            paint.isAntiAlias = true
            paint.shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            drawRoundRect(canvas, paint, width.toFloat(), height.toFloat())
        } catch (e: Exception) {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        }

        return BitmapResource.obtain(bitmap, pool)!!.get()
    }

    private fun drawRoundRect(canvas: Canvas, paint: Paint, width: Float, height: Float) {

        when (mCornerType) {
            GlideRoundedCornersTransformation.CornerType.ALL -> {
                val halfBorderWidth = borderWidth * 1.0f / 2
                val f = RectF(halfBorderWidth, halfBorderWidth, width - halfBorderWidth, height - halfBorderWidth)
                canvas.drawRoundRect(f, mRadius.toFloat(), mRadius.toFloat(), paint)
//                L.i("hasBorder = " + hasBorder + "borderWidth = " + borderWidth)
                if (hasBorder) {
                    canvas.drawRoundRect(f, mRadius.toFloat(), mRadius.toFloat(),
                            mBorderPaint)
                }
            }
            GlideRoundedCornersTransformation.CornerType.TOP_LEFT -> drawTopLeftRoundRect(canvas, paint, width, height)
            GlideRoundedCornersTransformation.CornerType.TOP_RIGHT -> drawTopRightRoundRect(canvas, paint, width, height)
            GlideRoundedCornersTransformation.CornerType.BOTTOM_LEFT -> drawBottomLeftRoundRect(canvas, paint, width, height)
            GlideRoundedCornersTransformation.CornerType.BOTTOM_RIGHT -> drawBottomRightRoundRect(canvas, paint, width, height)
            GlideRoundedCornersTransformation.CornerType.TOP -> drawTopRoundRect(canvas, paint, width, height)
            GlideRoundedCornersTransformation.CornerType.BOTTOM -> drawBottomRoundRect(canvas, paint, width, height)
            GlideRoundedCornersTransformation.CornerType.LEFT -> drawLeftRoundRect(canvas, paint, width, height)
            GlideRoundedCornersTransformation.CornerType.RIGHT -> drawRightRoundRect(canvas, paint, width, height)
            GlideRoundedCornersTransformation.CornerType.OTHER_TOP_LEFT -> drawOtherTopLeftRoundRect(canvas, paint, width, height)
            GlideRoundedCornersTransformation.CornerType.OTHER_TOP_RIGHT -> drawOtherTopRightRoundRect(canvas, paint, width, height)
            GlideRoundedCornersTransformation.CornerType.OTHER_BOTTOM_LEFT -> drawOtherBottomLeftRoundRect(canvas, paint, width, height)
            GlideRoundedCornersTransformation.CornerType.OTHER_BOTTOM_RIGHT -> drawOtherBottomRightRoundRect(canvas, paint, width, height)
            GlideRoundedCornersTransformation.CornerType.DIAGONAL_FROM_TOP_LEFT -> drawDiagonalFromTopLeftRoundRect(canvas, paint, width, height)
            GlideRoundedCornersTransformation.CornerType.DIAGONAL_FROM_TOP_RIGHT -> drawDiagonalFromTopRightRoundRect(canvas, paint, width, height)
            GlideRoundedCornersTransformation.CornerType.DIFFERENT_SIZE -> drawDifferentSizeRoundRect(canvas, paint, width, height)
            else -> canvas.drawRoundRect(RectF(0f, 0f, width, height), mRadius.toFloat(), mRadius.toFloat(), paint)
        }
    }

    /**左上角圆角实现 */
    private fun drawTopLeftRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
        canvas.drawRoundRect(RectF(0f, 0f, mDiameter.toFloat(), mDiameter.toFloat()),
                mRadius.toFloat(), mRadius.toFloat(), paint)
        canvas.drawRect(RectF(0f, mRadius.toFloat(), mRadius.toFloat(), bottom), paint)
        canvas.drawRect(RectF(mRadius.toFloat(), 0f, right, bottom), paint)
    }

    /**右上角圆角实现 */
    private fun drawTopRightRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
        canvas.drawRoundRect(RectF(right - mDiameter, 0f, right, mDiameter.toFloat()), mRadius.toFloat(),
                mRadius.toFloat(), paint)
        canvas.drawRect(RectF(0f, 0f, right - mRadius, bottom), paint)
        canvas.drawRect(RectF(right - mRadius, mRadius.toFloat(), right, bottom), paint)
    }

    /**左下角圆角实现 */
    private fun drawBottomLeftRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
        canvas.drawRoundRect(RectF(0f, bottom - mDiameter, mDiameter.toFloat(), bottom),
                mRadius.toFloat(), mRadius.toFloat(), paint)
        canvas.drawRect(RectF(0f, 0f, mDiameter.toFloat(), bottom - mRadius), paint)
        canvas.drawRect(RectF(mRadius.toFloat(), 0f, right, bottom), paint)
    }

    /**右下角圆角实现 */
    private fun drawBottomRightRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
        canvas.drawRoundRect(RectF(right - mDiameter, bottom - mDiameter, right, bottom), mRadius.toFloat(),
                mRadius.toFloat(), paint)
        canvas.drawRect(RectF(0f, 0f, right - mRadius, bottom), paint)
        canvas.drawRect(RectF(right - mRadius, 0f, right, bottom - mRadius), paint)
    }

    /**上圆角实现 */
    private fun drawTopRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
        canvas.drawRoundRect(RectF(0f, 0f, right, mDiameter.toFloat()), mRadius.toFloat(), mRadius.toFloat(),
                paint)
        canvas.drawRect(RectF(0f, mRadius.toFloat(), right, bottom), paint)
    }

    /**自定义不同大小的圆角角度 */
    private fun drawDifferentSizeRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
        radiusArray[0] = mTopLeftRadius.toFloat()
        radiusArray[1] = mTopLeftRadius.toFloat()
        radiusArray[2] = mTopRightRadius.toFloat()
        radiusArray[3] = mTopRightRadius.toFloat()
        radiusArray[4] = mBottomRightRadius.toFloat()
        radiusArray[5] = mBottomRightRadius.toFloat()
        radiusArray[6] = mBottomLeftRadius.toFloat()
        radiusArray[7] = mBottomLeftRadius.toFloat()
        val path = Path()
        path.addRoundRect(RectF(0f, 0f, right, bottom), radiusArray, Path.Direction.CW)
        paint.isAntiAlias = true
        canvas.drawPath(path, paint)

    }

    /**下圆角实现 */
    private fun drawBottomRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
        canvas.drawRoundRect(RectF(0f, bottom - mDiameter, right, bottom), mRadius.toFloat(), mRadius.toFloat(),
                paint)
        canvas.drawRect(RectF(0f, 0f, right, bottom - mRadius), paint)
    }

    /**左圆角实现 */
    private fun drawLeftRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
        canvas.drawRoundRect(RectF(0f, 0f, mDiameter.toFloat(), bottom), mRadius.toFloat(), mRadius.toFloat(),
                paint)
        canvas.drawRect(RectF(mRadius.toFloat(), 0f, right, bottom), paint)
    }

    /**右圆角实现 */
    private fun drawRightRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
        canvas.drawRoundRect(RectF(right - mDiameter, 0f, right, bottom), mRadius.toFloat(), mRadius.toFloat(),
                paint)
        canvas.drawRect(RectF(0f, 0f, right - mRadius, bottom), paint)
    }

    /**除左上角外其余都是圆角 */
    private fun drawOtherTopLeftRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {

        canvas.drawRoundRect(RectF(0f, bottom - mDiameter, right, bottom), mRadius.toFloat(), mRadius.toFloat(),
                paint)
        canvas.drawRoundRect(RectF(right - mDiameter, 0f, right, bottom), mRadius.toFloat(), mRadius.toFloat(),
                paint)
        canvas.drawRect(RectF(0f, 0f, right - mRadius, bottom - mRadius), paint)
    }

    /**除右上角外其余都是圆角 */
    private fun drawOtherTopRightRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
        canvas.drawRoundRect(RectF(0f, 0f, mDiameter.toFloat(), bottom), mRadius.toFloat(), mRadius.toFloat(),
                paint)
        canvas.drawRoundRect(RectF(0f, bottom - mDiameter, right, bottom), mRadius.toFloat(), mRadius.toFloat(),
                paint)
        canvas.drawRect(RectF(mRadius.toFloat(), 0f, right, bottom - mRadius), paint)
    }

    /**除左下角外其余都是圆角 */
    private fun drawOtherBottomLeftRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
        canvas.drawRoundRect(RectF(0f, 0f, right, mDiameter.toFloat()), mRadius.toFloat(), mRadius.toFloat(),
                paint)
        canvas.drawRoundRect(RectF(right - mDiameter, 0f, right, bottom), mRadius.toFloat(), mRadius.toFloat(),
                paint)
        canvas.drawRect(RectF(0f, mRadius.toFloat(), right - mRadius, bottom), paint)
    }

    /**除右下角外其余都是圆角 */
    private fun drawOtherBottomRightRoundRect(canvas: Canvas, paint: Paint, right: Float,
                                              bottom: Float) {
        canvas.drawRoundRect(RectF(0f, 0f, right, mDiameter.toFloat()), mRadius.toFloat(), mRadius.toFloat(),
                paint)
        canvas.drawRoundRect(RectF(0f, 0f, mDiameter.toFloat(), bottom), mRadius.toFloat(), mRadius.toFloat(),
                paint)
        canvas.drawRect(RectF((0 + mRadius).toFloat(), mRadius.toFloat(), right, bottom), paint)
    }

    /**左上、右下对角圆角实现 */
    private fun drawDiagonalFromTopLeftRoundRect(canvas: Canvas, paint: Paint, right: Float,
                                                 bottom: Float) {
        canvas.drawRoundRect(RectF(0f, 0f, mDiameter.toFloat(), (0 + mDiameter).toFloat()),
                mRadius.toFloat(), mRadius.toFloat(), paint)
        canvas.drawRoundRect(RectF(right - mDiameter, bottom - mDiameter, right, bottom), mRadius.toFloat(),
                mRadius.toFloat(), paint)
        canvas.drawRect(RectF(0f, mRadius.toFloat(), right - mDiameter, bottom), paint)
        canvas.drawRect(RectF(mDiameter.toFloat(), 0f, right, bottom - mRadius), paint)
    }

    /**左下、右上对角圆角实现 */
    private fun drawDiagonalFromTopRightRoundRect(canvas: Canvas, paint: Paint, right: Float,
                                                  bottom: Float) {
        canvas.drawRoundRect(RectF(right - mDiameter, 0f, right, mDiameter.toFloat()), mRadius.toFloat(),
                mRadius.toFloat(), paint)
        canvas.drawRoundRect(RectF(0f, bottom - mDiameter, mDiameter.toFloat(), bottom),
                mRadius.toFloat(), mRadius.toFloat(), paint)
        canvas.drawRect(RectF(0f, 0f, right - mRadius, bottom - mRadius), paint)
        canvas.drawRect(RectF(mRadius.toFloat(), mRadius.toFloat(), right, bottom), paint)
    }


}