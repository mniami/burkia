package pl.guideme.burkia.view.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class Hamburger extends RelativeLayout {
    private Paint mTextPaint;
    private String mText;

    public Hamburger(Context context) {
        super(context);
        init();
    }

    public Hamburger(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Hamburger(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Rect textBounds = new Rect();
        mTextPaint.getTextBounds(mText, 0, mText.length(), textBounds);

        setMeasuredDimension(textBounds.width() + getPaddingLeft() + getPaddingRight(), textBounds.height() + getPaddingTop() + getPaddingBottom());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawText(mText, 0, mText.length(), getPaddingLeft(), getPaddingTop(), mTextPaint);
    }

    private void init() {
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(20f);
        mText = "Hello!";
    }
}
