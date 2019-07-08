package dev.auxility.baseadapter.sample

import android.view.View
import dev.auxility.baseadapter.Adapter
import dev.auxility.baseadapter.BaseAdapter
import dev.auxility.baseadapter.item.TitledItem
import dev.auxility.baseadapter.sample.R.layout

class BaseRVItem : TitledItem {

  val adapter: Adapter<RvSampleItem> =
    BaseAdapter(List(10) { index ->
      RvSampleItem(index)
    })

  fun removeLast(view: View) {
    adapter.remove(adapter.size - 1)
  }

  fun addToTheEnd(view: View) {
    adapter.add(RvSampleItem(adapter.size))
  }

  override fun getTitle(): String = "RecyclerView"

  override fun getLayoutId(): Int = layout.item_base_rv
}