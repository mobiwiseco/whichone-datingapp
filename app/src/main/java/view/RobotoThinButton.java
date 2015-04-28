package view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by mertsimsek on 13/01/15.
 */
public class RobotoThinButton extends Button {

    public RobotoThinButton(Context context) {
        super(context);
        init();
    }

    public RobotoThinButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RobotoThinButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getResources().getAssets(), "robotothin.ttf");
        setTypeface(tf);
    }
}
