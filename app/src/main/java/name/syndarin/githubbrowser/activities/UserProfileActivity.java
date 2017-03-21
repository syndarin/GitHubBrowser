package name.syndarin.githubbrowser.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import name.syndarin.githubbrowser.R;

/**
 * Created by vtiahotenkov on 10.03.17.
 */

public class UserProfileActivity extends AppCompatActivity {

    public static final String EXTRA_USER_PROFILE_URL = "extra-user-profile-url";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        String userProfileUrl = getIntent().getStringExtra(EXTRA_USER_PROFILE_URL);

        if (TextUtils.isEmpty(userProfileUrl)) {
            throw new RuntimeException("Fail fast: user profile url isn't set");
        } else {
            /*ActivityUserProfileBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_user_profile);
            viewModel = new FragmentUserProfileViewModel(binding, userProfileUrl);
            ((GitHubBrowserApplication) getApplication()).getNetworkComponent().inject(viewModel);
            binding.setViewModel(viewModel);*/
        }
    }
}
