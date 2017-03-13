package name.syndarin.githubbrowser.dependency;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vtiahotenkov on 13.03.17.
 */

@Module
public class ParsingModule {

    @Provides @Singleton
    public Gson getJsonParser() {
        return new Gson();
    }

}
