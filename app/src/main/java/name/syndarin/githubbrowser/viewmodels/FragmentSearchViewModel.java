package name.syndarin.githubbrowser.viewmodels;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextWatcher;
import android.view.View;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import name.syndarin.githubbrowser.adapters.SearchResultAdapter;
import name.syndarin.githubbrowser.databinding.BindingSearchFragment;
import name.syndarin.githubbrowser.entities.UserSearchResultItem;
import name.syndarin.githubbrowser.models.SearchModel;
import name.syndarin.githubbrowser.navigation.Navigator;
import name.syndarin.githubbrowser.utils.RxClickListener;
import name.syndarin.githubbrowser.utils.RxInputWatcher;
import timber.log.Timber;

/**
 * Created by vtiahotenkov on 15.03.17.
 */

public class FragmentSearchViewModel {

    @Inject
    SearchModel searchModel;

    @Inject
    Navigator navigator;

    SearchResultAdapter adapter;

    Observable inputSearchTextObservable;
    Disposable inputSearchTextSubscription;

    Observable<UserSearchResultItem> itemClickObservable;
    Disposable itemClickSubscription;

    RxInputWatcher inputWatcher;
    RxClickListener clickListener;

    public FragmentSearchViewModel() {
        adapter = new SearchResultAdapter();
        inputWatcher = new RxInputWatcher();
        clickListener = new RxClickListener();

        Observable<String> buttonSearchClickObservable = clickListener.getSubject()
                .flatMap(s -> Observable.just(inputWatcher.getCachedInput()));

        inputSearchTextObservable = inputWatcher.getOnTextChangedSubject()
                .filter(input -> input.length() >= 3)
                .mergeWith(buttonSearchClickObservable)
                .observeOn(Schedulers.io())
                .map(String::toLowerCase)
                .flatMap(s -> searchModel.searchForUsers(s))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(result -> adapter.updateDataSet(result.getItems()))
                .doOnError(Throwable::printStackTrace);

        itemClickObservable = adapter.getClickEventsSubject()
                .doOnNext(user -> {
                    Timber.d("Start profile activity for user %s", user.getLogin());
                    navigator.showUserProfile(user.getUrl());
                });
    }

    public SearchResultAdapter getAdapter() {
        return adapter;
    }

    public TextWatcher getInputTextWatcher() {
        return inputWatcher;
    }

    public View.OnClickListener getClickListener() {
        return clickListener;
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
