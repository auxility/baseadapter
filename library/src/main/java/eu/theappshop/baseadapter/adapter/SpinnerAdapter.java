package eu.theappshop.baseadapter.adapter;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import eu.theappshop.baseadapter.R;
import eu.theappshop.baseadapter.vm.SpinnerVM;
import eu.theappshop.baseadapter.vm.VM;
import java.util.List;

public class SpinnerAdapter extends BaseAdapter implements AdapterDataObserver {

  private boolean isHintEnabled;
  @Nullable
  private Adapter adapter;

  public SpinnerAdapter(
      @Nullable Adapter adapter, boolean isHintEnabled) {
    this.adapter = adapter;
    this.isHintEnabled = isHintEnabled;
  }

  @Override public int getCount() {
    return adapter == null ? 0 : adapter.getItemCount();
  }

  @Override public Object getItem(int position) {
    return adapter == null ? null : adapter.getItem(position);
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

  @Override public void refresh(List<VM> oldItems) {
    notifyDataSetChanged();
  }
}
