package eu.theappshop.baseadapter.sample

import eu.theappshop.baseadapter.vm.VM
import eu.theappshop.baseadapterproject.R

class TestVM(val count: Int) : VM {
  override fun getLayoutId(): Int = R.layout.item_test
}