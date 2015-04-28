package view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by mertsimsek on 11/01/15.
 */
public class RobotoRegularButton extends Button {
    public RobotoRegularButton(Context context) {
        super(context);
        init();
    }

    public RobotoRegularButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RobotoRegularButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getResources().getAssets(), "robotoregular.ttf");
        setTypeface(tf);
    }
}
