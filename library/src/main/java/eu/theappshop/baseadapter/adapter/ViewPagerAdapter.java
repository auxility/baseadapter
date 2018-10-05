package eu.theappshop.baseadapter.adapter;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import eu.theappshop.baseadapter.vm.TitledVM;
import eu.theappshop.baseadapter.vm.VM;
import java.util.ArrayList;
import java.util.List;

class ViewPagerAdapter<V extends VM> extends PagerAdapter implements AdapterDataObserver<V> {

  private static final String STATES_COUNT_TAG = "STATES_COUNT";
  private static final String STATE_TAG = "VIEW_STATE_";
  private SparseArray<PagerBindingHolder> activeHolders = new SparseArray<>();

  @NonNull
  private Adapter<VM> adapter;
  private List<SparseArray<Parcelable>> states;

  ViewPagerAdapter(@NonNull Adapter<VM> adapter) {
    this.adapter = adapter;
    states = new ArrayList<>(adapter.getItemCount());
    for (int i = 0; i < adapter.getItemCount(); i++) {
        states.add(null);
    }
  }

  @Override
  public int getCount() {
    return adapter.getItemCount();
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
    VM VM = adapter.getItem(position);
    PagerBindingHolder vmPagerBindingHolder =
        PagerBindingHolder.create(LayoutInflater.from(container.getContext()), VM.getLayoutId(),
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
    while (states.size() < (adapter.getItemCount())) {
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
    VM vm = adapter.getItem(position);
    if (vm instanceof TitledVM) {
      TitledVM tvm = (TitledVM) vm;
      return tvm.getTitle();
    } else {
      return "Untitled";
    }
  }

  @Override
  public void notifyDataSetChanged() {
      states = new ArrayList<>();
      while (states.size() < adapter.getItemCount()) {
          states.add(null);
      }
    super.notifyDataSetChanged();
  }

  @Override
  public void notifyItemInserted(int position) {
    states.add(position, null);
    super.notifyDataSetChanged();
  }

  @Override
  public void notifyItemRangeInserted(int positionStart, int itemCount) {
    int i = positionStart;
    while (i < positionStart + itemCount) {
      states.add(positionStart, null);
      i++;
    }
    super.notifyDataSetChanged();
  }

  @Override
  public void notifyItemRemoved(int position) {
    states.remove(position);
    super.notifyDataSetChanged();
  }

  @Override
  public void notifyItemRangeRemoved(int positionStart, int itemCount) {
    for (int i = 0; i < itemCount; i++) {
      states.remove(positionStart);
    }
    super.notifyDataSetChanged();
  }

  @Override
  public void notifyDataSetChanged(List<V> oldItems, List<V> newVms) {
    notifyDataSetChanged();
  }
}
