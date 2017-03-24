package name.syndarin.githubbrowser.utils;

import android.text.Editable;
import android.text.TextWatcher;

import io.reactivex.subjects.PublishSubject;

/**
 * Created by vtiahotenkov on 24.03.17.
 */

public class RxInputWatcher implements TextWatcher {

    private PublishSubject<String> onTextChangedSubject = PublishSubject.create();

    private String cachedInput;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // TODO
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        cachedInput = s.toString();
        onTextChangedSubject.onNext(cachedInput);
    }

    @Override
    public void afterTextChanged(Editable s) {
        // TODO
    }

    public PublishSubject<String> getOnTextChangedSubject() {
        return onTextChangedSubject;
    }

    public String getCachedInput() {
        return cachedInput;
    }
}
