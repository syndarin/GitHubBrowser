package name.syndarin.githubbrowser.navigation;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;

import name.syndarin.githubbrowser.R;
import name.syndarin.githubbrowser.fragments.SearchFragment;
import name.syndarin.githubbrowser.fragments.UserProfileFragment;
import timber.log.Timber;

/**
 * Created by vtiahotenkov on 22.03.17.
 */

public class NavigatorLandscape implements Navigator {

    private FragmentManager fragmentManager;

    private Context context;

    private ViewGroup root;
    private View primaryContainer;
    private View secondaryContainer;

    public NavigatorLandscape(AppCompatActivity activity) {
        Timber.d("New instance");
        this.fragmentManager = activity.getSupportFragmentManager();
        this.context = activity;

        root = (ViewGroup) activity.getWindow().getDecorView().getRootView().findViewById(R.id.root);

        primaryContainer = root.findViewById(R.id.fragment_container_primary);
        secondaryContainer = root.findViewById(R.id.fragment_container_secondary);
    }

    @Override
    public void showStartScreen() {
        SearchFragment fragment = new SearchFragment();
        replaceFragment(fragment, R.id.fragment_container_primary);
    }

    @Override
    public void showUserProfile(String profileUrl) {
        TransitionManager.beginDelayedTransition(root);
        secondaryContainer.setVisibility(View.VISIBLE);

        UserProfileFragment fragment = UserProfileFragment.create(profileUrl);
        replaceFragment(fragment, R.id.fragment_container_secondary);
    }

    @Override
    public boolean dispatchBack() {
        return false;
    }

    private void replaceFragment(Fragment fragment, int containerId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(containerId, fragment);
        transaction.commit();
    }
}
