package eu.theappshop.baseadapter.adapter;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import eu.theappshop.baseadapter.observer.VMObserver;
import eu.theappshop.baseadapter.viewholder.BaseViewHolder;
import eu.theappshop.baseadapter.vm.BaseVM;

public interface ViewModelAdapter<VM extends BaseVM> extends Serializable, Iterable<VM> {

    int getItemCount();

    void bindViewHolder(BaseViewHolder<VM> viewHolder, int position);

    int getItemViewType(int position);

    boolean isEmpty();

    <T extends BaseVM> int getCountItemType(Class<T> clazz);

    void clear();

    void add(VM item);

    void add(int position, VM item);

    void addAll(List<? extends VM> list);

    void addAll(List<? extends VM> list, int position);

    List<VM> getItems();

    VM getItem(int index);

    int indexOf(VM o);

    Iterator<VM> iterator();

    int findFirstIndexOf(Class<? extends VM> cls);

    void remove(VM vm);

    VM remove(int index);

    void removeRange(int start, int end);

    void registerObserver(VMObserver observer);

    void unregisterObserver(VMObserver observer);
}
