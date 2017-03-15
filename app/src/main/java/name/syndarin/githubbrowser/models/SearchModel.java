package name.syndarin.githubbrowser.models;

import com.google.gson.Gson;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import name.syndarin.githubbrowser.entities.User;
import name.syndarin.githubbrowser.entities.UserSearchResult;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import timber.log.Timber;

/**
 * Created by vtiahotenkov on 15.03.17.
 */

public class SearchModel {

    private OkHttpClient okHttpClient;

    private Gson gson;

    public SearchModel(OkHttpClient okHttpClient, Gson gson) {
        this.okHttpClient = okHttpClient;
        this.gson = gson;
    }

    public Observable<UserSearchResult> searchForUsers(String q) throws IOException {
        String url = "https://api.github.com/search/users?q=" + q;
        Request request = new Request.Builder().url(url).build();
        return Observable.just(okHttpClient.newCall(request).execute())
                .map(response -> gson.fromJson(response.body().string(), UserSearchResult.class));
    }

    public Observable<User> getUserProfile(String url) {
        return Observable.fromCallable(() -> {
            Request request = new Request.Builder().get().url(url).build();
            Call call = okHttpClient.newCall(request);
            return call.execute();
        })
        .subscribeOn(Schedulers.io())
        .map(response -> response.body().string())
        .map(userAsJson -> gson.fromJson(userAsJson, User.class));
    }
}
