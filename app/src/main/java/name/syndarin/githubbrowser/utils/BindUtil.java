package name.syndarin.githubbrowser.utils;

import android.databinding.BindingAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import timber.log.Timber;

/**
 * Created by vtiahotenkov on 24.03.17.
 */

public class BindUtil {

    @BindingAdapter({"adapter"})
    public static void setAdapter(RecyclerView view, RecyclerView.Adapter adapter) {
        Timber.d("Request for adapter!");
        view.setAdapter(adapter);
        view.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    @BindingAdapter("textWatcher")
    public static void setTextWatcher(EditText view, TextWatcher watcher) {
        view.addTextChangedListener(watcher);
    }

    @BindingAdapter("clickListener")
    public static void setClickListener(View view, View.OnClickListener listener) {
        view.setOnClickListener(listener);
    }
}
