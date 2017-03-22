package name.syndarin.githubbrowser.dependency;

import dagger.Component;
import name.syndarin.githubbrowser.navigation.Navigator;
import name.syndarin.githubbrowser.viewmodels.FragmentSearchViewModel;
import name.syndarin.githubbrowser.viewmodels.FragmentUserProfileViewModel;

/**
 * Created by syndarin on 3/21/17.
 */

@Component(dependencies = NetworkComponent.class, modules = {ActivityModule.class})
@ActivityScope
public interface ActivityComponent {

    Navigator navigator();

    void inject(FragmentSearchViewModel viewModel);

    void inject(FragmentUserProfileViewModel viewModel);

}
