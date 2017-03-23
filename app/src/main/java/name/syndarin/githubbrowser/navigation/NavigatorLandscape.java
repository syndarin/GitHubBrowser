package name.syndarin.githubbrowser.navigation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.percent.PercentLayoutHelper;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import name.syndarin.githubbrowser.R;
import name.syndarin.githubbrowser.animations.PercentValueAnimator;
import name.syndarin.githubbrowser.fragments.SearchFragment;
import name.syndarin.githubbrowser.fragments.UserProfileFragment;
import timber.log.Timber;

/**
 * Created by vtiahotenkov on 22.03.17.
 */

public class NavigatorLandscape implements Navigator {

    private FragmentManager fragmentManager;

    private Context context;

    private View primaryContainer;
    private View secondaryContainer;

    PercentValueAnimator primaryContainerAnimator;
    PercentValueAnimator secondaryContainerAnimator;

    public NavigatorLandscape(AppCompatActivity activity) {
        Timber.d("New instance");
        this.fragmentManager = activity.getSupportFragmentManager();
        this.context = activity;

        View rootView = activity.getWindow().getDecorView().getRootView();
        primaryContainer = rootView.findViewById(R.id.fragment_container_primary);
        primaryContainerAnimator = new PercentValueAnimator(((PercentRelativeLayout.LayoutParams) primaryContainer.getLayoutParams()).getPercentLayoutInfo());

        secondaryContainer = rootView.findViewById(R.id.fragment_container_secondary);
        secondaryContainerAnimator = new PercentValueAnimator(((PercentRelativeLayout.LayoutParams) secondaryContainer.getLayoutParams()).getPercentLayoutInfo());
    }

    @Override
    public void showStartScreen() {
        SearchFragment fragment = new SearchFragment();
        replaceFragment(fragment, R.id.fragment_container_primary);
    }

    @Override
    public void showUserProfile(String profileUrl) {

        Animator shrinkAnimator = AnimatorInflater.loadAnimator(context, R.animator.anim_search_fragment_shrink);
        shrinkAnimator.setTarget(primaryContainerAnimator);

        Animator expandAnimator = AnimatorInflater.loadAnimator(context, R.animator.anim_profile_fragment_expand);
        expandAnimator.setTarget(secondaryContainerAnimator);
        expandAnimator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                Timber.d("Animation end!");
                UserProfileFragment fragment = UserProfileFragment.create(profileUrl);
                replaceFragment(fragment, R.id.fragment_container_secondary);
            }
        });

        shrinkAnimator.start();
        expandAnimator.start();
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
