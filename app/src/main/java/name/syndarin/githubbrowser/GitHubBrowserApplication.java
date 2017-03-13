package name.syndarin.githubbrowser;

import android.app.Application;

import com.facebook.stetho.Stetho;

import java.io.File;

import name.syndarin.githubbrowser.logging.FileTree;
import timber.log.Timber;

/**
 * Created by vtiahotenkov on 10.03.17.
 */

public class GitHubBrowserApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);

        File logFile = new File(getFilesDir(), "log.txt");
        Timber.plant(new FileTree(logFile), new Timber.DebugTree());
    }
}
