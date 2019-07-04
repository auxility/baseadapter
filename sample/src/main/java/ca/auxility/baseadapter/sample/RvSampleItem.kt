package ca.auxility.baseadapter.sample

import ca.auxility.baseadapter.item.Item
import ca.auxility.baseadapter.sample.R.layout

class RvSampleItem(val index: Int) : Item {
  override fun getLayoutId(): Int = layout.item_rv_sample
}