package akilliyazilim.whichone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import ui.LoginFragment;

/**
 * Created by mertsimsek on 10/01/15.
 */
public class LoginActivity extends FragmentActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_login, LoginFragment.newInstance())
                    .commit();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
