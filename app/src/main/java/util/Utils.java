package util;

import android.app.Activity;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.Calendar;

import akilliyazilim.whichone.R;
import view.RobotoBlackTextview;
import view.RobotoLightTextView;
import view.RobotoRegularTextView;

/**
 * Created by mertsimsek on 10/01/15.
 */
public class Utils {

    public static int dateToAgeConverter(String date) {
        String year = date.substring(date.lastIndexOf('/') + 1);
        return Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(year);
    }

    public static MaterialDialog createInfoDialog(Activity activity,String header,String content, String action) {
        final MaterialDialog dialog = new MaterialDialog.Builder(activity)
                .customView(R.layout.dialog_info)
                .build();

        RobotoRegularTextView textview_header = (RobotoRegularTextView) dialog.getCustomView().findViewById(R.id.textview_info_dialog_header);
        RobotoLightTextView textview_content = (RobotoLightTextView) dialog.getCustomView().findViewById(R.id.textview_info_dialog_content);
        RobotoBlackTextview textview_action = (RobotoBlackTextview) dialog.getCustomView().findViewById(R.id.textview_info_dialog_action);

        textview_header.setText(header);
        textview_content.setText(content);
        textview_action.setText(action);

        textview_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        return dialog;
    }

}
