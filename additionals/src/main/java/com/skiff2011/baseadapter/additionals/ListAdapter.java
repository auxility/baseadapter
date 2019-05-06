package com.skiff2011.baseadapter.additionals;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.skiff2011.baseadapter.AbstractVmAdapter;
import com.skiff2011.baseadapter.AdapterDataObserver;
import com.skiff2011.baseadapter.vm.VM;
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

  @Override public void notifyOnDataSetChanged() {
    invalidateViewType();
    super.notifyDataSetChanged();
  }

  @Override public void notifyOnItemInserted(int position) {
    notifyOnDataSetChanged();
  }

  @Override public void notifyOnItemRangeInserted(int positionStart, int itemCount) {
    notifyOnDataSetChanged();
  }

  @Override public void notifyOnItemRemoved(int position) {
    notifyOnDataSetChanged();
  }

  @Override public void notifyOnItemRangeRemoved(int positionStart, int itemCount) {
    notifyOnDataSetChanged();
  }

  @Override public void notifyOnDataSetChanged(@NonNull List<V> oldItems, @NonNull List<V> newVms) {
    notifyOnDataSetChanged();
  }

  @Override public void notifyOnItemChanged(int position) {
    notifyOnDataSetChanged();
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
