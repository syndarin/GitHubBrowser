package name.syndarin.githubbrowser.dependency;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import name.syndarin.githubbrowser.models.SearchModel;
import okhttp3.OkHttpClient;

/**
 * Created by vtiahotenkov on 15.03.17.
 */

@Module
public class ModelModule {

    @Provides @Singleton
    public SearchModel getSearchModel(OkHttpClient okHttpClient, Gson gson) {
        return new SearchModel(okHttpClient, gson);
    }

}
