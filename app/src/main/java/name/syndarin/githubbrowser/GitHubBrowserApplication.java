package name.syndarin.githubbrowser;

import android.app.Application;

import com.facebook.stetho.Stetho;

import java.io.File;

import name.syndarin.githubbrowser.dependency.ApplicationModule;
import name.syndarin.githubbrowser.dependency.DaggerNetworkComponent;
import name.syndarin.githubbrowser.dependency.NetworkComponent;
import name.syndarin.githubbrowser.logging.FileTree;
import timber.log.Timber;

/**
 * Created by vtiahotenkov on 10.03.17.
 */

public class GitHubBrowserApplication extends Application {

    private NetworkComponent networkComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);

        networkComponent = DaggerNetworkComponent.builder().applicationModule(new ApplicationModule(this)).build();

        File logFile = new File(getFilesDir(), "log.txt");
        Timber.plant(new FileTree(logFile), new Timber.DebugTree());
    }

    public NetworkComponent getNetworkComponent() {
        return networkComponent;
    }
}
