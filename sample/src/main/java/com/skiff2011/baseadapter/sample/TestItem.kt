package com.skiff2011.baseadapter.sample

import com.skiff2011.baseadapter.item.Item

class TestItem(val count: Int) : Item {
  override fun getLayoutId(): Int = R.layout.item_test
}