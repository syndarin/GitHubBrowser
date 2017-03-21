package name.syndarin.githubbrowser.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import name.syndarin.githubbrowser.GitHubBrowserApplication;
import name.syndarin.githubbrowser.R;
import name.syndarin.githubbrowser.databinding.BindingUserProfileFragment;
import name.syndarin.githubbrowser.viewmodels.FragmentUserProfileViewModel;

/**
 * Created by syndarin on 3/21/17.
 */

public class UserProfileFragment extends Fragment {

    private FragmentUserProfileViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        BindingUserProfileFragment binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_profile, container, false);
        viewModel = new FragmentUserProfileViewModel("https://api.github.com/users/octocat"); //TODO fix asap
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        GitHubBrowserApplication application = (GitHubBrowserApplication) getActivity().getApplication();
        application.getNetworkComponent().inject(viewModel);
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        viewModel.onPause();
    }
}
