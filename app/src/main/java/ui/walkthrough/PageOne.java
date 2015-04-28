package ui.walkthrough;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import akilliyazilim.whichone.R;

/**
 * Created by mertsimsek on 13/01/15.
 */
public class PageOne extends Fragment{

    public PageOne() {
    }

    public static PageOne newInstance(){
        return new PageOne();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_walkthrough_3,container,false);
        return v;
    }
}
