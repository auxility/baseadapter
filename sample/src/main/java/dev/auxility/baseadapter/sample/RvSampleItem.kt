package dev.auxility.baseadapter.sample

import dev.auxility.baseadapter.item.Item
import dev.auxility.baseadapter.sample.R.layout

class RvSampleItem(val index: Int) : Item {
  override fun getLayoutId(): Int = layout.item_rv_sample
}