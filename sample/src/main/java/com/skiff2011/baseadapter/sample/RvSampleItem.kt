package com.skiff2011.baseadapter.sample

import com.skiff2011.baseadapter.item.Item

class RvSampleItem(val index: Int) : Item {
  override fun getLayoutId(): Int = R.layout.item_rv_sample
}