package ca.auxility.baseadapter.additionals;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import ca.auxility.baseadapter.AbstractItemAdapter;
import ca.auxility.baseadapter.AdapterDataObserver;
import ca.auxility.baseadapter.R;
import ca.auxility.baseadapter.item.Item;
import java.util.List;

public class SpinnerAdapter<V extends Item> extends BaseAdapter implements AdapterDataObserver {

  private boolean isHintEnabled;
  @Nullable
  private AbstractItemAdapter<V> adapter;

  public SpinnerAdapter(
      @Nullable AbstractItemAdapter<V> adapter, boolean isHintEnabled) {
    this.adapter = adapter;
    this.isHintEnabled = isHintEnabled;
  }

  @Override public int getCount() {
    return adapter == null ? 0 : adapter.getSize();
  }

  @Override public Object getItem(int position) {
    return adapter == null ? null : adapter.get(position);
  }

  @Override public long getItemId(int position) {
    return position;
  }

  //in case of spinner convertView is always null no need to add extra conditions
  @Override public View getView(int position, View convertView, ViewGroup parent) {
    Item item = (Item) getItem(position);
    ListBindingHolder<Item> bindingHolder =
        ListBindingHolder.create(LayoutInflater.from(parent.getContext()),
            item instanceof SpinnerItem ? ((SpinnerItem) item).getSelectedLayoutID()
                : item.getLayoutId(),
            parent);
    bindingHolder.bindViewModel(item);
    return bindingHolder.getBinding().getRoot();
  }

  @Override public View getDropDownView(int position, View convertView, ViewGroup parent) {
    Item item = (Item) getItem(position);
    ListBindingHolder<Item> bindingHolder;
    //if convertView is null just create new ListBindingHolder
    if (convertView == null) {
      bindingHolder =
          ListBindingHolder.create(LayoutInflater.from(parent.getContext()), item.getLayoutId(),
              parent);
    } else {
      // if it is hint item inflate it
      if (isHintEnabled && position == 0) {
        bindingHolder = ListBindingHolder.create(LayoutInflater.from(parent.getContext()),
            R.layout.layout_empty,
            parent);
      } else {
        //check if views are the same if it is not inflate new one
        bindingHolder = (ListBindingHolder<Item>) convertView.getTag();
        if (bindingHolder.getVm().getLayoutId() != item.getLayoutId()) {
          bindingHolder =
              ListBindingHolder.create(LayoutInflater.from(parent.getContext()), item.getLayoutId(),
                  parent);
        }
      }
    }
    bindingHolder.bindViewModel(item);
    return bindingHolder.getBinding().getRoot();
  }

  @Override public void notifyOnDataSetChanged() {
    notifyOnDataSetChanged();
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
}
