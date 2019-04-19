package eu.theappshop.baseadapter.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import eu.theappshop.baseadapter.adapterv2.AbstractVmAdapter;
import eu.theappshop.baseadapter.adapterv2.AdapterDataObserver;
import eu.theappshop.baseadapter.vm.VM;
import java.util.ArrayList;
import java.util.List;

public class ListAdapter<V extends VM> extends BaseAdapter implements AdapterDataObserver<V> {

  @NonNull
  protected AbstractVmAdapter<V> adapter;
  private List<Integer> viewTypes;

  public ListAdapter(@NonNull AbstractVmAdapter<V> adapter) {
    this.adapter = adapter;
    invalidateViewType();
  }

  @Override public int getViewTypeCount() {
    if (getCount() == 0) {
      return super.getViewTypeCount();
    }
    return viewTypes.size();
  }

  @Override public int getItemViewType(int position) {
    if (getCount() == 0) {
      return super.getItemViewType(position);
    }
    return viewTypes.indexOf(adapter.get(position).getLayoutId());
  }

  @Override public int getCount() {
    return adapter.getSize();
  }

  @Override public Object getItem(int position) {
    return adapter.get(position);
  }

  @Override public long getItemId(int position) {
    return position;
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    ListBindingHolder<VM> bindingHolder;
    VM vm = (VM) getItem(position);
    if (convertView == null) {
      bindingHolder =
          ListBindingHolder.create(LayoutInflater.from(parent.getContext()), vm.getLayoutId(),
              parent);
    } else {
      bindingHolder = (ListBindingHolder<VM>) convertView.getTag();
    }
    bindingHolder.bindViewModel(vm);
    return bindingHolder.getBinding().getRoot();
  }

  @Override public void notifyDataSetChanged() {
    invalidateViewType();
    super.notifyDataSetChanged();
  }

  @Override public void notifyItemInserted(int position) {
    notifyDataSetChanged();
  }

  @Override public void notifyItemRangeInserted(int positionStart, int itemCount) {
    notifyDataSetChanged();
  }

  @Override public void notifyItemRemoved(int position) {
    notifyDataSetChanged();
  }

  @Override public void notifyItemRangeRemoved(int positionStart, int itemCount) {
    notifyDataSetChanged();
  }

  @Override public void notifyDataSetChanged(List<V> oldItems, List<V> newVms) {
    notifyDataSetChanged();
  }

  @Override public void notifyItemChanged(int position) {
    notifyDataSetChanged();
  }

  private void invalidateViewType() {
    viewTypes = new ArrayList<>();
    if (getCount() == 0) {
      return;
    }
    for (Object object : adapter.vms()) {
      VM vm = (VM) object;
      if (!viewTypes.contains(vm.getLayoutId())) {
        viewTypes.add(vm.getLayoutId());
      }
    }
  }
}
