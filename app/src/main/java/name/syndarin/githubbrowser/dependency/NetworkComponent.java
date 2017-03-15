package name.syndarin.githubbrowser.dependency;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Component;
import name.syndarin.githubbrowser.activities.MainActivity;
import name.syndarin.githubbrowser.activities.UserProfileActivity;
import name.syndarin.githubbrowser.models.SearchModel;
import okhttp3.OkHttpClient;

/**
 * Created by vtiahotenkov on 13.03.17.
 */

@Singleton
@Component(modules = {NetworkModule.class, ModelModule.class})
public interface NetworkComponent {

    SearchModel searchModel();

    void inject(MainActivity activity);

    void inject(UserProfileActivity activity);
}
