package name.syndarin.githubbrowser.navigation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import name.syndarin.githubbrowser.R;
import name.syndarin.githubbrowser.fragments.SearchFragment;
import name.syndarin.githubbrowser.fragments.UserProfileFragment;

/**
 * Created by syndarin on 3/21/17.
 */

public class NavigatorImpl implements Navigator {

    private FragmentManager fragmentManager;

    public NavigatorImpl(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void showStartScreen() {
        SearchFragment fragment = new SearchFragment();
        replaceFragment(fragment, R.id.fragment_container);
    }

    @Override
    public void showUserProfile(String profileUrl) {
        UserProfileFragment fragment = UserProfileFragment.create(profileUrl);
        replaceFragment(fragment, R.id.fragment_container);
    }

    private void replaceFragment(Fragment fragment, int containerId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(containerId, fragment);
        transaction.commit();
    }
}
