package view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.facebook.widget.LoginButton;

/**
 * Created by mertsimsek on 13/01/15.
 */
public class RobotoFacebookButton extends LoginButton{

    public RobotoFacebookButton(Context context) {
        super(context);
        init();
    }

    public RobotoFacebookButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RobotoFacebookButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getResources().getAssets(), "robotothin.ttf");
        setTypeface(tf);
    }

}
