package name.syndarin.githubbrowser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String tag = MainActivity.class.getSimpleName();

    @BindView(R.id.edit_username_input)
    EditText editSearchInput;

    OkHttpClient okHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        okHttpClient = new OkHttpClient.Builder()
            .addNetworkInterceptor(new StethoInterceptor())
            .build();

        RxTextView.textChanges(editSearchInput)
            .subscribeOn(Schedulers.io())

            .filter(input -> {
                Log.i(tag, "filter " + Thread.currentThread().getName());
                return input.length() >= 3;
            })

            .observeOn(Schedulers.io())

            .map(input -> {
                Log.i(tag, "map " + Thread.currentThread().getName());
                return input.toString().toLowerCase();
            })

            .flatMap((Function<String, ObservableSource<?>>) s -> {
                Log.i(tag, "flatMap " + Thread.currentThread().getName());
                String url = "https://api.github.com/search/users?q=" + s;
                Request request = new Request.Builder().url(url).build();
                return Observable.just(okHttpClient.newCall(request).execute());
            })

            .cast(Response.class)

            .doOnNext(result -> {
                Log.i(tag, "doOnNext " + Thread.currentThread().getName());
                Log.i(tag, String.valueOf(result.body().string()));
            })

            .doOnError(Throwable::printStackTrace)
            .subscribe();
    }
}
