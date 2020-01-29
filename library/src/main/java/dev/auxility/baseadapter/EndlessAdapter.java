package dev.auxility.baseadapter;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import dev.auxility.baseadapter.item.Item;
import dev.auxility.baseadapter.misc.function.Predicate;

public class EndlessAdapter<V extends Item> extends AbstractAdapterDecorator<V> {

    private final int threshold;
    private final OnLoadMoreListener listener;
    private boolean inProgress = false;
    private boolean complete = false;

    public EndlessAdapter(
            @NonNull AbstractAdapter<V> decoratedAdapter,
            int threshold,
            OnLoadMoreListener listener
    ) {
        super(decoratedAdapter);
        this.threshold = threshold;
        this.listener = listener;
    }

    public EndlessAdapter(int threshold, OnLoadMoreListener listener) {
        this(new BaseAdapter<V>(), threshold, listener);
    }

    @Bindable
    @Override
    public int getSize() {
        int size = getAdapter().getSize();
        if (size == 0) {
            checkThreshold(0);
        }
        return size;
    }

    @NonNull
    @Override
    public ListIterator<V> listIterator(int index) {
        return getAdapter().listIterator(index);
    }

    @NonNull
    @Override
    public V get(int index) {
        checkThreshold(index);
        return getAdapter().get(index);
    }

    @NonNull
    @Override
    public List<V> items() {
        return getAdapter().items();
    }

    @NonNull
    @Override
    public V remove(int index) {
        return getAdapter().remove(index);
    }

    @Override
    public boolean removeIf(@NonNull Predicate<V> predicate, boolean withDiffUtil) {
        return getAdapter().removeIf(predicate, withDiffUtil);
    }

    @Override
    public List<V> removeRange(int beginIndex, int endIndex) {
        return getAdapter().removeRange(beginIndex, endIndex);
    }

    @Override
    public void clear(boolean withDiffUtil) {
        getAdapter().clear();
    }

    @Override
    public void add(int index, @NonNull V element) {
        getAdapter().add(index, element);
    }

    @Override
    public boolean addAll(int index, @NonNull Collection<? extends V> c) {
        return getAdapter().addAll(index, c);
    }

    @NonNull
    @Override
    public V set(int index, @NonNull V element) {
        return getAdapter().set(index, element);
    }

    @Override
    public void set(@NonNull Collection<? extends V> c, boolean withDiffUtil) {
        getAdapter().set(c, withDiffUtil);
    }

    @NonNull
    @Override
    public Iterator<V> iterator() {
        return getAdapter().iterator();
    }

    public int getThreshold() {
        return threshold;
    }

    public boolean isInProgress() {
        return inProgress;
    }

    public void setInProgress(boolean inProgress) {
        this.inProgress = inProgress;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    @Override
    public void refresh() {
        getAdapter().refresh();
    }

    private void checkThreshold(int index) {
        if (!isComplete() && !isInProgress() && index >= getAdapter().getSize() - threshold) {
            listener.onLoadMore(getAdapter().getSize());
        }
    }

    public interface OnLoadMoreListener extends Serializable {

        void onLoadMore(int currentSize);

    }
}
