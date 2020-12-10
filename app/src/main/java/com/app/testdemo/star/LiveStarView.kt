package com.app.testdemo.star

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Log
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.app.testdemo.R
import java.lang.ref.WeakReference
import java.util.*

class LiveStarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    RelativeLayout(context, attrs, defStyleAttr) {

    /**
     * 产生随机数
     */
    private val mRandom: Random by lazy {
        Random()
    }

    //默认大小  适配wrap_content
    private var mWidth: Int = 300
    private var mHeight: Int = 900

    private var iconWidth: Int = 0
    private var iconHeight: Int = 0


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.AT_MOST && MeasureSpec.getMode(
                heightMeasureSpec
            ) == MeasureSpec.AT_MOST
        ) {
            setMeasuredDimension(mWidth, mHeight)
        } else if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.AT_MOST) {
            mHeight = measuredHeight
            setMeasuredDimension(mWidth, mHeight)
        } else if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
            mWidth = measuredWidth
            setMeasuredDimension(mWidth, mHeight)
        } else {
            mWidth = measuredWidth
            mHeight = measuredHeight
        }

    }

    override fun onDetachedFromWindow() {
        removeAllViews()
        super.onDetachedFromWindow()
    }

    /**
     * 添加点赞
     */
    fun addLiveStar() {
        val view = ImageView(context)
        view.setImageResource(getImageRandom())
        iconWidth = view.drawable.intrinsicWidth
        iconHeight = view.drawable.intrinsicHeight
        addView(view)
        startAnimator(view)
    }

    /**
     * 随机字体颜色
     */
    private fun getImageRandom(): Int {
        val r = mRandom.nextInt(4)
        when (r) {
            0 -> return R.mipmap.icon_luck_blue
            1 -> return R.mipmap.icon_luck_orange
            2 -> return R.mipmap.icon_luck_purple
            3 -> return R.mipmap.icon_luck_yellow
        }
        return R.mipmap.icon_luck_blue
    }

    /**
     * 开启动画
     */
    private fun startAnimator(view: ImageView) {
        //贝塞尔曲线第一个 第二个控制点
        val pointF1 = PointF(
            mRandom.nextInt(mWidth).toFloat(),
            (mRandom.nextInt(mHeight / 2) + mHeight / 2).toFloat()
        )
        val pointF2 =
            PointF(mRandom.nextInt(mWidth).toFloat(), mRandom.nextInt(mHeight / 2).toFloat())

        //贝塞尔曲线 起 始 点
        val pointStart = PointF(
            ((mWidth - iconWidth) / 2).toFloat(),
            (mHeight - iconHeight).toFloat()
        )
        val pointEnd =
            PointF(
                mRandom.nextInt(mWidth).toFloat(),
                mRandom.nextInt(mHeight / 2).toFloat()
            )

        val evaluator = BezierEvaluator(pointF1, pointF2)
        val animator = ValueAnimator.ofObject(evaluator, pointStart, pointEnd)
        animator.setTarget(view)
        animator.duration = 2000
        animator.interpolator = DecelerateInterpolator()
        animator.addUpdateListener(UpdateListener(WeakReference(view)))
        animator.addListener(AnimatorListener(WeakReference(view), WeakReference(this)))
        animator.start()
    }

    /**
     * 更新点赞图标位置
     */
    class UpdateListener(private var iv: WeakReference<ImageView>) :
        ValueAnimator.AnimatorUpdateListener {


        override fun onAnimationUpdate(animation: ValueAnimator) {
            val pointF = animation.animatedValue as PointF
            val view = iv.get()
            view?.x = pointF.x
            view?.y = pointF.y
            view?.alpha = 1 - animation.animatedFraction + 0.1f
            if (animation.animatedFraction > 0.5) {
                view?.scaleX = 1 - animation.animatedFraction + 0.5f
                view?.scaleY = 1 - animation.animatedFraction + 0.5f
            }
        }
    }

    /**
     * 动画结束将view从容器中移除
     */
    class AnimatorListener(
        private val iv: WeakReference<ImageView>,
        private val parent: WeakReference<LiveStarView>
    ) :
        AnimatorListenerAdapter() {

        override fun onAnimationEnd(animation: Animator?) {
            val view = iv.get()
            val pView = parent.get()
            if (null != view)
                pView?.removeView(view)
        }
    }


}