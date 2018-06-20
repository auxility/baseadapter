package eu.theappshop.baseadapter.viewholder.viewpager;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import eu.theappshop.baseadapter.BR;
import eu.theappshop.baseadapter.viewholder.BaseViewHolder;
import eu.theappshop.baseadapter.vm.VM;

public class PagerBindingHolder<V extends VM> implements BaseViewHolder<V> {

    private static final String TAG = PagerBindingHolder.class.getSimpleName();

    private ViewDataBinding binding;
    private V vm;

    public PagerBindingHolder(ViewDataBinding binding) {
        this.binding = binding;
    }

    public static <V extends VM> PagerBindingHolder<V> create(LayoutInflater layoutInflater, @LayoutRes int resId, ViewGroup p) {
        try {
            return new PagerBindingHolder<>(DataBindingUtil.inflate(layoutInflater, resId, p, true));
        } catch (Throwable e) {
            String resName = layoutInflater.getContext().getResources().getResourceName(resId);
            Log.e(TAG, "Error inflating layout " + resName);
            throw e;
        }
    }

    @Override
    public void bindViewModel(V VM) {
        this.vm = VM;
        binding.setVariable(BR.viewModel, VM);
    }

    @Override
    public ViewDataBinding getBinding() {
        return binding;
    }

    public V getVM() {
        return vm;
    }
}
