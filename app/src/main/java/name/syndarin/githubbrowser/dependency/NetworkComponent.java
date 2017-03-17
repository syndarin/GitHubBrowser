package name.syndarin.githubbrowser.dependency;

import android.content.Context;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Component;
import name.syndarin.githubbrowser.activities.MainActivity;
import name.syndarin.githubbrowser.activities.UserProfileActivity;
import name.syndarin.githubbrowser.models.SearchModel;
import name.syndarin.githubbrowser.viewmodels.MainActivityViewModel;
import name.syndarin.githubbrowser.viewmodels.UserProfileActivityViewModel;
import okhttp3.OkHttpClient;

/**
 * Created by vtiahotenkov on 13.03.17.
 */

@Singleton
@Component(modules = {NetworkModule.class, ModelModule.class, ApplicationModule.class})
public interface NetworkComponent {

    SearchModel searchModel();

    Context context();

    void inject(MainActivityViewModel viewModel);

    void inject(UserProfileActivityViewModel viewModel);
}
