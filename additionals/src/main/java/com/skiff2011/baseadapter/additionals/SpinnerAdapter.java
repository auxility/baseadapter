package com.skiff2011.baseadapter.additionals;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.skiff2011.baseadapter.AbstractVmAdapter;
import com.skiff2011.baseadapter.AdapterDataObserver;
import com.skiff2011.baseadapter.R;
import com.skiff2011.baseadapter.vm.VM;
import java.util.List;

public class SpinnerAdapter<V extends VM> extends BaseAdapter implements AdapterDataObserver<V> {

  private boolean isHintEnabled;
  @Nullable
  private AbstractVmAdapter<V> adapter;

  public SpinnerAdapter(
      @Nullable AbstractVmAdapter<V> adapter, boolean isHintEnabled) {
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
    VM vm = (VM) getItem(position);
    ListBindingHolder<VM> bindingHolder =
        ListBindingHolder.create(LayoutInflater.from(parent.getContext()),
            vm instanceof SpinnerVM ? ((SpinnerVM) vm).getSelectedLayoutID() : vm.getLayoutId(),
            parent);
    bindingHolder.bindViewModel(vm);
    return bindingHolder.getBinding().getRoot();
  }

  @Override public View getDropDownView(int position, View convertView, ViewGroup parent) {
    VM vm = (VM) getItem(position);
    ListBindingHolder<VM> bindingHolder;
    //if convertView is null just create new ListBindingHolder
    if (convertView == null) {
      bindingHolder =
          ListBindingHolder.create(LayoutInflater.from(parent.getContext()), vm.getLayoutId(),
              parent);
    } else {
      // if it is hint vm inflate it
      if (isHintEnabled && position == 0) {
        bindingHolder = ListBindingHolder.create(LayoutInflater.from(parent.getContext()),
            R.layout.layout_empty,
            parent);
      } else {
        //check if views are the same if it is not inflate new one
        bindingHolder = (ListBindingHolder<VM>) convertView.getTag();
        if (bindingHolder.getVm().getLayoutId() != vm.getLayoutId()) {
          bindingHolder =
              ListBindingHolder.create(LayoutInflater.from(parent.getContext()), vm.getLayoutId(),
                  parent);
        }
      }
    }
    bindingHolder.bindViewModel(vm);
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

  @Override public void notifyOnDataSetChanged(@NonNull List<V> oldItems, @NonNull List<V> newVms) {
    notifyOnDataSetChanged();
  }

  @Override public void notifyOnItemChanged(int position) {
    notifyOnDataSetChanged();
  }
}
