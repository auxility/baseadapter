package eu.theappshop.baseadapter.viewholder;

import android.databinding.ViewDataBinding;
import eu.theappshop.baseadapter.vm.VM;

public interface BaseViewHolder<V extends VM> {

    void bindViewModel(V VM);

    ViewDataBinding getBinding();

}
