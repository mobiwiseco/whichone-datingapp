package akilliyazilim.whichone;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import model.User;
import task.NewUserTask;
import ui.MatchFragment;
import ui.NavigationDrawerCallbacks;
import ui.NavigationDrawerFragment;
import util.ApplicationPreferences;


public class WhichoneActivity extends ActionBarActivity implements NavigationDrawerCallbacks{

    private Toolbar mToolbar;
    private NavigationDrawerFragment mNavigationDrawerFragment;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whichone);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            mToolbar.setTitle(getResources().getString(R.string.whichone));
            mToolbar.setTitleTextColor(getResources().getColor(R.color.color_main));
        }

        User user = ApplicationPreferences.getInstance(getApplicationContext()).getUser();

        if(user!=null){
            NewUserTask task = new NewUserTask(user,getApplicationContext()){
                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.container, MatchFragment.newInstance())
                                .commit();
                }
            };
            task.execute();
        }

        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_drawer);
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);
    }



    @Override
    public void onNavigationDrawerItemSelected(int position) {


    }


}
