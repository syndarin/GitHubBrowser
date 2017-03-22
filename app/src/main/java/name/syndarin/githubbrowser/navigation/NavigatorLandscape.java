package name.syndarin.githubbrowser.navigation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import name.syndarin.githubbrowser.R;
import name.syndarin.githubbrowser.fragments.SearchFragment;
import name.syndarin.githubbrowser.fragments.UserProfileFragment;
import timber.log.Timber;

/**
 * Created by vtiahotenkov on 22.03.17.
 */

public class NavigatorLandscape implements Navigator {

    private FragmentManager fragmentManager;

    public NavigatorLandscape(FragmentManager fragmentManager) {
        Timber.d("New instance");
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void showStartScreen() {
        SearchFragment fragment = new SearchFragment();
        replaceFragment(fragment, R.id.fragment_container_primary);
    }

    @Override
    public void showUserProfile(String profileUrl) {
        UserProfileFragment fragment = UserProfileFragment.create(profileUrl);
        replaceFragment(fragment, R.id.fragment_container_primary);
    }

    private void replaceFragment(Fragment fragment, int containerId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(containerId, fragment);
        transaction.commit();
    }
}
