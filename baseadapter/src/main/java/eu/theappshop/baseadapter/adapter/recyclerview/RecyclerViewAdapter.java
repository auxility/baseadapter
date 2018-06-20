package eu.theappshop.baseadapter.adapter.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import eu.theappshop.baseadapter.adapter.BaseAdapter;
import eu.theappshop.baseadapter.observer.VMObserver;
import eu.theappshop.baseadapter.viewholder.recyclerview.RecyclerBindingHolder;

public class RecyclerViewAdapter<VM extends eu.theappshop.baseadapter.vm.VM> extends RecyclerView.Adapter<RecyclerBindingHolder<VM>> implements VMObserver {

    private BaseAdapter<VM> abstractAdapter;

    public RecyclerViewAdapter(BaseAdapter<VM> abstractAdapter) {
        this.abstractAdapter = abstractAdapter;
    }

    @NonNull
    @Override
    public RecyclerBindingHolder<VM> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return RecyclerBindingHolder.create(LayoutInflater.from(parent.getContext()), viewType, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerBindingHolder<VM> holder, int position) {
        abstractAdapter.bindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return abstractAdapter.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        return abstractAdapter.getItemViewType(position);
    }

    @Override
    public void registerAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
        super.registerAdapterDataObserver(observer);
        abstractAdapter.registerObserver(this);
    }

    @Override
    public void unregisterAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
        super.unregisterAdapterDataObserver(observer);
        abstractAdapter.unregisterObserver(this);
    }
}
