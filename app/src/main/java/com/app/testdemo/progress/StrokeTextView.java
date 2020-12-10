package com.app.testdemo.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;

import com.app.testdemo.R;

public class StrokeTextView extends AppCompatTextView {
    private TextView outlineTextView;

    public StrokeTextView(Context context) {
        super(context);

        outlineTextView = new TextView(context);
        init(null);
    }

    public StrokeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        outlineTextView = new TextView(context, attrs);
        init(attrs);
    }

    public StrokeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        outlineTextView = new TextView(context, attrs, defStyle);
        init(attrs);
    }

    public void init(AttributeSet attrs) {
        int strokeColor = Color.RED;
        if(null != attrs) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.StrokeTextViewStyle);
            strokeColor = a.getColor(R.styleable.StrokeTextViewStyle_strokeColor,Color.RED);
            a.recycle();
        }
        TextPaint paint = outlineTextView.getPaint();
        paint.setStrokeWidth(15);// 描边宽度
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        outlineTextView.setTextColor(strokeColor);// 描边颜色
        outlineTextView.setGravity(getGravity());
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        super.setLayoutParams(params);
        outlineTextView.setLayoutParams(params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 设置轮廓文字
        CharSequence outlineText = outlineTextView.getText();
        if (outlineText == null || !outlineText.equals(this.getText())) {
            outlineTextView.setText(getText());
            postInvalidate();
        }
        outlineTextView.measure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        outlineTextView.layout(left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        outlineTextView.draw(canvas);
        super.onDraw(canvas);
    }
}
