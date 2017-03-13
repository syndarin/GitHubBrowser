package name.syndarin.githubbrowser.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import timber.log.Timber;

/**
 * Created by vtiahotenkov on 10.03.17.
 */

public class UserProfileActivity extends AppCompatActivity {

    public static final String EXTRA_USER_PROFILE_URL = "extra-user-profile-url";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d("onCreate");
    }
}
