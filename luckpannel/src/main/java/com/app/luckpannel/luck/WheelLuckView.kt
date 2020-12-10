package com.app.luckpannel.luck

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.app.luckpannel.R

/**
 * 幸运转盘抽奖容器
 */
class WheelLuckView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    RelativeLayout(context, attrs, defStyleAttr) {

    private var mCenterContainer: LinearLayout = LinearLayout(context)
    private var mRewardImgView: ImageView = ImageView(context)
    private var mRewardTextView: TextView = TextView(context)

    private var mWheelLuckSpanView: WheelLuckSpanView = WheelLuckSpanView(context, attrs)

    private var mRotateListener: LuckRotateListener? = null

    init {

        val imageLayoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageLayoutParams.addRule(CENTER_IN_PARENT)
        val image = ImageView(context)
        image.setBackgroundResource(R.mipmap.icon_luck_bg)
        image.layoutParams = imageLayoutParams
        addView(image)

        val layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        layoutParams.addRule(CENTER_IN_PARENT)
        mWheelLuckSpanView.layoutParams = layoutParams
        addView(mWheelLuckSpanView)

        //设置中间的指针背景  默认是可以抽奖的背景
        mCenterContainer.setBackgroundResource(R.mipmap.icon_start_reward)
        mCenterContainer.orientation = LinearLayout.VERTICAL
        mCenterContainer.gravity = Gravity.CENTER
        val mCenterParams =
            LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        mCenterParams.addRule(CENTER_IN_PARENT)
        mCenterContainer.layoutParams = mCenterParams

        //设置指针中间的图标
        mRewardImgView.setImageResource(R.mipmap.icon_has_reward_text)
        mRewardTextView.gravity = Gravity.CENTER
        mRewardTextView.setPadding(0, 10, 0, 0)
        mRewardTextView.text = "1次机会"
        mRewardTextView.textSize = 9f
        mRewardTextView.setTextColor(Color.WHITE)

        mCenterContainer.addView(mRewardImgView)
        mCenterContainer.addView(mRewardTextView)

        addView(mCenterContainer)

        //开始抽奖点击事件
        mCenterContainer.setOnClickListener {
            //开始点击抽奖
            mRotateListener?.rotateBefore()
        }

    }

    fun setRotateListener(mRotateListener: LuckRotateListener?) {
        this.mRotateListener = mRotateListener
        mWheelLuckSpanView.setRotateListener(mRotateListener)
    }

    fun startRotate() {
        mWheelLuckSpanView.startReward()
    }

    fun updateRewardResult(position: Int) {
        mWheelLuckSpanView.setRewardPosition(position)
    }

    /**
     * 更新中间指针背景图片
     */
    fun updateCenterViewBackgroundResource(@DrawableRes res: Int) {
        mCenterContainer.setBackgroundResource(res)
    }

    /**
     * 更新指针中间的图标
     */
    fun updateRewardImageResource(@DrawableRes res: Int) {
        mRewardImgView.setBackgroundResource(res)
    }

    /**
     * 更新抽奖次数
     */
    fun updateRewardCountText(count: Int) {
        mRewardTextView.text = String.format("%d次机会", count)
    }

    /**
     * 是否允许抽奖
     */
    fun enableReward(enabled: Boolean) {

    }


}