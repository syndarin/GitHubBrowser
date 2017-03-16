package name.syndarin.githubbrowser.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import name.syndarin.githubbrowser.R;
import name.syndarin.githubbrowser.databinding.ItemSearchUserResultBinding;
import name.syndarin.githubbrowser.entities.UserSearchResultItem;
import name.syndarin.githubbrowser.viewmodels.SearchResultItemViewModel;

/**
 * Created by vtiahotenkov on 10.03.17.
 */

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultItemHolder> {

    class SearchResultItemHolder extends RecyclerView.ViewHolder {

        ItemSearchUserResultBinding binding;
        SearchResultItemViewModel viewModel;

        SearchResultItemHolder(ItemSearchUserResultBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.viewModel = new SearchResultItemViewModel();
            binding.setViewModel(viewModel);
        }

        void bindData(UserSearchResultItem item) {
            viewModel.setItem(item);
            binding.invalidateAll();

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
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemSearchUserResultBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_search_user_result, parent, false);
        return new SearchResultItemHolder(binding);
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
