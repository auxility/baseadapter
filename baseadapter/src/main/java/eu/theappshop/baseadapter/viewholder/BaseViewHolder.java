package eu.theappshop.baseadapter.viewholder;

import android.databinding.ViewDataBinding;

public interface BaseViewHolder<VM extends eu.theappshop.baseadapter.vm.VM> {

    void bindViewModel(VM VM);

    ViewDataBinding getBinding();

}
