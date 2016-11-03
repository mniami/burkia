package pl.guideme.burkia.view.customviews.loadingbars;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class BlockView extends View {
    private static final int BLOCK_L = 1;
    private static final float BLOCK_SIZE = 135;
    private static final int BLOCK_COUNT = 3;

    private int mBlockType;
    private Paint mPaint;

    private RectF firstRec;
    private RectF secondRec;
    private RectF thirdRec;
    private RectF fourthRec;

    public BlockView(Context context) {
        super(context);
    }

    public BlockView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BlockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (mBlockType != BLOCK_L) {
            firstRec = createRect(0, 0);
            secondRec = createRect(theSame(firstRec), bottom(firstRec));
            thirdRec = createRect(theSame(secondRec), bottom(secondRec));
            fourthRec = createRect(right(thirdRec), theSameTop(thirdRec));
        } else {
            firstRec = createRect(getBlockSize(), getBlockSize());
            secondRec = createRect(theSame(firstRec), bottom(firstRec));
            thirdRec = createRect(theSame(secondRec), bottom(secondRec));
            fourthRec = createRect(left(thirdRec), theSameTop(thirdRec));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (firstRec != null) {
            canvas.drawRect(firstRec, mPaint);
            canvas.drawRect(secondRec, mPaint);
            canvas.drawRect(thirdRec, mPaint);
            canvas.drawRect(fourthRec, mPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int) BLOCK_SIZE, (int) BLOCK_SIZE);
    }

    public void init(int blockType) {
        mBlockType = blockType;
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
    }

    private float left(RectF rectF) {
        return rectF.left - getBlockSize();
    }

    private RectF createRect(float left, float top) {
        RectF rectF = new RectF(left, top, left + getBlockSize(), top + getBlockSize());
        return rectF;
    }

    private float theSame(RectF rectF) {
        return rectF.left;
    }

    private float bottom(RectF rectF) {
        return rectF.bottom + getBlockOffset();
    }

    private float right(RectF rectF) {
        return rectF.right + getBlockOffset();
    }

    private float theSameTop(RectF rectF) {
        return rectF.top;
    }

    private float getBlockSize() {
        return getBottom() / BLOCK_COUNT - getBlockOffset();
    }

    private float getBlockOffset() {
        return getBottom() / 20;
    }
}
