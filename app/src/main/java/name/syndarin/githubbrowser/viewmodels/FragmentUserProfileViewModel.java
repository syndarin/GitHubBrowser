package name.syndarin.githubbrowser.viewmodels;

import android.app.Application;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import name.syndarin.githubbrowser.R;
import name.syndarin.githubbrowser.models.SearchModel;

/**
 * Created by vtiahotenkov on 17.03.17.
 */

public class FragmentUserProfileViewModel extends BaseObservable {

    @Inject
    SearchModel searchModel;

    @Inject
    Application application;

    Observable observableContentLoader;
    Disposable subscriptionContentLoader;

    public ObservableField<String> username;
    public ObservableField<String> avatarUrl;

    public ObservableField<String> followersCount;
    public ObservableField<String> followingCount;
    public ObservableField<String> reposCount;
    public ObservableField<String> gistsCount;

    public FragmentUserProfileViewModel(String url) {
        username = new ObservableField<>();
        avatarUrl = new ObservableField<>();
        followersCount = new ObservableField<>();
        followingCount = new ObservableField<>();
        reposCount = new ObservableField<>();
        gistsCount = new ObservableField<>();

        observableContentLoader = Observable.defer(() -> searchModel.getUserProfile(url))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(user -> {
                    username.set(user.getName());
                    avatarUrl.set(user.getAvatarUrl());
                    followersCount.set(application.getString(R.string.user_profile_followers_template, user.getFollowers()));
                    followingCount.set(application.getString(R.string.user_profile_following_template, user.getFollowing()));
                    reposCount.set(application.getString(R.string.user_profile_repos_template, user.getPublicRepos()));
                    gistsCount.set(application.getString(R.string.user_profile_gists_template, user.getPublicGists()));
                });
    }

    public void onResume() {
        subscriptionContentLoader = observableContentLoader.subscribe();
    }

    public void onPause() {
        subscriptionContentLoader.dispose();
    }
}
