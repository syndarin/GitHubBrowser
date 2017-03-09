package name.syndarin.githubbrowser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String tag = MainActivity.class.getSimpleName();

    @BindView(R.id.edit_username_input)
    EditText editSearchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        RxTextView.textChanges(editSearchInput)
            .filter(input -> input.length() >= 3)
            .map(input -> input.toString().toUpperCase())
            .doOnNext(input -> Log.i(tag, "Input value " + input))
            .subscribe();
    }
}
