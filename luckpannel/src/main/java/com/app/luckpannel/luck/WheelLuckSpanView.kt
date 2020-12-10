package com.app.luckpannel.luck

import android.animation.*
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.app.luckpannel.R
import java.lang.Exception
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

/**
 * 转盘抽奖view
 */
class WheelLuckSpanView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr) {

    //视图的大小
    private var mWidth: Int = 0

    //每一个区域背景画笔
    private lateinit var mBgPaint: Paint

    //每一个区域上文字画笔
    private lateinit var mTextPaint: Paint

    private lateinit var mDescPaint: Paint

    //每一个区域线条画笔
    private lateinit var mLinePaint: Paint

    //圆环背景
    private lateinit var mCircleBg: Bitmap

    //中心点横坐标
    private var mCenter: Int = 0

    //圆形半径
    private var mRadius: Int = 0

    //每一个扇形的角度
    private var mAngle: Float = 0f

    //分成多少个区域
    private var mTypeNum: Int = 0

    //最低圈数 默认值3 也就是说每次旋转都会最少转3圈
    private var mMinTimes: Int = 3

    //每个扇形旋转的时间
    private var mVarTime = 75

    private var mRotateListener: LuckRotateListener? = null

    //每一块背景颜色数组
    private var mColors = arrayOf(
        Color.parseColor("#FFEAC5"),
        Color.parseColor("#FFF9F1"),
        Color.parseColor("#FFEAC5"),
        Color.parseColor("#FFF9F1"),
        Color.parseColor("#FFEAC5"),
        Color.parseColor("#FFF9F1")
    )

    //每一块奖品名字数组
    private var mRewardsText = arrayOf(
        "¥88", "¥66", "¥52", "¥38", "随机", "谢谢"
    )

    //每一块奖品描述文字数组
    private var mDesText = arrayOf(
        "现金奖励", "现金奖励", "现金奖励", "现金奖励", "现金奖励", "参与"
    )

    //抽中的奖品
    private var mRewardPosition = -1


    init {
        setBackgroundColor(Color.TRANSPARENT)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.WheelLuckViewStyle)
        try {
            mTypeNum = typedArray.getInteger(R.styleable.WheelLuckViewStyle_typeNum, 0)
            //每一个扇形的角度
            mAngle = (360.0 / mTypeNum).toFloat()
            val mHuanImgRes = typedArray.getResourceId(
                R.styleable.WheelLuckViewStyle_circleImg,
                R.mipmap.icon_luck_bg
            )
            mCircleBg = BitmapFactory.decodeResource(context.resources, mHuanImgRes)
        } catch (e: Exception) {

        } finally {
            typedArray.recycle()
        }
        mBgPaint = Paint().apply {
            style = Paint.Style.FILL
            isAntiAlias = true
            isDither = true
        }
        mLinePaint = Paint().apply {
            style = Paint.Style.FILL
            isAntiAlias = true
            isDither = true
            strokeWidth = 5f
            color = Color.parseColor("#FFFFA084")
        }
        mDescPaint = Paint().apply {
            style = Paint.Style.FILL
            isAntiAlias = true
            isDither = true
            color = Color.parseColor("#FFF6601D")
            textSize = 24f
            textAlign = Paint.Align.CENTER
        }
        mTextPaint = Paint().apply {
            style = Paint.Style.FILL
            isAntiAlias = true
            isDither = true
            color = Color.parseColor("#FFF6601D")
            textSize = 66f
            textAlign = Paint.Align.CENTER
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 从最上面开始绘制扇形
        var startAngle = -mAngle / 2 - 90

        //绘制每一个区域
        for (i in 0 until mTypeNum) {
            //画扇形
            drawArc(startAngle, i, canvas)

            //画每一个区域的分割线
            if (i != 0) {
                drawLines(startAngle, canvas)
            }

            drawTopText(mRewardsText[i], i, startAngle, canvas)

            //画描述文字
            drawDesText(mDesText[i], i, startAngle, canvas)

            startAngle += mAngle
        }


        //画第一条  防止线条被扇形区域挡住
        drawLines(-mAngle / 2 - 90, canvas)
        //绘制圆环
        //drawCircle(canvas)

    }

    private fun drawTopText(string: String, i: Int, startAngle: Float, canvas: Canvas) {
        //决定文字离中心的距离
        val p = 0.8

        val startX =
            (cos(startAngle * Math.PI / 180) * mRadius.toDouble() * p + mCenter).toFloat()
        val starY =
            (sin(startAngle * Math.PI / 180) * mRadius.toDouble() * p + mCenter).toFloat()

        val stopX =
            (cos((startAngle + mAngle) * Math.PI / 180) * mRadius.toDouble() * p + mCenter).toFloat()
        val stopY =
            (sin((startAngle + mAngle) * Math.PI / 180) * mRadius.toDouble() * p + mCenter).toFloat()

        val path = Path()

        path.moveTo(startX, starY)
        path.lineTo(stopX, stopY)
        if (i == mTypeNum - 1) {
            mTextPaint.textSize = 42f
        } else {
            mTextPaint.textSize = 66f
        }
        canvas.drawTextOnPath(string, path, 0f, 0f, mTextPaint)
    }

