package com.skiff2011.baseadapter.view.viewpager;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.skiff2011.baseadapter.AdapterDataObserver;
import com.skiff2011.baseadapter.ItemAdapter;
import com.skiff2011.baseadapter.item.Item;
import com.skiff2011.baseadapter.item.TitledItem;
import java.util.ArrayList;
import java.util.List;

class ViewPagerAdapter extends PagerAdapter implements AdapterDataObserver {

  private static final String STATES_COUNT_TAG = "STATES_COUNT";
  private static final String STATE_TAG = "VIEW_STATE_";
  private SparseArray<PagerBindingHolder> activeHolders = new SparseArray<>();

  @NonNull
  private ItemAdapter adapter;
  private List<SparseArray<Parcelable>> states;

  ViewPagerAdapter(@NonNull ItemAdapter adapter) {
    this.adapter = adapter;
    states = new ArrayList<>(adapter.getSize());
    for (int i = 0; i < adapter.getSize(); i++) {
      states.add(null);
    }
  }

  @Override
  public int getCount() {
    return adapter.getSize();
  }

  @Override
  public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
    if (object instanceof PagerBindingHolder) {
      PagerBindingHolder pagerBindingHolder = (PagerBindingHolder) object;
      return pagerBindingHolder.getBinding().getRoot() == view;
    } else {
      throw new IllegalStateException("PagerBindingHolder or its inheritors must be used");
    }
  }

  @SuppressWarnings("unchecked")
  @NonNull
  @Override
  public Object instantiateItem(@NonNull ViewGroup container, int position) {
    Item Item = adapter.get(position);
    PagerBindingHolder vmPagerBindingHolder =
        PagerBindingHolder.create(LayoutInflater.from(container.getContext()), Item.getLayoutId(),
            container);
    adapter.bindViewHolder(vmPagerBindingHolder, position);
    if ((states != null) && (states.size() > position)) {
      SparseArray<Parcelable> savedState = states.get(position);
      if (savedState != null) {
        vmPagerBindingHolder.getBinding().getRoot().restoreHierarchyState(savedState);
      }
    }
    activeHolders.put(position, vmPagerBindingHolder);
    return vmPagerBindingHolder;
  }

  @Override
  public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
    if (object instanceof PagerBindingHolder) {
      PagerBindingHolder pagerBindingHolder = (PagerBindingHolder) object;
      if (states == null) {
        states = new ArrayList<>();
      }
      while (states.size() < position) {
        states.add(null);
      }
      SparseArray<Parcelable> state = new SparseArray<>();
      pagerBindingHolder.getBinding().getRoot().saveHierarchyState(state);
      states.set(position, state);
      container.removeView(pagerBindingHolder.getBinding().getRoot());
      activeHolders.remove(position);
    } else {
      throw new IllegalStateException("PagerBindingHolder or its inheritors must be used");
    }
  }

  @Nullable
  @Override
  public Parcelable saveState() {
    Bundle bundle = new Bundle();
    if (states == null) {
      states = new ArrayList<>();
    }
    while (states.size() < (adapter.getSize())) {
      states.add(null);
    }
    bundle.putInt(STATES_COUNT_TAG, states.size());
    for (int i = 0; i < activeHolders.size(); i++) {
      int key = activeHolders.keyAt(i);
      PagerBindingHolder holder = activeHolders.valueAt(i);
      SparseArray<Parcelable> state = new SparseArray<>();
      holder.getBinding().getRoot().saveHierarchyState(state);
      states.set(key, state);
    }
    for (int i = 0; i < states.size(); i++) {
      bundle.putSparseParcelableArray(STATE_TAG + i, states.get(i));
    }
    return bundle;
  }

  @Override
  public void restoreState(@Nullable Parcelable state, @Nullable ClassLoader loader) {
    states = new ArrayList<>();
    if (state != null) {
      Bundle bundle = (Bundle) state;
      bundle.setClassLoader(loader);
      for (int i = 0; i < bundle.getInt(STATES_COUNT_TAG, 0); i++) {
        states.add(bundle.getSparseParcelableArray(STATE_TAG + i));
      }
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public int getItemPosition(@NonNull Object object) {
    if (object instanceof PagerBindingHolder) {
      PagerBindingHolder pagerBindingHolder = (PagerBindingHolder) object;
      int position = adapter.indexOf(pagerBindingHolder.getVM());
      return position == -1 ? POSITION_NONE : position;
    } else {
      throw new IllegalStateException("PagerBindingHolder or its inheritors must be used");
    }
  }

  @Nullable
  @Override
  public CharSequence getPageTitle(int position) {
    Item item = adapter.get(position);
    if (item instanceof TitledItem) {
      TitledItem tvm = (TitledItem) item;
      return tvm.getTitle();
    } else {
      return "Untitled";
    }
  }

  @Override
  public void notifyOnDataSetChanged() {
    states = new ArrayList<>();
    while (states.size() < adapter.getSize()) {
      states.add(null);
    }
    super.notifyDataSetChanged();
  }

  @Override
  public void notifyOnItemInserted(int position) {
    states.add(position, null);
    super.notifyDataSetChanged();
  }

  @Override
  public void notifyOnItemRangeInserted(int positionStart, int itemCount) {
    int i = positionStart;
    while (i < positionStart + itemCount) {
      states.add(positionStart, null);
      i++;
    }
    super.notifyDataSetChanged();
  }

  @Override
  public void notifyOnItemRemoved(int position) {
    states.remove(position);
    super.notifyDataSetChanged();
  }

  @Override
  public void notifyOnItemRangeRemoved(int positionStart, int itemCount) {
    for (int i = 0; i < itemCount; i++) {
      states.remove(positionStart);
    }
    super.notifyDataSetChanged();
  }

  @Override
  public void notifyOnItemChanged(int position) {
    states.set(position, null);
    super.notifyDataSetChanged();
  }

  @Override
  public void notifyOnDataSetChanged(@NonNull List<? extends Item> oldItems,
      @NonNull List<? extends Item> newVms) {
    notifyOnDataSetChanged();
  }
}
