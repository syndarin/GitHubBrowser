package name.syndarin.githubbrowser.viewmodels;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import name.syndarin.githubbrowser.R;
import name.syndarin.githubbrowser.entities.UserSearchResultItem;

/**
 * Created by vtiahotenkov on 16.03.17.
 */

public class SearchResultItemViewModel {

    @BindingAdapter({"imageUrl"})
    public static void loadAvatar(ImageView target, String url) {
        Glide.with(target.getContext()).load(url).error(R.mipmap.ic_launcher).into(target);
    }

    private UserSearchResultItem item;

    public void setItem(UserSearchResultItem item) {
        this.item = item;
    }

    public String getUsername() {
        return item.getLogin();
    }

    public String getUserAvatar() {
        return item.getAvatarUrl();
    }

}
