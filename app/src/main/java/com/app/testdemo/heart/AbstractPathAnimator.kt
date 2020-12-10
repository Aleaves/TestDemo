package com.app.testdemo.heart

import java.util.*
import android.content.res.TypedArray
import android.view.View
import java.util.concurrent.atomic.AtomicInteger
import android.graphics.Path
import android.view.ViewGroup
import com.app.testdemo.R


abstract class AbstractPathAnimator {
    private var mRandom: Random
    protected var mConfig: Config

    constructor(mConfig: Config) {
        this.mConfig = mConfig
        mRandom = Random()
    }

    fun randomRotation(): Float = mRandom.nextFloat() * 28.6F - 14.3F

    fun createPath(counter: AtomicInteger, view: View, factor: Int): Path {
        var factor = factor
        val r = mRandom
        var x = r.nextInt(mConfig.xRand)
        var x2 = r.nextInt(mConfig.xRand)
        val y = view.height - mConfig.initY
        var y2 =
            counter.toInt() * 15 + mConfig.animLength * factor + r.nextInt(mConfig.animLengthRand)
        factor = y2 / mConfig.bezierFactor
        x = mConfig.xPointFactor + x
        x2 = mConfig.xPointFactor + x2
        val y3 = y - y2
        y2 = y - y2 / 2
        val p = Path()
        p.moveTo(mConfig.initX.toFloat(), y.toFloat())
        p.cubicTo(
            mConfig.initX.toFloat(),
            (y - factor).toFloat(),
            x.toFloat(),
            (y2 + factor).toFloat(),
            x.toFloat(),
            y2.toFloat()
        )
        p.moveTo(x.toFloat(), y2.toFloat())
        p.cubicTo(
            x.toFloat(),
            (y2 - factor).toFloat(),
            x2.toFloat(),
            (y3 + factor).toFloat(),
            x2.toFloat(),
            y3.toFloat()
        )
        return p
    }

    abstract fun start(child: View, parent: ViewGroup)

    class Config {
        var initX: Int = 0
        var initY: Int = 0
        var xRand: Int = 0
        var animLengthRand: Int = 0
        var bezierFactor: Int = 0
        var xPointFactor: Int = 0
        var animLength: Int = 0
        var heartWidth: Int = 0
        var heartHeight: Int = 0
        var animDuration: Int = 0

        companion object {

            fun fromTypeArray(
                typedArray: TypedArray,
                x: Float,
                y: Float,
                pointx: Int,
                heartWidth: Int,
                heartHeight: Int
            ): Config {
                val config = Config()
                val res = typedArray.resources
                config.initX = typedArray.getDimension(
                    R.styleable.HeartLayout_initX,
                    x
                ).toInt()
                config.initY = typedArray.getDimension(
                    R.styleable.HeartLayout_initY,
                    y
                ).toInt()
                config.xRand = typedArray.getDimension(
                    R.styleable.HeartLayout_xRand,
                    res.getDimensionPixelOffset(R.dimen.heart_anim_bezier_x_rand).toFloat()
                ).toInt()
                config.animLength = typedArray.getDimension(
                    R.styleable.HeartLayout_animLength,
                    res.getDimensionPixelOffset(R.dimen.heart_anim_length).toFloat()
                ).toInt()//动画长度
                config.animLengthRand = typedArray.getDimension(
                    R.styleable.HeartLayout_animLengthRand,
                    res.getDimensionPixelOffset(R.dimen.heart_anim_length_rand).toFloat()
                ).toInt()
                config.bezierFactor = typedArray.getInteger(
                    R.styleable.HeartLayout_bezierFactor,
                    res.getInteger(R.integer.heart_anim_bezier_factor)
                )
                config.xPointFactor = pointx
                //            config.heartWidth = (int) typedArray.getDimension(R.styleable.HeartLayout_heart_width,
                //                    res.getDimensionPixelOffset(R.dimen.heart_size_width));//动画图片宽度
                //            config.heartHeight = (int) typedArray.getDimension(R.styleable.HeartLayout_heart_height,
                //                    res.getDimensionPixelOffset(R.dimen.heart_size_height));//动画图片高度
                config.heartWidth = heartWidth
                config.heartHeight = heartHeight
                config.animDuration = typedArray.getInteger(
                    R.styleable.HeartLayout_anim_duration,
                    res.getInteger(R.integer.anim_duration)
                )//持续期
                return config
            }
        }
    }
}