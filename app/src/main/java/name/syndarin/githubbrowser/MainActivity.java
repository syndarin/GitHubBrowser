package name.syndarin.githubbrowser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.jakewharton.rxbinding2.widget.RxTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import name.syndarin.githubbrowser.adapters.SearchResultAdapter;
import name.syndarin.githubbrowser.entities.UserSearchResult;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity {

    private static final String tag = MainActivity.class.getSimpleName();

    @BindView(R.id.edit_username_input)
    EditText editSearchInput;

    @BindView(R.id.recycler_search_results)
    RecyclerView recyclerSearchResults;

    OkHttpClient okHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        okHttpClient = new OkHttpClient.Builder()
            .addNetworkInterceptor(new StethoInterceptor())
            .build();

        recyclerSearchResults.setLayoutManager(new LinearLayoutManager(this));

        Gson gson = new Gson();

        RxTextView.textChanges(editSearchInput)
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
            .doOnNext(result -> recyclerSearchResults.setAdapter(new SearchResultAdapter(result.getItems())))
            .doOnError(Throwable::printStackTrace)
            .subscribe();
    }
}
