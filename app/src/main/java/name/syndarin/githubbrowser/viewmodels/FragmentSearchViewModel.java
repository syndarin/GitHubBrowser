package name.syndarin.githubbrowser.viewmodels;

import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import name.syndarin.githubbrowser.activities.UserProfileActivity;
import name.syndarin.githubbrowser.adapters.SearchResultAdapter;
import name.syndarin.githubbrowser.databinding.BindingSearchFragment;
import name.syndarin.githubbrowser.entities.UserSearchResultItem;
import name.syndarin.githubbrowser.models.SearchModel;
import timber.log.Timber;

/**
 * Created by vtiahotenkov on 15.03.17.
 */

public class FragmentSearchViewModel {

    @BindingAdapter({"adapter"})
    public static void setAdapter(RecyclerView view, RecyclerView.Adapter adapter) {
        Timber.d("Request for adapter!");
        view.setAdapter(adapter);
        view.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    @Inject
    SearchModel searchModel;

    @Inject
    Context context;

    SearchResultAdapter adapter;

    Observable inputSearchTextObservable;
    Disposable inputSearchTextSubscription;

    Observable<UserSearchResultItem> itemClickObservable;
    Disposable itemClickSubscription;

    public FragmentSearchViewModel(BindingSearchFragment binding) {
        adapter = new SearchResultAdapter();

        Observable<CharSequence> buttonSearchClickObservable = RxView.clicks(binding.buttonSearch)
                .flatMap(s -> Observable.just(binding.editUsernameInput.getText().toString()));

        inputSearchTextObservable = RxTextView.textChanges(binding.editUsernameInput)
                .filter(input -> input.length() >= 3)
                .mergeWith(buttonSearchClickObservable)
                .observeOn(Schedulers.io())
                .map(input -> input.toString().toLowerCase())
                .flatMap(s -> searchModel.searchForUsers(s))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(result -> adapter.updateDataSet(result.getItems()))
                .doOnError(Throwable::printStackTrace);

        itemClickObservable = adapter.getClickEventsSubject()
                .doOnNext(user -> {
                    Timber.d("Start profile activity for user %s", user.getLogin());
                    Intent intent = new Intent(context, UserProfileActivity.class);
                    intent.putExtra(UserProfileActivity.EXTRA_USER_PROFILE_URL, user.getUrl());
                    context.startActivity(intent);
                });
    }

    public SearchResultAdapter getAdapter() {
        return adapter;
    }

    public void onResume() {
        inputSearchTextSubscription = inputSearchTextObservable.subscribe();
        itemClickSubscription = itemClickObservable.subscribe();
    }

    public void onPause() {
        inputSearchTextSubscription.dispose();
        itemClickSubscription.dispose();
    }
}
