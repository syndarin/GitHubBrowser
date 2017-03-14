package name.syndarin.githubbrowser.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import name.syndarin.githubbrowser.GitHubBrowserApplication;
import name.syndarin.githubbrowser.R;
import name.syndarin.githubbrowser.adapters.SearchResultAdapter;
import name.syndarin.githubbrowser.entities.UserSearchResult;
import name.syndarin.githubbrowser.entities.UserSearchResultItem;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.edit_username_input)
    EditText editSearchInput;

    @BindView(R.id.recycler_search_results)
    RecyclerView recyclerSearchResults;

    @BindView(R.id.buttonSearch)
    Button buttonSearch;

    @Inject OkHttpClient okHttpClient;

    @Inject Gson gson;

    Observable inputSearchTextObservable;
    Disposable inputSearchTextSubscription;

    Observable<UserSearchResultItem> itemClickObservable;
    Disposable itemClickSubscription;

    SearchResultAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ((GitHubBrowserApplication) getApplication()).getNetworkComponent().inject(this);

        adapter = new SearchResultAdapter();
        recyclerSearchResults.setAdapter(adapter);

        recyclerSearchResults.setLayoutManager(new LinearLayoutManager(this));

        Observable<CharSequence> buttonSearchClickObservable = RxView.clicks(buttonSearch)
                .flatMap(s -> Observable.just(editSearchInput.getText().toString()));

        inputSearchTextObservable = RxTextView.textChanges(editSearchInput)
                .mergeWith(buttonSearchClickObservable)
                .filter(input -> input.length() >= 3)
                .observeOn(Schedulers.io())
                .map(input -> input.toString().toLowerCase())

                .flatMap(s -> {
                    String url = "https://api.github.com/search/users?q=" + s;
                    Request request = new Request.Builder().url(url).build();
                    return Observable.just(okHttpClient.newCall(request).execute());
                })

                .map(response -> gson.fromJson(response.body().string(), UserSearchResult.class))
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
