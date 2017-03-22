package name.syndarin.githubbrowser.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import name.syndarin.githubbrowser.GitHubBrowserApplication;
import name.syndarin.githubbrowser.R;
import name.syndarin.githubbrowser.dependency.ActivityComponent;
import name.syndarin.githubbrowser.dependency.DaggerActivityComponent;
import name.syndarin.githubbrowser.dependency.NetworkComponent;

public class MainActivity extends AppCompatActivity {

    private ActivityComponent activityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NetworkComponent networkComponent = ((GitHubBrowserApplication) getApplication()).getNetworkComponent();
        activityComponent = DaggerActivityComponent.builder().networkComponent(networkComponent).build();
    }

    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }
}
