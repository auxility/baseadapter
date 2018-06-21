package eu.theappshop.baseadapter.adapter;

import eu.theappshop.baseadapter.observer.VMObserver;
import eu.theappshop.baseadapter.viewholder.BaseViewHolder;
import eu.theappshop.baseadapter.vm.VM;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

public interface ViewModelAdapter<V extends VM> extends Serializable, Iterable<V> {

    int getItemCount();

    void bindViewHolder(BaseViewHolder<V> viewHolder, int position);

    int getItemViewType(int position);

    boolean isEmpty();

    <T extends eu.theappshop.baseadapter.vm.VM> int getCountItemType(Class<T> clazz);

    void clear();

    void add(V item);

    void add(int position, V item);

    void addAll(List<? extends V> list);

    void addAll(List<? extends V> list, int position);

    List<V> getItems();

    V getItem(int index);

    int indexOf(V o);

    Iterator<V> iterator();

    int findFirstIndexOf(Class<? extends V> cls);

    void remove(V v);

    V remove(int index);

    void removeRange(int start, int end);

    void registerObserver(VMObserver observer);

    void unregisterObserver(VMObserver observer);
}