package eu.theappshop.baseadapter.adapter;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import eu.theappshop.baseadapter.vm.VM;

public class SpinnerAdapter extends ListAdapter {

  private boolean isHintEnabled;

  public SpinnerAdapter(
      @Nullable Adapter adapter, boolean isHintEnabled) {
    super(adapter);
    this.isHintEnabled = isHintEnabled;
  }

  @Override public View getDropDownView(int position, View convertView, ViewGroup parent) {
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

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    return super.getView(position, convertView, parent);
  }
}
