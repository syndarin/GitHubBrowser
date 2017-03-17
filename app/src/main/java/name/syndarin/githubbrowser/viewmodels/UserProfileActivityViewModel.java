package name.syndarin.githubbrowser.viewmodels;

import android.content.Context;
import android.databinding.ObservableField;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import name.syndarin.githubbrowser.databinding.ActivityUserProfileBinding;
import name.syndarin.githubbrowser.models.SearchModel;

/**
 * Created by vtiahotenkov on 17.03.17.
 */

public class UserProfileActivityViewModel {

    @Inject
    SearchModel searchModel;

    @Inject
    Context context;

    Observable observableContentLoader;
    Disposable subscriptionContentLoader;

    public ObservableField<String> username;
    public ObservableField<String> avatarUrl;

    public UserProfileActivityViewModel(ActivityUserProfileBinding binding, String url) {
        username = new ObservableField<>();
        avatarUrl = new ObservableField<>();

        observableContentLoader = Observable.defer(() -> searchModel.getUserProfile(url))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(user -> {
                    username.set(user.getName());
                    avatarUrl.set(user.getAvatarUrl());
                });
    }

    public void onResume() {
        subscriptionContentLoader = observableContentLoader.subscribe();
    }

    public void onPause() {
        subscriptionContentLoader.dispose();
    }
}
