package name.syndarin.githubbrowser.utils;

import android.view.View;

import io.reactivex.subjects.PublishSubject;

/**
 * Created by vtiahotenkov on 24.03.17.
 */

public class RxClickListener implements View.OnClickListener {

    private PublishSubject<View> subject = PublishSubject.create();

    @Override
    public void onClick(View v) {
        subject.onNext(v);
    }

    public PublishSubject<View> getSubject() {
        return subject;
    }
}
