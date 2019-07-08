package dev.auxility.baseadapter.sample

import android.view.View
import androidx.databinding.ObservableInt
import dev.auxility.baseadapter.FilterableAdapter
import dev.auxility.baseadapter.item.TitledItem
import dev.auxility.baseadapter.sample.R.layout

class FilterablePagerItem : TitledItem {

  val adapter: FilterableAdapter<PagerSampleItem> =
    FilterableAdapter(List(10) { index ->
      PagerSampleItem(index)
    }) { item ->
      item.index % 2 == 0
    }

  val realCount: ObservableInt = ObservableInt(adapter.items().size)

  fun removeLast(view: View) {
    adapter.remove(adapter.items().size - 1)
    realCount.set(realCount.get() - 1)
  }

  fun addToTheEnd(view: View) {
    adapter.add(PagerSampleItem(adapter.items().size))
    realCount.set(realCount.get() + 1)
  }

  override fun getLayoutId(): Int = layout.item_filter_pager

  override fun getTitle(): String = "ViewPager"
}