    private fun drawDesText(string: String, i: Int, startAngle: Float, canvas: Canvas) {
        //决定文字离中心的距离
        val p = 0.6

        val startX =
            (cos(startAngle * Math.PI / 180) * mRadius.toDouble() * p + mCenter).toFloat()
        val starY =
            (sin(startAngle * Math.PI / 180) * mRadius.toDouble() * p + mCenter).toFloat()

        val stopX =
            (cos((startAngle + mAngle) * Math.PI / 180) * mRadius.toDouble() * p + mCenter).toFloat()
        val stopY =
            (sin((startAngle + mAngle) * Math.PI / 180) * mRadius.toDouble() * p + mCenter).toFloat()

        val path = Path()

        path.moveTo(startX, starY)
        path.lineTo(stopX, stopY)
        if (i == mTypeNum - 1) {
            mDescPaint.textSize = 42f
        } else {
            mDescPaint.textSize = 24f
        }
        canvas.drawTextOnPath(string, path, 0f, 0f, mDescPaint)
    }

    private fun drawLines(startAngle: Float, canvas: Canvas) {
        val stopX = (Math.cos(startAngle * Math.PI / 180) * mRadius + mCenter).toFloat()
        val stopY = (Math.sin(startAngle * Math.PI / 180) * mRadius + mCenter).toFloat()
        canvas.drawLine(mCenter.toFloat(), mCenter.toFloat(), stopX, stopY, mLinePaint)
    }

    private fun drawArc(startAngle: Float, i: Int, canvas: Canvas) {
        mBgPaint.color = mColors[i]
        val rect = RectF(
            (mCenter - mRadius).toFloat(),
            (mCenter - mRadius).toFloat(),
            (mCenter + mRadius).toFloat(),
            (mCenter + mRadius).toFloat()
        )
        canvas.drawArc(rect, startAngle, mAngle, true, mBgPaint)
    }

    private fun drawCircle(canvas: Canvas) {
        val mDestRect = Rect(0, 0, mWidth, mWidth)
        canvas.drawBitmap(mCircleBg, null, mDestRect, mBgPaint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //视图是个正方形的 所以有宽就足够了 默认值是800 也就是WRAP_CONTENT的时候
        val desiredWidth = 800

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        val width: Int

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {

            width = widthSize
        } else if (widthMode == MeasureSpec.AT_MOST) {

            width = min(desiredWidth, widthSize)
        } else {

            width = desiredWidth
        }
        //将测得的宽度保存起来
        mWidth = width

        mCenter = mWidth / 2
        //绘制扇形的半径 减掉50是为了防止边界溢出
        mRadius = mWidth / 2 - 85

        setMeasuredDimension(width, width)
    }

    fun setRotateListener(rotateListener: LuckRotateListener?) {
        this.mRotateListener = rotateListener
    }


    private  lateinit var mRotateAnim: ObjectAnimator
    private lateinit var mBeforeAnim: ObjectAnimator

    /**
     * 开始转动
     * pos 位置 1 开始 这里的位置上是按照逆时针递增的 比如当前指的那个选项是第一个  那么他左边的那个是第二个 以此类推
     */
    fun startRotate(pos: Int) {
        //最低圈数是mMinTimes圈
        //left为上次停留的角度
//        var left = 0f
//        if (lastPosition != 0) {
//            left = (lastPosition - 1) * mAngle
//        }
//        val newAngle =
//            (360 * mMinTimes).toFloat() + (pos - 1) * mAngle + currAngle - left

        //计算目前的角度划过的扇形份数
//        val num = ((newAngle - currAngle) / mAngle).toInt()

        val sweepAngle = 360 * mMinTimes + (mTypeNum - pos) * mAngle

        mRotateAnim = ObjectAnimator.ofFloat(this@WheelLuckSpanView, "rotation", 0f, sweepAngle)

        // 动画的持续时间
        mRotateAnim.duration = (mMinTimes * (mTypeNum + mTypeNum - pos) * mVarTime).toLong()
        mRotateAnim.addUpdateListener { animation ->
            //将动画的过程态回调给调用者
            mRotateListener?.rotating(animation)
        }
        mRotateAnim.interpolator = LinearInterpolator()
        mRotateAnim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                //当旋转结束的时候回调当前所选择的内容
                mRotateListener?.rotateEnd(pos, mRewardsText[pos])
                //抽奖结束 重新赋初始值
                mRewardPosition = -1
            }
        })
        // 正式开始启动执行动画
        mRotateAnim.start()
    }

    //抽奖之前 先来一波预热 旋转
    fun startReward() {
        mRewardPosition = -1
        mBeforeAnim =
            ObjectAnimator.ofFloat(this@WheelLuckSpanView, "rotation", 0f, 360f).apply {
                duration = (mTypeNum * mVarTime).toLong()
                interpolator = LinearInterpolator()
                repeatCount = ValueAnimator.INFINITE
            }
        mBeforeAnim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationRepeat(animation: Animator?) {
                super.onAnimationRepeat(animation)
                //循环旋转 如果从服务端获取到结果之后  开始下一轮动画 结束抽奖
                if (mRewardPosition != -1) {
                    mBeforeAnim.cancel()
                    startRotate(mRewardPosition)
                }
            }
        })
        mBeforeAnim.start()
    }

    fun setRewardPosition(i: Int) {
        this.mRewardPosition = i
    }


    fun onDestory(){
        mRotateAnim?.let {
            if(it.isRunning){
                mRotateAnim.cancel()
            }
        }
        mBeforeAnim?.let {
            if(it.isRunning){
                mBeforeAnim.cancel()
            }
        }
    }

}