package view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by mertsimsek on 13/01/15.
 */
public class RobotoThinTextView extends TextView{

    public RobotoThinTextView(Context context) {
        super(context);
        init();
    }

    public RobotoThinTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RobotoThinTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getResources().getAssets(), "robotothin.ttf");
        setTypeface(tf);
    }
}


