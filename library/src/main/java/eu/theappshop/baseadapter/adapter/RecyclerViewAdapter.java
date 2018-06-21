package eu.theappshop.baseadapter.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerBindingHolder> implements AdapterDataObserver {

    private BaseAdapter abstractAdapter;

    RecyclerViewAdapter(BaseAdapter abstractAdapter) {
        this.abstractAdapter = abstractAdapter;
    }

    @NonNull
    @Override
    public RecyclerBindingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return RecyclerBindingHolder.create(LayoutInflater.from(parent.getContext()), viewType, parent);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(@NonNull RecyclerBindingHolder holder, int position) {
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
