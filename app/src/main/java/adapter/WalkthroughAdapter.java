package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import ui.walkthrough.PageOne;
import ui.walkthrough.PageThree;
import ui.walkthrough.PageTwo;

/**
 * Created by mertsimsek on 13/01/15.
 */
public class WalkthroughAdapter extends FragmentStatePagerAdapter {


    public WalkthroughAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int arg0) {
        Fragment fragment = null;
        switch (arg0){
            case 0:
                fragment = PageOne.newInstance();
                break;
            case 1:
                fragment =  PageTwo.newInstance();
                break;
            case 2:
                fragment =  PageThree.newInstance();
                break;
            default:
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

}
