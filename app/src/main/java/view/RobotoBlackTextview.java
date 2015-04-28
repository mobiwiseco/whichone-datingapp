package view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by mertsimsek on 20/01/15.
 */
public class RobotoBlackTextview extends TextView{

    public RobotoBlackTextview(Context context) {
        super(context);
        init();
    }

    public RobotoBlackTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RobotoBlackTextview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getResources().getAssets(), "robotoblack.ttf");
        setTypeface(tf);
    }
}
