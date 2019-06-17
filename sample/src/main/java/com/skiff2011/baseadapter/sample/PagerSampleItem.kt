package com.skiff2011.baseadapter.sample

import com.skiff2011.baseadapter.item.Item

class PagerSampleItem(val index: Int) : Item {
  override fun getLayoutId(): Int = R.layout.item_pager_sample
}