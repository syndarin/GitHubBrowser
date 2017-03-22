package name.syndarin.githubbrowser.dependency;

import android.support.v7.app.AppCompatActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import name.syndarin.githubbrowser.navigation.Navigator;
import name.syndarin.githubbrowser.navigation.NavigatorImpl;

/**
 * Created by syndarin on 3/21/17.
 */

@Module
public class ActivityModule {

    private AppCompatActivity appCompatActivity;

    public ActivityModule(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
    }

    @Provides
    @ActivityScope
    Navigator getNavigator(){
        return new NavigatorImpl(appCompatActivity.getSupportFragmentManager());
    }

}
