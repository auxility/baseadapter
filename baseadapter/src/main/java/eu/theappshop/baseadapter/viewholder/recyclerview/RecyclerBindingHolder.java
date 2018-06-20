package eu.theappshop.baseadapter.viewholder.recyclerview;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import eu.theappshop.baseadapter.BR;
import eu.theappshop.baseadapter.viewholder.BaseViewHolder;
import eu.theappshop.baseadapter.vm.VM;

public class RecyclerBindingHolder<V extends VM> extends RecyclerView.ViewHolder implements BaseViewHolder<V> {

    private static final String TAG = RecyclerBindingHolder.class.getSimpleName();
    private ViewDataBinding binding;

    public RecyclerBindingHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public static <V extends VM> RecyclerBindingHolder<V> create(LayoutInflater layoutInflater, @LayoutRes int resId, ViewGroup p) {
        try {
            return new RecyclerBindingHolder<>(DataBindingUtil.inflate(layoutInflater, resId, p, false));
        } catch (Throwable e) {
            String resName = layoutInflater.getContext().getResources().getResourceName(resId);
            Log.e(TAG, "Error inflating layout " + resName);
            throw e;
        }
    }

    @Override
    public void bindViewModel(V VM) {
        binding.setVariable(BR.viewModel, VM);
    }

    @Override
    public ViewDataBinding getBinding() {
        return binding;
    }
}
