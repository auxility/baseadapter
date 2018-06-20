package eu.theappshop.baseadapter.viewholder;

import android.databinding.ViewDataBinding;

import eu.theappshop.baseadapter.vm.BaseVM;

public interface BaseViewHolder<VM extends BaseVM> {

    void bindViewModel(VM baseVM);

    ViewDataBinding getBinding();

}
