package name.syndarin.githubbrowser.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import name.syndarin.githubbrowser.R;
import name.syndarin.githubbrowser.entities.UserSearchResult;
import name.syndarin.githubbrowser.entities.UserSearchResultItem;

/**
 * Created by vtiahotenkov on 10.03.17.
 */

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultItemHolder> {

    class SearchResultItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_username)
        TextView textUsername;

        public SearchResultItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(UserSearchResultItem item) {
            textUsername.setText(item.getLogin());
        }
    }

    private List<UserSearchResultItem> items;

    public SearchResultAdapter(@NonNull List<UserSearchResultItem> items) {
        this.items = items;
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
}
