package eu.theappshop.baseadapter.adapter;

import android.database.Observable;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import eu.theappshop.baseadapter.observer.VMObserver;
import eu.theappshop.baseadapter.viewholder.BaseViewHolder;

public class BaseAdapter<VM extends eu.theappshop.baseadapter.vm.VM> extends Observable<VMObserver> implements ViewModelAdapter<VM> {

    private List<VM> vms;

    public BaseAdapter(List<VM> vms) {
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
    public void bindViewHolder(BaseViewHolder<VM> viewHolder, int position) {
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
        for (VM vm : vms) {
            if (clazz.isAssignableFrom(vm.getClass())) {
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
    public void add(VM item) {
        vms.add(item);
        for (VMObserver observer : mObservers) {
            observer.notifyItemInserted(vms.size());
        }
    }

    @Override
    public void add(int position, VM item) {
        vms.add(position, item);
        for (VMObserver observer : mObservers) {
            observer.notifyItemInserted(position);
        }
    }

    @Override
    public void addAll(List<? extends VM> list) {
        int previousLength = getItemCount();
        vms.addAll(list);
        for (VMObserver observer : mObservers) {
            observer.notifyItemRangeInserted(previousLength, list.size());
        }
    }

    @Override
    public void addAll(List<? extends VM> list, int position) {
        vms.addAll(position, list);
        for (VMObserver observer : mObservers) {
            observer.notifyItemRangeInserted(position, list.size());
        }
    }

    @Override
    public List<VM> getItems() {
        return vms;
    }

    @Override
    public VM getItem(int position) {
        return vms.get(position);
    }

    @Override
    public int indexOf(VM o) {
        return vms.indexOf(o);
    }

    @NonNull
    @Override
    public Iterator<VM> iterator() {
        return vms.iterator();
    }

    @Override
    public int findFirstIndexOf(Class<? extends VM> cls) {
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
    public void remove(VM vm) {
        int index = vms.indexOf(vm);
        if (index != -1) {
            vms.remove(index);
            for (VMObserver observer : mObservers) {
                observer.notifyItemRemoved(index);
            }
        }
    }

    @Override
    public VM remove(int index) {
        if (index != -1) {
            VM vm = vms.remove(index);
            for (VMObserver observer : mObservers) {
                observer.notifyItemRemoved(index);
            }
            return vm;
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

    public int getVMPosition(VM vm) {
        return vms.indexOf(vm);
    }
}
