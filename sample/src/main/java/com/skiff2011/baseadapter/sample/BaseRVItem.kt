package com.skiff2011.baseadapter.sample

import android.view.View
import com.skiff2011.baseadapter.BaseItemAdapter
import com.skiff2011.baseadapter.ItemAdapter
import com.skiff2011.baseadapter.item.TitledItem

class BaseRVItem : TitledItem {

  val adapter: ItemAdapter<RvSampleItem> = BaseItemAdapter(List(10) { index ->
    RvSampleItem(index)
  })

  fun removeLast(view: View) {
    adapter.remove(adapter.size - 1)
  }

  fun addToTheEnd(view: View) {
    adapter.add(RvSampleItem(adapter.size))
  }

  override fun getTitle(): String = "RecyclerView"

  override fun getLayoutId(): Int = R.layout.item_base_rv
}