package name.syndarin.githubbrowser;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by vtiahotenkov on 10.03.17.
 */

public class GitHubBrowserApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
