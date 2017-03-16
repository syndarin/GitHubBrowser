package name.syndarin.githubbrowser.viewmodels;

import javax.inject.Inject;

import name.syndarin.githubbrowser.databinding.ActivityMainBinding;
import name.syndarin.githubbrowser.models.SearchModel;

/**
 * Created by vtiahotenkov on 15.03.17.
 */

public class MainActivityViewModel {

    @Inject
    SearchModel searchModel;

    public MainActivityViewModel(ActivityMainBinding binding) {

    }
}
