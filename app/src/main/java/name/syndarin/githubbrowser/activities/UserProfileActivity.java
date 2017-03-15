package name.syndarin.githubbrowser.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import name.syndarin.githubbrowser.GitHubBrowserApplication;
import name.syndarin.githubbrowser.R;
import name.syndarin.githubbrowser.databinding.ActivityUserProfileBinding;
import name.syndarin.githubbrowser.models.SearchModel;
import timber.log.Timber;

/**
 * Created by vtiahotenkov on 10.03.17.
 */

public class UserProfileActivity extends AppCompatActivity {

    public static final String EXTRA_USER_PROFILE_URL = "extra-user-profile-url";

    @Inject
    SearchModel searchModel;

    Observable observableContentLoader;
    Disposable subscriptionContentLoader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityUserProfileBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_user_profile);

        ((GitHubBrowserApplication) getApplication()).getNetworkComponent().inject(this);

        String userProfileUrl = getIntent().getStringExtra(EXTRA_USER_PROFILE_URL);

        if (TextUtils.isEmpty(userProfileUrl)) {
            finish();
            return;
        }

        observableContentLoader = searchModel.getUserProfile(userProfileUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(user -> {
                    binding.textUserName.setText(user.getName());
                    Glide.with(this).load(user.getAvatarUrl()).into(binding.imageUserAvatar);
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Timber.d("onResume");
        subscriptionContentLoader = observableContentLoader.subscribe();
    }

    @Override
    protected void onPause() {
        super.onPause();
        subscriptionContentLoader.dispose();
    }
}
