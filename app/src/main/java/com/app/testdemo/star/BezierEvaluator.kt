package com.app.testdemo.star

import android.animation.TypeEvaluator
import android.graphics.PointF

class BezierEvaluator(val point1: PointF, val point2: PointF) : TypeEvaluator<PointF> {

    private val point: PointF by lazy {
        PointF()
    }

    override fun evaluate(t: Float, startValue: PointF, endValue: PointF): PointF {
        point.x =
            startValue.x * (1 - t) * (1 - t) * (1 - t) + 3f * point1.x * t * (1 - t) * (1 - t) + 3f * point2.x * t * t * (1 - t) + endValue.x * t * t * t
        point.y =
            startValue.y * (1 - t) * (1 - t) * (1 - t) + 3f * point1.y * t * (1 - t) * (1 - t) + 3f * point2.y * t * t * (1 - t) + endValue.y * t * t * t
        return point
    }

}