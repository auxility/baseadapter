package ca.auxility.baseadapter.sample

import android.view.View
import ca.auxility.baseadapter.Adapter
import ca.auxility.baseadapter.BaseAdapter
import ca.auxility.baseadapter.item.TitledItem
import ca.auxility.baseadapter.sample.R.layout

class BasePagerItem : TitledItem {

  val adapter: Adapter<PagerSampleItem> =
    BaseAdapter(List(5) { index ->
      PagerSampleItem(index)
    })

  fun removeLast(view: View) {
    adapter.remove(adapter.size - 1)
  }

  fun addToTheEnd(view: View) {
    adapter.add(PagerSampleItem(adapter.size))
  }

  override fun getTitle(): String = "ViewPager"

  override fun getLayoutId(): Int = layout.item_base_pager
}