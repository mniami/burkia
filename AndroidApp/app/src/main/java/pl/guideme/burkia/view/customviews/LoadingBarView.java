package pl.guideme.burkia.view.customviews;

import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import pl.guideme.burkia.view.customviews.loadingbars.BlockView;


public class LoadingBarView extends FrameLayout {
    private static final int BLOCK_SIZE = 125;
    private BlockView mBlockView;
    private int index;

    public LoadingBarView(Context context) {
        super(context);
        init();
    }

    public LoadingBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (mBlockView != null) {
                mBlockView.setRotation(mBlockView.getRotation() + 90);
            }
        }
        return super.onTouchEvent(event);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        setMeasuredDimension(BLOCK_SIZE * 2, BLOCK_SIZE * 3);
//    }

    public void init() {
        mBlockView = createBlock();
    }

    private BlockView createBlock() {
        mBlockView = new BlockView(getContext());
        mBlockView.init(index);
        addView(mBlockView, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mBlockView.setY(-BLOCK_SIZE);
        mBlockView.animate().y(0).setDuration(3000).setListener(new BlockAnimationAdapter() {
            @Override
            public void onAnimationEnd(Animator animator) {
                createBlock();
            }
        }).start();
        return mBlockView;
    }
}
