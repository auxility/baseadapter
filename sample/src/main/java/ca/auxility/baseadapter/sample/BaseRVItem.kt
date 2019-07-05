package ca.auxility.baseadapter.sample

import android.view.View
import ca.auxility.baseadapter.Adapter
import ca.auxility.baseadapter.BaseAdapter
import ca.auxility.baseadapter.item.TitledItem
import ca.auxility.baseadapter.sample.R.layout

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