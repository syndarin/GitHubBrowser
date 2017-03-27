package name.syndarin.githubbrowser.dependency;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import name.syndarin.githubbrowser.models.SearchModel;
import name.syndarin.githubbrowser.viewmodels.FragmentSearchViewModel;
import name.syndarin.githubbrowser.viewmodels.FragmentUserProfileViewModel;

/**
 * Created by vtiahotenkov on 13.03.17.
 */

@Singleton
@Component(modules = {NetworkModule.class, ModelModule.class, ApplicationModule.class})
public interface NetworkComponent {

    SearchModel searchModel();

    Application application();

}
