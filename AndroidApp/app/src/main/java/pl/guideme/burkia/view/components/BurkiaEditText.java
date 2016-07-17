package pl.guideme.burkia.view.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

import pl.guideme.burkia.R;

public class BurkiaEditText extends EditText {
    public BurkiaEditText(final Context context){
        super(context);
    }

    public BurkiaEditText (final Context context, final AttributeSet attrs){
        super(context, attrs);

        if(!isInEditMode()){
            initTextView(context, attrs);
        }
    }

    public BurkiaEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(!isInEditMode()){
            initTextView(context, attrs);
        }
    }

    private void initTextView(final Context context, final AttributeSet attrs){
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomFontView);
        String customFont = attributes.getString(R.styleable.CustomFontView_customFont);
        setText(getText());
        if(customFont!=null){
            Typeface tf = getTypeface(context, customFont);
            setTypeface(tf);
        }
        attributes.recycle();
    }

    private Typeface getTypeface(final Context context, final String customFontPath) {
        return Typeface.createFromAsset(context.getAssets(), customFontPath);
    }

    public void setTypeFace(Context con, String font){
        if(font!=null){
            Typeface tf = getTypeface(con, font);
            setTypeface(tf);
        }
    }
}
