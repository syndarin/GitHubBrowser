package name.syndarin.githubbrowser.viewmodels;

import android.content.Context;

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

    public UserProfileActivityViewModel(ActivityUserProfileBinding binding, String url) {

        observableContentLoader = Observable.defer(() -> searchModel.getUserProfile(url))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(user -> {
                    binding.textUserName.setText(user.getName());
                    Glide.with(context).load(user.getAvatarUrl()).into(binding.imageUserAvatar);
                });
    }

    public void onResume() {
        subscriptionContentLoader = observableContentLoader.subscribe();
    }

    public void onPause() {
        subscriptionContentLoader.dispose();
    }
}
