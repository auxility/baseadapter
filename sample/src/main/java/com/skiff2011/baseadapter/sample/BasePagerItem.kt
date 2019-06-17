package com.skiff2011.baseadapter.sample

import android.view.View
import com.skiff2011.baseadapter.BaseItemAdapter
import com.skiff2011.baseadapter.ItemAdapter
import com.skiff2011.baseadapter.item.TitledItem

class BasePagerItem : TitledItem {

  val adapter: ItemAdapter<PagerSampleItem> = BaseItemAdapter(List(5) { index ->
    PagerSampleItem(index)
  })

  fun removeLast(view: View) {
    adapter.remove(adapter.size - 1)
  }

  fun addToTheEnd(view: View) {
    adapter.add(PagerSampleItem(adapter.size))
  }

  override fun getTitle(): String = "ViewPager"

  override fun getLayoutId(): Int = R.layout.item_base_pager
}