package eu.theappshop.baseadapter.adapter;

import android.support.annotation.NonNull;
import android.widget.Filter;
import android.widget.Filterable;
import eu.theappshop.baseadapter.adapterv2.AbstractVmAdapter;

public class FilterableListAdapter extends ListAdapter implements Filterable {

  private Filter filter = new EmptyFilter();

  public FilterableListAdapter(
      @NonNull AbstractVmAdapter adapter) {
    super(adapter);
  }

  @Override public Filter getFilter() {
    return filter;
  }

  private class EmptyFilter extends Filter {

    @Override protected FilterResults performFiltering(CharSequence constraint) {
      FilterResults filterResults = new FilterResults();
      filterResults.values = adapter.vms();
      filterResults.count = adapter.getSize();
      return filterResults;
    }

    @Override protected void publishResults(CharSequence constraint, FilterResults results) {

    }
  }
}
