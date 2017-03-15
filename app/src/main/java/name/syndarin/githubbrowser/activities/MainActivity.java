package name.syndarin.githubbrowser.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import name.syndarin.githubbrowser.GitHubBrowserApplication;
import name.syndarin.githubbrowser.R;
import name.syndarin.githubbrowser.adapters.SearchResultAdapter;
import name.syndarin.githubbrowser.databinding.ActivityMainBinding;
import name.syndarin.githubbrowser.entities.UserSearchResultItem;
import name.syndarin.githubbrowser.models.SearchModel;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Inject
    SearchModel searchModel;

    Observable inputSearchTextObservable;
    Disposable inputSearchTextSubscription;

    Observable<UserSearchResultItem> itemClickObservable;
    Disposable itemClickSubscription;

    SearchResultAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        ((GitHubBrowserApplication) getApplication()).getNetworkComponent().inject(this);

        adapter = new SearchResultAdapter();
        binding.recyclerSearchResults.setAdapter(adapter);
        binding.recyclerSearchResults.setLayoutManager(new LinearLayoutManager(this));

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
                    Intent intent = new Intent(this, UserProfileActivity.class);
                    intent.putExtra(UserProfileActivity.EXTRA_USER_PROFILE_URL, user.getUrl());
                    startActivity(intent);
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        inputSearchTextSubscription = inputSearchTextObservable.subscribe();
        itemClickSubscription = itemClickObservable.subscribe();
    }

    @Override
    protected void onPause() {
        super.onPause();
        inputSearchTextSubscription.dispose();
        itemClickSubscription.dispose();
    }
}
