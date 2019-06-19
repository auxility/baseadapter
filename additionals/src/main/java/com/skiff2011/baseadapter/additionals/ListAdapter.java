package com.skiff2011.baseadapter.additionals;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import androidx.annotation.NonNull;
import com.skiff2011.baseadapter.AbstractItemAdapter;
import com.skiff2011.baseadapter.AdapterDataObserver;
import com.skiff2011.baseadapter.item.Item;
import java.util.ArrayList;
import java.util.List;

public class ListAdapter<V extends Item> extends BaseAdapter implements AdapterDataObserver {

  @NonNull
  protected AbstractItemAdapter<V> adapter;
  private List<Integer> viewTypes;

  public ListAdapter(@NonNull AbstractItemAdapter<V> adapter) {
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
    ListBindingHolder<Item> bindingHolder;
    Item item = (Item) getItem(position);
    if (convertView == null) {
      bindingHolder =
          ListBindingHolder.create(LayoutInflater.from(parent.getContext()), item.getLayoutId(),
              parent);
    } else {
      bindingHolder = (ListBindingHolder<Item>) convertView.getTag();
    }
    bindingHolder.bindViewModel(item);
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

  @Override public void notifyOnDataSetChanged(@NonNull List<? extends Item> oldItems,
      @NonNull List<? extends Item> newVms) {
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
    for (Object object : adapter.items()) {
      Item item = (Item) object;
      if (!viewTypes.contains(item.getLayoutId())) {
        viewTypes.add(item.getLayoutId());
      }
    }
  }
}
