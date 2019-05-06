package com.skiff2011.baseadapter.sample

import com.skiff2011.baseadapter.vm.VM

class TestVM(val count: Int) : VM {
  override fun getLayoutId(): Int = R.layout.item_test
}