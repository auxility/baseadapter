package dev.auxility.baseadapter.additionals;

import android.widget.Filter;
import android.widget.Filterable;
import androidx.annotation.NonNull;
import dev.auxility.baseadapter.AbstractAdapter;

public class FilterableListAdapter extends ListAdapter implements Filterable {

  private Filter filter = new EmptyFilter();

  public FilterableListAdapter(
      @NonNull AbstractAdapter adapter) {
    super(adapter);
  }

  @Override public Filter getFilter() {
    return filter;
  }

  private class EmptyFilter extends Filter {

    @Override protected FilterResults performFiltering(CharSequence constraint) {
      FilterResults filterResults = new FilterResults();
      filterResults.values = adapter.items();
      filterResults.count = adapter.getSize();
      return filterResults;
    }

    @Override protected void publishResults(CharSequence constraint, FilterResults results) {

    }
  }
}
