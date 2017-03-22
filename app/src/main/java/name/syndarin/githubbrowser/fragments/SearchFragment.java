package name.syndarin.githubbrowser.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import name.syndarin.githubbrowser.R;
import name.syndarin.githubbrowser.activities.MainActivity;
import name.syndarin.githubbrowser.databinding.BindingSearchFragment;
import name.syndarin.githubbrowser.viewmodels.FragmentSearchViewModel;

/**
 * Created by syndarin on 3/21/17.
 */

public class SearchFragment extends Fragment {

    private FragmentSearchViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        BindingSearchFragment binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        viewModel = new FragmentSearchViewModel(binding);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainActivity activity = (MainActivity) getActivity();
        activity.getActivityComponent().inject(viewModel);
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
