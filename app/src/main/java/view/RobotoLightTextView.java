package view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by muratcanbur on 17/12/14.
 */
public class RobotoLightTextView extends TextView {
    public RobotoLightTextView(Context context) {
        super(context);
        init();
    }

    public RobotoLightTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RobotoLightTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getResources().getAssets(), "robotolight.ttf");
        setTypeface(tf);
    }
}
