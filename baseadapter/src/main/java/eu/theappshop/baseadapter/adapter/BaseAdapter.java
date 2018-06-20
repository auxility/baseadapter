package eu.theappshop.baseadapter.adapter;

import android.database.Observable;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import eu.theappshop.baseadapter.observer.VMObserver;
import eu.theappshop.baseadapter.viewholder.BaseViewHolder;
import eu.theappshop.baseadapter.vm.VM;

public class BaseAdapter<V extends VM> extends Observable<VMObserver> implements ViewModelAdapter<V> {

    private List<V> vms;

    public BaseAdapter(List<V> vms) {
        this.vms = vms;
    }

    public BaseAdapter() {
        vms = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return vms.size();
    }

    @Override
    public void bindViewHolder(BaseViewHolder<V> viewHolder, int position) {
        viewHolder.bindViewModel(vms.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return vms.get(position).getLayoutId();
    }

    @Override
    public boolean isEmpty() {
        return vms.isEmpty();
    }

    @Override
    public <T extends eu.theappshop.baseadapter.vm.VM> int getCountItemType(Class<T> clazz) {
        int count = 0;
        for (V v : vms) {
            if (clazz.isAssignableFrom(v.getClass())) {
                count++;
            }
        }
        return count;
    }

    @Override
    public void clear() {
        vms.clear();
    }

    @Override
    public void add(V item) {
        vms.add(item);
        for (VMObserver observer : mObservers) {
            observer.notifyItemInserted(vms.size());
        }
    }

    @Override
    public void add(int position, V item) {
        vms.add(position, item);
        for (VMObserver observer : mObservers) {
            observer.notifyItemInserted(position);
        }
    }

    @Override
    public void addAll(List<? extends V> list) {
        int previousLength = getItemCount();
        vms.addAll(list);
        for (VMObserver observer : mObservers) {
            observer.notifyItemRangeInserted(previousLength, list.size());
        }
    }

    @Override
    public void addAll(List<? extends V> list, int position) {
        vms.addAll(position, list);
        for (VMObserver observer : mObservers) {
            observer.notifyItemRangeInserted(position, list.size());
        }
    }

    @Override
    public List<V> getItems() {
        return vms;
    }

    @Override
    public V getItem(int position) {
        return vms.get(position);
    }

    @Override
    public int indexOf(V o) {
        return vms.indexOf(o);
    }

    @NonNull
    @Override
    public Iterator<V> iterator() {
        return vms.iterator();
    }

    @Override
    public int findFirstIndexOf(Class<? extends V> cls) {
        int index = 0;
        for (int i = 0; i < getItemCount(); i++) {
            if (getItem(i).getClass() == cls) {
                return index;
            }
            index++;
        }
        return -1;
    }

    @Override
    public void remove(V v) {
        int index = vms.indexOf(v);
        if (index != -1) {
            vms.remove(index);
            for (VMObserver observer : mObservers) {
                observer.notifyItemRemoved(index);
            }
        }
    }

    @Override
    public V remove(int index) {
        if (index != -1) {
            V v = vms.remove(index);
            for (VMObserver observer : mObservers) {
                observer.notifyItemRemoved(index);
            }
            return v;
        }
        return null;
    }

    @Override
    public void removeRange(int start, int end) {
        vms.subList(start, end).clear();
        for (VMObserver observer : mObservers) {
            observer.notifyItemRangeRemoved(start, end - start);
        }
    }

    public int getVMPosition(V v) {
        return vms.indexOf(v);
    }
}
