package name.syndarin.githubbrowser.dependency;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import name.syndarin.githubbrowser.models.SearchModel;
import name.syndarin.githubbrowser.viewmodels.SearchFragmentViewModel;
import name.syndarin.githubbrowser.viewmodels.UserProfileActivityViewModel;

/**
 * Created by vtiahotenkov on 13.03.17.
 */

@Singleton
@Component(modules = {NetworkModule.class, ModelModule.class, ApplicationModule.class})
public interface NetworkComponent {

    SearchModel searchModel();

    Context context();

    void inject(SearchFragmentViewModel viewModel);

    void inject(UserProfileActivityViewModel viewModel);
}
