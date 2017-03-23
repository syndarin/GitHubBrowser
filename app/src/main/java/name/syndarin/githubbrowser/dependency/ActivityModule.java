package name.syndarin.githubbrowser.dependency;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import dagger.Module;
import dagger.Provides;
import name.syndarin.githubbrowser.R;
import name.syndarin.githubbrowser.navigation.Navigator;
import name.syndarin.githubbrowser.navigation.NavigatorLandscape;
import name.syndarin.githubbrowser.navigation.NavigatorPortrait;

/**
 * Created by syndarin on 3/21/17.
 */

@Module
public class ActivityModule {

    private AppCompatActivity appCompatActivity;

    public ActivityModule(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;

        if (appCompatActivity.getResources().getBoolean(R.bool.portrait_only)) {
            appCompatActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Provides
    @ActivityScope
    Navigator getNavigator(){
        boolean isLandscape = appCompatActivity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        FragmentManager fragmentManager = appCompatActivity.getSupportFragmentManager();
        return isLandscape ? new NavigatorLandscape(appCompatActivity) : new NavigatorPortrait(fragmentManager);
    }

}
