package com.skiff2011.baseadapter.sample

import android.view.View
import androidx.databinding.ObservableInt
import com.skiff2011.baseadapter.FilterableItemAdapter
import com.skiff2011.baseadapter.item.TitledItem

class FilterableRVItem : TitledItem {

  val adapter: FilterableItemAdapter<RvSampleItem> = FilterableItemAdapter(List(10) { index ->
    RvSampleItem(index)
  }) { item ->
    item.index % 2 == 0
  }

  val realCount: ObservableInt = ObservableInt(adapter.vms().size)

  fun removeLast(view: View) {
    adapter.remove(adapter.vms().size - 1)
    realCount.set(realCount.get() - 1)
  }

  fun addToTheEnd(view: View) {
    adapter.add(RvSampleItem(adapter.vms().size))
    realCount.set(realCount.get() + 1)
  }

  override fun getLayoutId(): Int = R.layout.item_filter_rv

  override fun getTitle(): String = "RecyclerView"
}