package name.syndarin.githubbrowser.dependency;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Component;
import name.syndarin.githubbrowser.activities.MainActivity;
import okhttp3.OkHttpClient;

/**
 * Created by vtiahotenkov on 13.03.17.
 */

@Singleton
@Component(modules = {NetworkModule.class, ParsingModule.class})
public interface NetworkComponent {

    OkHttpClient okHttpClient();

    Gson gson();

    void inject(MainActivity activity);


}
