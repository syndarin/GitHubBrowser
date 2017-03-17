package name.syndarin.githubbrowser.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import name.syndarin.githubbrowser.GitHubBrowserApplication;
import name.syndarin.githubbrowser.R;
import name.syndarin.githubbrowser.databinding.ActivityMainBinding;
import name.syndarin.githubbrowser.viewmodels.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = new MainActivityViewModel(binding);
        binding.setViewModel(viewModel);
        ((GitHubBrowserApplication) getApplication()).getNetworkComponent().inject(viewModel);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewModel.onPause();
    }
}
