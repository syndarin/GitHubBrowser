package name.syndarin.githubbrowser.dependency;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Created by vtiahotenkov on 13.03.17.
 */

@Module
public class NetworkModule {

    @Provides @Singleton
    public OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
    }

    @Provides @Singleton
    public Gson getJsonParser() {
        return new Gson();
    }

}
