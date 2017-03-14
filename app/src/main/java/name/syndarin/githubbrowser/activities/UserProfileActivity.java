package name.syndarin.githubbrowser.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import name.syndarin.githubbrowser.GitHubBrowserApplication;
import name.syndarin.githubbrowser.R;
import name.syndarin.githubbrowser.entities.User;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

/**
 * Created by vtiahotenkov on 10.03.17.
 */

public class UserProfileActivity extends AppCompatActivity {

    public static final String EXTRA_USER_PROFILE_URL = "extra-user-profile-url";

    @BindView(R.id.imageUserAvatar)
    ImageView imageAvatar;

    @BindView(R.id.textUserName)
    TextView textView;

    @Inject
    OkHttpClient okHttpClient;

    @Inject
    Gson gson;

    Observable observableContentLoader;
    Disposable subscriptionContentLoader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);
        ((GitHubBrowserApplication) getApplication()).getNetworkComponent().inject(this);

        String userProfileUrl = getIntent().getStringExtra(EXTRA_USER_PROFILE_URL);

        if (TextUtils.isEmpty(userProfileUrl)) {
            finish();
            return;
        }

        observableContentLoader = Observable.fromCallable(() -> {
                    Timber.d("Load user info");
                    Request request = new Request.Builder().get().url(userProfileUrl).build();
                    Call call = okHttpClient.newCall(request);
                    return call.execute();
                })
                .subscribeOn(Schedulers.io())
                .map(response -> response.body().string())
                .map(userAsJson -> gson.fromJson(userAsJson, User.class))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(user -> {
                    textView.setText(user.getName());
                    Glide.with(this).load(user.getAvatarUrl()).into(imageAvatar);
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
