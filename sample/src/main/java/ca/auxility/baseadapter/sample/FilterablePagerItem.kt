package ca.auxility.baseadapter.sample

import android.view.View
import androidx.databinding.ObservableInt
import ca.auxility.baseadapter.FilterableItemAdapter
import ca.auxility.baseadapter.item.TitledItem
import ca.auxility.baseadapter.sample.R.layout

class FilterablePagerItem : TitledItem {

  val adapter: FilterableItemAdapter<PagerSampleItem> =
    FilterableItemAdapter(List(10) { index ->
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