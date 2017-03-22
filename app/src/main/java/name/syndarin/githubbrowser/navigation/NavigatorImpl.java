package name.syndarin.githubbrowser.navigation;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import name.syndarin.githubbrowser.R;
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
    public void showUserProfile(String profileUrl) {
        UserProfileFragment fragment = UserProfileFragment.create(profileUrl);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container, fragment);
        transaction.commit();
    }
}
