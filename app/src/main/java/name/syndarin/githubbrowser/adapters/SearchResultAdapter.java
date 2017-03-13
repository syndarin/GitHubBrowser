package name.syndarin.githubbrowser.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import name.syndarin.githubbrowser.R;
import name.syndarin.githubbrowser.entities.UserSearchResultItem;

/**
 * Created by vtiahotenkov on 10.03.17.
 */

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultItemHolder> {

    class SearchResultItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_username)
        TextView textUsername;

        SearchResultItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindData(UserSearchResultItem item) {
            textUsername.setText(item.getLogin());
            itemView.setOnClickListener(view -> clickEventsSubject.onNext(item));
        }
    }

    private PublishSubject<UserSearchResultItem> clickEventsSubject;

    private List<UserSearchResultItem> items = Collections.emptyList();

    public SearchResultAdapter() {
        this.clickEventsSubject = PublishSubject.create();
    }

    @Override
    public SearchResultAdapter.SearchResultItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_user_result, parent, false);
        return new SearchResultItemHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchResultAdapter.SearchResultItemHolder holder, int position) {
        holder.bindData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public Subject<UserSearchResultItem> getClickEventsSubject() {
        return clickEventsSubject;
    }

    public void updateDataSet(@NonNull List<UserSearchResultItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }
}
